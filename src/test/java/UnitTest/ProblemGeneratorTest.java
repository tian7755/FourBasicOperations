package UnitTest;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Tool.Fraction;
import Tool.Calculator;
import static Tool.Calculator.isFraction;

import Utils.FileUtils;
import Utils.ProblemGenerator;

class ProblemGeneratorTest {

    @Test
    void generateProblems() {
        // 设计目标：验证ProblemGenerator是否能生成指定数量和数值范围的题目及其答案
        // Arrange
        int numProblems = 10000;
        int range = 3;
        ProblemGenerator generator = new ProblemGenerator(numProblems, range);

        // Act
        generator.generateProblems();

        // 读取生成的文件内容
        List<String> expressions = FileUtils.readFromFile("resources/Exercises.txt");
        List<String> answers = FileUtils.readFromFile("resources/Answers.txt");

        // Assert
        assertEquals(numProblems, expressions.size(), "生成的题目数量不正确");
        assertEquals(numProblems, answers.size(), "生成的答案数量不正确");

        // 检查每个问题和答案是否符合预期
        for (int i = 0; i < numProblems; i++) {
            // 验证答案不为空
            assertNotNull(answers.get(i), "生成的答案不能为空");

            // 验证输出表达式的操作数在指定范围内
            String expression = expressions.get(i).substring(expressions.get(i).lastIndexOf(".") + 1, expressions.get(i).indexOf("="));
            String expression_2 = Calculator.separateParentheses(expression);
            String[] tokens = expression_2.split(" ");
            for (String token : tokens) {
                if (token != null && !token.isEmpty()) {
                    if (isFraction(token) || Character.isDigit(token.charAt(0))) {
                        Fraction a = new Fraction(token);
                        assert a.isInRange(range) : "操作数数值范围错误" + "range: " + range + "操作数: " + a;
                    }
                }
            }

            // 验证输出的计算结果正确性
            String answer = answers.get(i).substring(expressions.get(i).lastIndexOf(".") + 1).trim();
            assertEquals(answer, Calculator.calculateExpression(expression), "生成的答案与计算结果不匹配");
        }
    }
}