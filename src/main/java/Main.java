import Utils.Grader;
import Utils.ProblemGenerator;

public class Main {

    public static void main(String[] args) {
        int numProblems = 0;
        int range = 0;
        String exerciseFile = null;
        String answerFile = null;

        // 保留，方便在编译器idea上的运行调试
        /*String exerciseFile = "resources/Exercises.txt";
        String answerFile = "resources/Answers.txt";*/

        // 遍历命令行参数
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
        }

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
        System.out.println("如果想生成一定数目且在一定范围内的四则运算题目以及答案");
        System.out.println("请输入：Myapp -n <生成题目的个数> -r <题目中数值的范围>");
        System.out.println("生成题目个数和题目中数值的范围需大于零，且题目中数值的范围只能输入一个参数，该参数为操作数的范围上限(不包括参数在内)\n");
        System.out.println("如果想判断关于某份题目的学生答案的正误情况");
        System.out.println("请输入：Myapp  -e <exercise file> -a <answer file>\n");
    }
}