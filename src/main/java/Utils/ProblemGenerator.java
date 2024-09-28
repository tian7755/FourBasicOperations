package Utils;

import java.util.*;

import Tool.Expression;
import Tool.Calculator;

public class ProblemGenerator {

    private final int numProblems;              // 题目个数

    private final int range;                    // 操作数的取值范围

    // 构造函数，接收输入的题目个数和数值范围
    public ProblemGenerator(int numProblems, int range) {
        this.numProblems = numProblems;
        this.range = range;
    }

    // 生成一定数目和一定数值范围的题目及其答案
    public void generateProblems() {
        // 设置范围值
        Expression.setRange(range);

        // 用于存储问题和答案的列表
        List<String> expressions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        Set<String> adjustedExpressions = new HashSet<>(); // 用于问题的唯一性校验

        for (int i = 0; i < numProblems; i++) {
            String problem;
            String adjustedExpression;
            do {
                problem = Expression.generateExpression(); // 生成算术表达式
                adjustedExpression = Expression.getAdjustedExpression();
            } while (adjustedExpressions.contains(adjustedExpression)); // 如果问题重复，重新生成

            String answer = Calculator.calculateExpression(problem); // 计算表达式的答案

            adjustedExpressions.add(adjustedExpression); // 确保问题唯一性
            expressions.add(problem); // 存储问题
            answers.add(answer); // 存储答案
        }
        System.out.println("\n成功生成" + numProblems + "道数值范围在" + range + "内的题目\n");

        // 一次性写入文件
        FileUtils.writeToFile("resources/Exercises.txt", expressions);//src/
        FileUtils.writeToFile("resources/Answers.txt", answers);
    }

}
