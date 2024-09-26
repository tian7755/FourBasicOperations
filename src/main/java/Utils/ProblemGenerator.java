package Utils;

import Tool.Expression;
import Tool.Calculator;

import java.util.*;

public class ProblemGenerator {
    private int numProblems;
    private int range;
    private List<String> problems;
    private List<String> answers;

    public ProblemGenerator(int numProblems, int range) {
        this.numProblems = numProblems;
        this.range = range;
        this.problems = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    public void generateProblems() {
        Expression.setRange(range); // 设置范围值
        Random random = new Random();
        while (problems.size() < numProblems) {
            String problem = Expression.generateExpression();
            if (true) { // // 调用 Expression 类判断是否重复
                String answer = Calculator.calculateExpression(problem);// 调用 Calculator 类计算题目答案
                problems.add(problem);
                answers.add(answer);
            }
        }

        // 保存文件
        FileUtils.writeToFile("resources/Exercises.txt", problems);
        FileUtils.writeToFile("resources/Answers.txt", answers);
    }
}
