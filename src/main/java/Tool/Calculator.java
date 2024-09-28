package Tool;

import java.util.*;

public class Calculator {

    // 构造函数
    public Calculator() {
    }

    // 根据输入的表达式生成答案，用于生成功能
    public static String calculateExpression(String expression) {
        Calculator calculator = new Calculator();
        String expressionToken = calculator. infixToPostfix(expression)[1];
        return calculator.evaluatePostfix(expressionToken);
    }

    // 根据输入的表达式生成包含答案和题目编号的映射表，用于判题功能
    public static Map<String, String> calculateStandardAnswer(String expression) {
        // 解析和计算表达式的方法
        Map<String, String> standardAnswer = new HashMap<>();
        Calculator calculator = new Calculator();
        String[] expressionToken = calculator. infixToPostfix(expression);
        String result = calculator.evaluatePostfix(expressionToken[1]);
        standardAnswer.put(expressionToken[0], result);
        return standardAnswer;
    }

    // 中缀表达式转为后缀表达式
    private String[] infixToPostfix(String expression) {
        String index = "0"; // 用于存储题目编号
        Stack<Character> operatorStack = new Stack<>();
        StringBuilder output = new StringBuilder();
        expression = separateParentheses(expression);
        String[] tokens = expression.split(" "); // 以空格分割表达式

        // 处理题目编号，生成功能使用时忽略
        if (tokens[0].endsWith(".")) {
            index = tokens[0].replaceAll("\\.$", ""); // 去掉最后的点
        }

        // 处理每个token
        for (String token : tokens) {
            if (token != null && !token.isEmpty()) {
                if (isFraction(token) || Character.isDigit(token.charAt(0))) {
                    output.append(token).append(" "); // 添加分数或数字
                } else if (token.equals("(")) {
                    operatorStack.push('('); // 左括号入栈
                } else if (token.equals(")")) {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                        output.append(operatorStack.pop()).append(" "); // 弹出运算符到输出
                    }
                    operatorStack.pop(); // 弹出左括号
                } else if (isOperator(token.charAt(0))) {
                    while (!operatorStack.isEmpty() && precedence(token.charAt(0)) <= precedence(operatorStack.peek())) {
                        output.append(operatorStack.pop()).append(" "); // 弹出高优先级运算符
                    }
                    operatorStack.push(token.charAt(0)); // 当前运算符入栈
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            output.append(operatorStack.pop()).append(" "); // 弹出剩余运算符
        }

        return new String[]{index, output.toString().trim()}; // 返回编号和后缀表达式，生成功能使用时只用接收后缀表达式
    }

    // 计算后缀表达式结果
    private String evaluatePostfix(String postfix) {
        Stack<String> valueStack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (isFraction(token) || Character.isDigit(token.charAt(0))){
                valueStack.push(token); // 入栈操作数
            } else if (isOperator(token.charAt(0))) {
                Fraction b = new Fraction(valueStack.pop());
                Fraction a = new Fraction(valueStack.pop());
                String result = calculate_s(a, b, token.charAt(0));
                valueStack.push(result); // 入栈结果
            }
        }
        return valueStack.pop(); // 返回最终结果
    }

    // 判题功能使用的计算操作
    private static String calculate_s(Fraction a, Fraction b, char operator) {
        return switch (operator) {
            case '+' -> a.add_s(b);
            case '-' -> a.subtract_s(b);
            // case '*':
            case '×' -> a.multiply_s(b);
            // case '/':
            case '÷' -> a.divide_s(b);
            default -> throw new IllegalArgumentException("无效的运算符: " + operator);
        };
    }

    // 生成功能使用的计算操作
    public static Fraction calculate_f(Fraction a, Fraction b, String operator) {
        return switch (operator) {
            case " + " -> a.add_f(b);
            case " - " -> a.subtract_f(b);
            // case '*':
            case " × " -> a.multiply_f(b);
            // case '/':
            case " ÷ " -> a.divide_f(b);
            default -> throw new IllegalArgumentException("无效的运算符: " + operator);
        };
    }

    // 在表达式中括号和临近的操作数间加入空格，便于之后表达式各个操作数与运算符的提取
    public static String separateParentheses(String input) {
        // 初始化一个新的StringBuilder用于构建结果字符串
        StringBuilder result = new StringBuilder();
        StringBuilder temp = new StringBuilder(); // 用于暂存括号之间的字符

        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // 检查当前字符是否是括号
            if (currentChar == '(' || currentChar == ')') {
                // 如果之前有非括号字符，将它们添加到结果字符串
                if (!temp.isEmpty()) {
                    result.append(temp).append(" ");
                    temp.setLength(0); // 清空临时字符串
                }
                // 将括号添加到结果字符串
                result.append(currentChar).append(" ");
            } else {
                // 如果当前字符不是括号，则添加到临时字符串
                temp.append(currentChar);
            }
        }

        // 如果最后一个字符不是括号，将剩余的字符添加到结果字符串
        if (!temp.isEmpty()) {
            result.append(temp);
        }

        // 返回结果字符串
        return result.toString().trim();
    }

    // 获取运算符优先级
    private static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '×', '÷' -> 2;
            default -> 0;
        };
    }

    // 判断一个字符串是否为带分数或真分数
    public static boolean isFraction(String token) {
        return token.matches("\\d+'\\d+/\\d+") || token.matches("\\d+/\\d+");
    }

    // 判断是否为四则运算符
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷';
    }
}
