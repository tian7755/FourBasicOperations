package Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    // 将题目或答案写入文件
    public static void writeToFile(String fileName, List<String> data) {
        // 确保文件夹存在
        Path path = Paths.get("resources");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);  // 创建resources文件夹
            } catch (IOException e) {
                System.out.println("无法创建文件夹：" + path.toString());
                e.printStackTrace();
                return; // 如果创建文件夹失败，提前返回
            }
        }

        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < data.size(); i++) {
                writer.write((i + 1) + ". " + data.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("写入文件失败：" + fileName);
            e.printStackTrace();
        }
    }
}
