package UnitTest;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Utils.FileUtils;

class FileUtilsTest {

    private final String testFolderPath = "resources";
    private final String testFileName = testFolderPath + "/testFile.txt";

    @Test
    void readFromFile() {
        // 设计目标：测试从文件中读取内容是否正确
        // Arrange
        List<List<String>> testData = Arrays.asList(
                List.of(),
                List.of("   "),
                List.of("Line 1"),
                List.of("Line 1", "Line 2", "Line 3"),
                List.of("   ", "Line 1", "   ", "Line 2"),
                List.of("4'2/5 + 8'7/5 × (9 + 10) ÷ 8 - 1/2 =           "),
                List.of("-85/47")
        );
        String[] expectedResults = {
                "[]",
                "[]",
                "[1. Line 1]",
                "[1. Line 1, 2. Line 2, 3. Line 3]",
                "[1. Line 1, 2. Line 2]",
                "[1. 4'2/5 + 8'7/5 × (9 + 10) ÷ 8 - 1/2 =]",
                "[1. -85/47]"
        };

        for (int i = 0; i < testData.size(); i++) {
            // Act
            List<String> content = testData.get(i);
            FileUtils.writeToFile(testFileName, content);
            List<String> result = FileUtils.readFromFile(testFileName);

            // Assert
            assertEquals(expectedResults[i], result.toString(), "读取文件错误");
        }
    }

    @Test
    void writeToFile_problemsOrAnswers() {
        // 设计目标：测试将题目与答案写入文件是否正确
        List<List<String>> testData = Arrays.asList(
                List.of(),
                List.of("   ")
        );
        List<String> testExercises = List.of( "4'2/5 + 8'7/5 × (9 + 10) ÷ 8 - 1/2 =           ","2 + 3 ="," 4'2/5 - 8 =   ","  8 × 7826 =");
        List<String> testAnswers = List.of("-8           ","-2'85/47","    8/9","1568339462");
        List<List<String>> expectedResult_1 = Arrays.asList(
                List.of(),
                List.of()
        );
        List<String> expectedResult_2 = List.of("1. 4'2/5 + 8'7/5 × (9 + 10) ÷ 8 - 1/2 =","2. 2 + 3 =","3. 4'2/5 - 8 =","4. 8 × 7826 =");
        List<String> expectedResult_3 = List.of("1. -8","2. -2'85/47","3. 8/9","4. 1568339462");

        for (int i = 0; i < testData.size(); i++) {
            // Act
            List<String> content = testData.get(i);
            FileUtils.writeToFile(testFileName, content);
            List<String> result = FileUtils.readFromFile(testFileName);

            // Assert
            List<String> expected = expectedResult_1.get(i);
            assertEquals(expected, result, "写入文件错误");
        }

        // Act
        FileUtils.writeToFile(testFileName, testExercises);

        // Assert
        List<String> result = FileUtils.readFromFile(testFileName);
        assertEquals(expectedResult_2, result, "写入文件错误");

        // Act
        FileUtils.writeToFile(testFileName, testAnswers);

        // Assert
        result = FileUtils.readFromFile(testFileName);
        assertEquals(expectedResult_3, result, "写入文件错误");
    }

    @Test
    void writeToFile_gradeResult() {
        // 设计目标：测试将统计结果写入文件是否正确
        ArrayList<Integer> correctList1 = new ArrayList<>();
        ArrayList<Integer> wrongList1 = new ArrayList<>();
        ArrayList<Integer> errorList1 = new ArrayList<>();
        List<String> expectedResult1 = List.of("Correct: 0(  )", "Wrong: 0(  )", "Error: 0(  )");

        ArrayList<Integer> correctList2 = new ArrayList<>();
        ArrayList<Integer> wrongList2 = new ArrayList<>();
        ArrayList<Integer> errorList2 = new ArrayList<>();
        for(int i = 0;i < 13;i++){
            String index = String.valueOf(i+1);
            if(i%3 == 1){
                correctList2.add(Integer.parseInt(index));// 2,5,8,11,共4个
            }else if(i%3 == 2){
                wrongList2.add(Integer.parseInt(index));// 3,6,9,12,共4个
            }else {
                errorList2.add(Integer.parseInt(index));// 1,4,7,10,13共4个
            }
        }
        List<String> expectedResult2 = List.of("Correct: 4( 2,5,8,11 )", "Wrong: 4( 3,6,9,12 )", "Error: 5( 1,4,7,10,13 )");

        // Arrange
        Object[][] testData = new Object[][] {
                {correctList1, wrongList1, errorList1,expectedResult1},
                {correctList2, wrongList2, errorList2,expectedResult2},
        };

        for (Object[] data : testData) {
            // Act
            ArrayList<Integer> correctList = (ArrayList<Integer>) data[0];
            ArrayList<Integer> wrongList = (ArrayList<Integer>) data[1];
            ArrayList<Integer> errorList = (ArrayList<Integer>) data[2];
            List<String>  expected = (List<String>) data[3];
            String fileDir = testFileName.replace("testFile.txt", "");
            FileUtils.writeToFile(fileDir, correctList, wrongList, errorList);
            List<String> result = FileUtils.readFromFile(fileDir + "/Grade.txt");

            // Assert
            assertEquals(expected, result, "统计结果写入文件错误");
        }
    }
}