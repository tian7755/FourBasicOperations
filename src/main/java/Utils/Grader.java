package Utils;

import java.util.*;
import Tool.Calculator;

public class Grader {

    private final String exerciseFile;                      // 存储题目的文件路径

    private final String answerFile;                        // 存储答案的文件路径

    // 构造函数，接收输入的题目文件和答案文件的路径
    public Grader(String exerciseFile, String answerFile) {
        this.exerciseFile = exerciseFile;
        this.answerFile = answerFile;
    }

    // 实现判题
    public void grade() {
        // 读取文本内容
        List<String> expressionLines = FileUtils.readFromFile(exerciseFile);
        List<String> answerLines = FileUtils.readFromFile(answerFile);

        // 初始化存放标答，回答的映射表，统计正确，错误，异常的动态数组表
        List<Map<String, String>> standardAnswers = new ArrayList<>();
        List<Map<String, String>>studentAnswers = new ArrayList<>();
        ArrayList<Integer> correctList = new ArrayList<>();
        ArrayList<Integer> wrongList = new ArrayList<>();
        ArrayList<Integer> errorList = new ArrayList<>();

        // 获取单行答案和题目
        int currentIndex = 0;
        String expression = getTextAtIndex(currentIndex, expressionLines);
        String answer = getTextAtIndex(currentIndex, answerLines);

        // 计算正确答案，读取输入答案
        while (expression != null && answer != null) {
            studentAnswers.add(getStudentAnswer(answer));
            standardAnswers.add(Calculator.calculateStandardAnswer(expression));
            currentIndex++;
            expression = getTextAtIndex(currentIndex, expressionLines);
            answer = getTextAtIndex(currentIndex, answerLines);
        }
        if (expression != null) {
            while (expression != null) {
                standardAnswers.add(Calculator.calculateStandardAnswer(expression));
                currentIndex++;
                expression = getTextAtIndex(currentIndex, expressionLines);
            }
        }
        if (answer != null) {
            while (answer != null) {
                studentAnswers.add(getStudentAnswer(answer));
                currentIndex++;
                answer = getTextAtIndex(currentIndex, answerLines);
            }
        }

        // 统计结果并写入文件
        statistics(standardAnswers, studentAnswers, correctList, wrongList, errorList);
        System.out.println("\n成功对比判断，并统计出在" + answerFile + "路径上的学生答案关于在" + exerciseFile + "路径上的题目的正误结果");
        System.out.println("结果含义说明：\nError——表示找不到的题目个数和对应编号\nCorrect——表示正确的题目个数和对应编号\nWrong——表示错误的题目个数和对应编号\n");
        FileUtils.writeToFile(answerFile, correctList, wrongList, errorList);
    }

    // 统计正确、错误和异常答案对应的题目编号及其数量
    private static void statistics(List<Map<String, String>> standardAnswers, List<Map<String, String>> studentAnswers,
                                   ArrayList<Integer> correctList, ArrayList<Integer> wrongList, ArrayList<Integer> errorList) {
        for (int i = 0; i < standardAnswers.size(); i++) {
            Map<String, String> standardMap = standardAnswers.get(i);

            for (Map.Entry<String, String> entry : standardMap.entrySet()) {
                String index = entry.getKey();
                String standardAnswer = entry.getValue();
                boolean isExist = false;

                for(int j = 0; j < studentAnswers.size(); j++) {
                    Map<String, String> studentMap = studentAnswers.get(j);

                    for (Map.Entry<String, String> entryStudent : studentMap.entrySet()) {
                        String index_s = entryStudent.getKey();
                        if(index_s.equals(index)) {
                            isExist = true;
                            String studentAnswer = entryStudent.getValue();
                            if (standardAnswer.equals(studentAnswer)) {
                                correctList.add(Integer.parseInt(index));
                            } else {
                                wrongList.add(Integer.parseInt(index));
                            }
                        }
                    }
                }
                if (!isExist) {
                    errorList.add(Integer.parseInt(index));
                }
            }
        }
    }

    // 从答案字符串中提取答案及其编号
    public static Map<String, String> getStudentAnswer(String answer) {
        Map<String, String> studentAnswer = new HashMap<>();
        String[] parts = answer.split("\\s*\\.\\s*");
        if (parts.length == 2) {
            studentAnswer.put(parts[0].trim(), parts[1].trim());
        }
        return studentAnswer;
    }

    // 返回当前行的内容
    public static String getTextAtIndex(int Index, List<String> fileLines) {
        if (Index >= 0 && Index < fileLines.size()) {
            return fileLines.get(Index); // 返回指定索引的行
        }
        return null; // 索引超出范围
    }
}
