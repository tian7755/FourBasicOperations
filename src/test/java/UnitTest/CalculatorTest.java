package UnitTest;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Tool.Fraction;
import Tool.Calculator;

class CalculatorTest {

    @Test
    void calculateExpression() {
        // 设计目标：测试计算表达式方法是否正确计算结果
        String[] expressions = {"1 - 1", "1/6 + 1/8", "3'2/5 × 2'1/2", "1/4 ÷ 1/8", "(7 + 8) × 2 ÷ (9 - 2)"};
        String[] expectedResults = {"0", "7/24", "8'1/2", "2", "4'2/7"};
        for (int i = 0; i < expressions.length; i++) {
            String expression = expressions[i];
            String result = Calculator.calculateExpression(expression);
            assertEquals(expectedResults[i], result, "表达式计算错误：" + expression);
        }
    }

    @Test
    void calculateStandardAnswer() {
        // 设计目标：测试标准答案计算方法是否正确返回结果和题目编号
        String[] expressions = {"1. 8/4 - 10/5", "2. 8/9 + 1", "3. 3 × 4/8", "4. 5/8 ÷ 3'1/2", "5. 1/5 × ((8 - 5) ÷ 5) + 2/25"};
        String[] expectedResults = {"0", "1'8/9", "1'1/2", "5/28", "1/5"};
        for (int i = 0; i < expressions.length; i++) {
            String expression = expressions[i];
            Map<String, String> result = Calculator.calculateStandardAnswer(expression);
            assertEquals(1, result.size(), "结果映射大小不正确：" + expression);
            for (Map.Entry<String, String> entry : result.entrySet()) {
                String index = entry.getKey();
                String standardAnswer = entry.getValue();
                String expectedIndex= String.valueOf(i + 1);
                assertEquals(expectedIndex, index, "表达式编号提取错误：" + expression);
                assertEquals(expectedResults[i], standardAnswer, "表达式结果计算错误：" + expression);
            }
        }
    }

    @Test
    void calculate_f() {
        // 设计目标：测试分数计算方法是否正确计算结果
        Fraction a1 = new Fraction("5/10"), b1 = new Fraction("3/9");
        Fraction a2 = new Fraction("4'2/3"), b2 = new Fraction("3'1/3");
        Fraction a3 = new Fraction("1/4"), b3 = new Fraction("2/3");
        Fraction a4 = new Fraction(4, 3), b4 = new Fraction(1, 2);
        String[] operators = {" + ", " - ", " × ", " ÷ "};
        Fraction r1 = new Fraction(5,6);
        Fraction r2 = new Fraction("1'1/3");
        Fraction r3 = new Fraction(2,12);
        Fraction r4 = new Fraction(16,6);
        Fraction[] expectedResults = {r1, r2, r3, r4};
        for (int i = 0; i < operators.length; i++) {
            Fraction a, b;
            switch (i) {
                case 0:
                    a = a1;
                    b = b1;
                    break;
                case 1:
                    a = a2;
                    b = b2;
                    break;
                case 2:
                    a = a3;
                    b = b3;
                    break;
                case 3:
                    a = a4;
                    b = b4;
                    break;
                default:
                    continue;
            }
            Fraction result = Calculator.calculate_f(a, b, operators[i]);
            assert result.isEquals(expectedResults[i]): "分数计算错误：" + a + operators[i] + b;
        }
    }
}