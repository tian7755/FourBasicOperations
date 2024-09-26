import Utils.Grader;
import Utils.ProblemGenerator;

public class Main {
    public static void main(String[] args) {
        int numProblems = 11;
        int range = 10;
        String exerciseFile = null;
        String answerFile = null;

//        String exerciseFile = "resources/Exercises.txt";
//        String answerFile = "resources/Answers.txt";

        /*// 遍历命令行参数
        for (int i = 0; i < args.length; i++) {
            if ("-n".equals(args[i]) && (i + 1) < args.length) {
                numProblems = Integer.parseInt(args[i + 1]);
                i++; // 跳过下一个参数（参数值）
            } else if ("-r".equals(args[i]) && (i + 1) < args.length) {
                range = Integer.parseInt(args[i + 1]);
                i++; // 跳过下一个参数（参数值）
            } else if ("-e".equals(args[i]) && (i + 1) < args.length) {
                exerciseFile = args[i + 1];
                i++; // 跳过下一个参数（参数值）
            } else if ("-a".equals(args[i]) && (i + 1) < args.length) {
                answerFile = args[i + 1];
                i++; // 跳过下一个参数（参数值）
            } else {
                printHelp();
                return;
            }
        }*/

        // 根据参数执行相应的操作
        if (numProblems > 0 && range > 0) {
            // 生成题目模式
            ProblemGenerator generator = new ProblemGenerator(numProblems, range);
            generator.generateProblems();
        }else if (exerciseFile != null && answerFile != null) {
            // 判题模式
            Grader grader = new Grader(exerciseFile, answerFile);
            grader.grade();
        } else {
            printHelp();
        }
    }

    private static void printHelp() {
        System.out.println("请输入：Myapp -n <生成题目的个数> -r <题目中数值的范围> -e <exercise file> -a <answer file>");
    }
}