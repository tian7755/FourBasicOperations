package Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // 从文件中读取内容，返回列表
    public static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                if (!trimmedLine.isEmpty()) {
                    lines.add(trimmedLine);
                }
            }
            System.out.println("已成功从文件读取题目或答案： "+ fileName);
        } catch (IOException e) {
            System.out.println("读取文件失败：" + fileName);
            e.printStackTrace();
        }
        return lines;
    }

    // 将题目或答案写入文件
    public static void writeToFile(String fileName, List<String> data) {
        Path path = Paths.get("resources");
        if(!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            }catch (IOException e){
                System.out.println("无法创建文件夹： "+ path);
                e.printStackTrace();
                return;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            int index = 1;
            for (int i = 0; i < data.size(); i++) {
                String trimmedData = data.get(i).replaceAll("^\\s+", "").trim();
                if (!trimmedData.isEmpty()) {
                    writer.write((index) + ". " + trimmedData );
                    writer.newLine();
                    index++;
                }
            }
            System.out.println("已将相应题目或答案写入文件： "+ fileName);
        } catch (IOException e) {
            System.out.println("写入文件失败：" + fileName);
            e.printStackTrace();
        }
    }

    // 将统计结果写入文件
    public static void writeToFile(String fileName_a, ArrayList<Integer> correctList, ArrayList<Integer> wrongList, ArrayList<Integer> errorList) {
        String fileDir = fileName_a.replace("Answers.txt", "");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileDir + "Grade.txt"))) {
            writer.write("Correct: " + correctList.size() + "( " + correctList.toString().replaceAll("[\\[\\] ]", "") + " )");
            writer.newLine();
            writer.write("Wrong: " + wrongList.size() + "( " + wrongList.toString().replaceAll("[\\[\\] ]", "") + " )");
            writer.newLine();
            writer.write("Error: " + errorList.size() + "( " + errorList.toString().replaceAll("[\\[\\] ]", "") + " )");
            writer.newLine();
            System.out.println("已将答案正误统计结果写入文件： "+ fileDir + "Grade.txt");
        } catch (IOException e) {
            System.out.println("写入文件失败：" + fileDir + "/Grade.txt");
            e.printStackTrace();
        }
    }
}
