package UnitTest;

import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Utils.Grader;
import Utils.FileUtils;

class GraderTest {

    @Test
    void grade() {
        // 设计目标：测试判题功能是否正确
        // Arrange
        List<String> testExercises = List.of("1/6 + 1/8 =","1/2 - 1/2 ="
                ,"(1 - 1) × 1/2 ÷ 3'2/3 + (5 + 3) =","2'1/2 × 6'2/3 =","8 ÷ 4 =");
        List<String> testAnswers = List.of("7/24","0","8","1/2");
        List<String> expectedResult = List.of("Correct: 3( 1,2,3 )","Wrong: 1( 4 )","Error: 1( 5 )");

        String testFolderPath = "resources";
        String testExercisesFileName = testFolderPath + "/Exercises.txt";
        FileUtils.writeToFile(testExercisesFileName,testExercises);
        String testAnswersFileName = testFolderPath + "/Answers.txt";
        FileUtils.writeToFile(testAnswersFileName,testAnswers);

        // Act
        Grader grader = new Grader(testExercisesFileName, testAnswersFileName);
        grader.grade();

        // Assert
        String fileDir = testAnswersFileName.replace("Answers.txt", "");
        List<String> result = FileUtils.readFromFile(fileDir + "/Grade.txt");
        assertEquals(expectedResult, result, "判题结果不正确");
    }

    @Test
    void getStudentAnswer() {
        // 设计目标：测试从答案字符串中提取答案及其编号的方法是否正确
        // Arrange
        String[] answers = {"1. 2/3", "2. -5", "3. 2'9/10", "4. 154932", "5. -3'2/3"};

        // Act & Assert
        for (String answer : answers) {
            Map<String, String> result = Grader.getStudentAnswer(answer);
            assertEquals(1, result.size(), "答案提取的大小错误，答案：" + answer);
            for (Map.Entry<String, String> entry : result.entrySet()) {
                String index = entry.getKey();
                String expectedAnswer = entry.getValue();
                assertEquals(answer.split("\\s*\\.\\s*")[0], index, "答案编号提取错误，答案：" + answer);
                assertEquals(answer.split("\\s*\\.\\s*")[1],expectedAnswer, "答案内容提取错误，答案：" + answer);
            }
        }
    }

    @Test
    void getTextAtIndex() {
        // 设计目标：测试返回当前行的内容的方法是否正确
        // Arrange
        List<String> lines = List.of("1. 2 + 3 =", "2. 5/3 ÷ 2'5/7 =", "3. (1 - 5'12/13) × (7 ÷ 2/9) =");

        // Act & Assert
        assertEquals("1. 2 + 3 =", Grader.getTextAtIndex(0, lines), "获取行内容错误，索引：0");
        assertEquals("3. (1 - 5'12/13) × (7 ÷ 2/9) =", Grader.getTextAtIndex(2, lines), "获取行内容错误，索引：5");
        assertNull(Grader.getTextAtIndex(-1, lines), "获取行内容错误，索引：-1");
        assertNull(Grader.getTextAtIndex(10, lines), "获取行内容错误，索引：10");
        assertEquals("2. 5/3 ÷ 2'5/7 =", Grader.getTextAtIndex(1, lines), "获取行内容错误，索引：2");
    }
}
