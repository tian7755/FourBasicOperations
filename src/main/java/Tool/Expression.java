package Tool;

import java.util.Random;

public class Expression {

    private static int range;
    private static Random random;
    private static Node root;

    private static final String ADD = " + ";
    private static final String SUB = " - ";
    private static final String MUL = " × ";
    private static final String DIV = " ÷ ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String[] OPERATOR = {ADD,SUB,MUL,DIV};




    static {
        random = new Random();
    }

    public static void setRange(int range) {
        Expression.range = range;
    }

    public static class Node {
        Fraction value;
        Node rchild;
        Node lchild;

        Node(Fraction value, Node rchild, Node lchild) {
            this.value = value;
            this.rchild = rchild;
            this.lchild = lchild;
        }

        @Override
        public String toString() {
            if (value != null) {
                return value.toString();
            } else {
                return "";
            }
        }

    }

    static class OperatorNode extends Node {
        String operator;

        OperatorNode(Node right, Node left, String operator) {
            super(null, right, left);
            this.operator = operator;
        }

        @Override
        public String toString() {
            if (value != null) {
                return value.toString();
            } else {
                return operator;
            }
        }


    }

    // 生成一个随机的算术表达式
    public static String generateExpression() {
        int currentOperators = random.nextInt(3) + 1;
        root = buildExpression(currentOperators);
        return print(root) + " = ";
    }

    // 生成表达式树
    public static Node buildExpression(int currentOperators) {
        if (currentOperators == 0) {
            return new Node(Fraction.createFraction(range), null, null);
        }
        OperatorNode parent = new OperatorNode(null, null, OPERATOR[random.nextInt(OPERATOR.length)]);


        int leftOperators = random.nextInt(currentOperators);
        int rightOperators = currentOperators - leftOperators - 1;


        parent.lchild = buildExpression(leftOperators);
        parent.rchild = buildExpression(rightOperators);

        return parent;
    }

    private static boolean leftBrackets(String leftSymbol, String currentSymbol) {
        return ifNeedsBrackets(leftSymbol, currentSymbol);
    }

    private static boolean rightBrackets(String rightSymbol, String currentSymbol) {
        return ifNeedsBrackets(currentSymbol, rightSymbol);
    }

    private static boolean ifNeedsBrackets(String operator1, String operator2) {
        // 定义运算符优先级
        int priority1 = getOperatorPriority(operator1);
        int priority2 = getOperatorPriority(operator2);
        // 如果左侧运算符优先级高于或等于当前节点的优先级，则需要括号
        return priority1 >= priority2;
    }

    private static int getOperatorPriority(String operator) {
        if (operator.equals(ADD) || operator.equals(SUB)) {
            return 1;
        } else if (operator.equals(MUL) || operator.equals(DIV)) {
            return 2;
        }
        return 0;
    }



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

        return left + mid + right;

    }


    public static void main(String[] args) {
        setRange(10);
        String expression = generateExpression();
        System.out.println("\nGenerated Expression: " + expression);
    }





}