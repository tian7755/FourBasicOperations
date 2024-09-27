package Tool;

import java.util.Random;

public class Expression {

    private static int range;                                               // 生成的操作数的数值范围

    private static final Random random;                                           // 用于生成伪随机数

    private static Node root;                                               // 存储表达式的二叉树的根结点

    private static final String ADD = " + ";                                // 加法运算符

    private static final String SUB = " - ";                                //减法运算符

    private static final String MUL = " × ";                                // 乘法运算符

    private static final String DIV = " ÷ ";                                // 除法运算符

    private static final String LEFT_BRACKET = "(";                         // 左括号符

    private static final String RIGHT_BRACKET = ")";                        // 右括号符

    private static final String[] OPERATOR = {ADD, SUB, MUL, DIV};          // 运算符集合

    // 生成随机数类实例
    static {
        random = new Random();
    }

    // 接收输入的数值范围
    public static void setRange(int range) {
        Expression.range = range;
    }

    // 操作数结点定义
    public static class Node {
        Fraction value;         // 存储操作数值
        Node rchild;            // 左子树结点
        Node lchild;            // 右子树结点

        // 操作数结点构造函数
        Node(Fraction value, Node rchild, Node lchild) {
            this.value = value;
            this.rchild = rchild;
            this.lchild = lchild;
        }

        // 转换为字符串类型，用于输出操作数值
        @Override
        public String toString() {
            if (value != null) {
                return value.toString();
            } else {
                return "";
            }
        }
    }

    // 运算符结点定义，继承操作数结点，其继承的value属性存储当前子表达式的计算结果
    static class OperatorNode extends Node {
        String operator;

        // 运算符结点构造函数
        OperatorNode(Node right, Node left, String operator) {
            super(null, right, left);
            this.operator = operator;
        }

        // 转换为字符串类型，用于输出运算符
        @Override
        public String toString() {
            return operator;
        }
    }

    // 生成一个随机的算术表达式
    public static String generateExpression() {
        int currentOperators = random.nextInt(3) + 1;
        root = buildExpression(currentOperators);
        return print(root) + " = ";
    }

    // 生成表达式二叉树
    public static Node buildExpression(int currentOperators) {
        if (currentOperators == 0) {
            return new Node(Fraction.createFraction(range), null, null);
        }
        OperatorNode parent = new OperatorNode(null, null, OPERATOR[random.nextInt(OPERATOR.length)]);

        int leftOperators = random.nextInt(currentOperators);
        int rightOperators = currentOperators - leftOperators - 1;

        parent.lchild = buildExpression(leftOperators);
        parent.rchild = buildExpression(rightOperators);

        // 计算子表达式的计算结果
        Fraction value = Calculator.calculate_f(parent.lchild.value, parent.rchild.value, parent.operator);

        // 调整减法子表达，保证被减数大于减数，且当被减数和减数相等时调整为加法表达式
        if (SUB.equals(parent.operator)) {
            if (value.isNegative()) {
                Node temp = parent.lchild;
                parent.lchild = parent.rchild;
                parent.rchild = temp;
                value.abs();
            }
            if (value.isNumeratorZero()) {
                parent.operator = ADD;
                value = Calculator.calculate_f(parent.lchild.value, parent.rchild.value, parent.operator);
            }
        }

        parent.value = value;
        return parent;
    }

    // 规范化表达式，便于去重
    public static String getAdjustedExpression() {
        adjustTree(root);
        return print(root);
    }

    // 调整存储随机表达式的二叉树为存储规范化表达式的二叉树,实际是统一加法与乘法子表达式左大右小
    public static void adjustTree(Node root) {
        if (root == null) {
            return;
        }
        if (root instanceof OperatorNode) {
            OperatorNode operatorNode = (OperatorNode) root;
            if (ADD.equals(operatorNode.operator) || MUL.equals(operatorNode.operator)) {
                if (Fraction.compare(root.lchild.value, root.rchild.value)) {
                    Node temp = root.lchild;
                    root.lchild = root.rchild;
                    root.rchild = temp;
                }
            }
        }
        adjustTree(root.lchild);
        adjustTree(root.rchild);
    }

    // 中序遍历二叉树，输出表达式
    private static String print(Node node) {
        if (node == null) {
            return "";
        }
        String mid = node.toString();

        String left = print(node.lchild);
        if (node.lchild instanceof OperatorNode && leftBrackets(((OperatorNode) node.lchild).operator, mid)) {
            left = LEFT_BRACKET + left + RIGHT_BRACKET;
        }

        String right = print(node.rchild);
        if (node.rchild instanceof OperatorNode && rightBrackets(((OperatorNode) node.rchild).operator, mid)) {
            right = LEFT_BRACKET + right + RIGHT_BRACKET;
        }
        return left + "" + mid + "" + right;
    }

    // 判断是否需要左括号
    private static boolean leftBrackets(String leftSymbol, String currentSymbol) {
        return ifNeedsBrackets(leftSymbol, currentSymbol);
    }

    // 判断是否需要右括号
    private static boolean rightBrackets(String rightSymbol, String currentSymbol) {
        return !ifNeedsBrackets(currentSymbol, rightSymbol);
    }

    // 判断是否需要括号
    private static boolean ifNeedsBrackets(String operator1, String operator2) {
        // 获取运算符优先级
        int priority1 = getOperatorPriority(operator1);
        int priority2 = getOperatorPriority(operator2);
        // 如果左侧运算符优先级低于当前节点的优先级，则需要括号
        return priority1 < priority2;
    }

    // 获取运算符优先级
    private static int getOperatorPriority(String operator) {
        if (operator.equals(ADD) || operator.equals(SUB)) {
            return 1;
        } else if (operator.equals(MUL) || operator.equals(DIV)) {
            return 2;
        }
        return 0;
    }
}