package Tool;

import java.util.Random;

public class Fraction {

    private int numerator; // 分子

    private int denominator; // 分母

    private static Random random = new Random();

    // 检查分子是否为零
    public boolean isNumeratorZero() {
        return this.numerator == 0;
    }


    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        } else if (Math.abs(numerator) >= denominator) {
            int wholePart = numerator / denominator;
            int remainder = Math.abs(numerator % denominator);

            if (remainder == 0) { // 我的下面没有这个判断，因为简化函数会有约分，分母为1就是整除了，上面已经输出了，因此删了
                return String.valueOf(wholePart);
            } else {
                return wholePart + "'" + remainder + "/" + denominator;
            }
        } else {
            return numerator + "/" + denominator;
        }
    }

    public static Fraction createFraction(int range) {
        if (range <= 1) {
            return new Fraction(1, 1);
        }

        int numerator = random.nextInt(range - 1) + 1;
        int denominator = random.nextInt(range + 10) + 1;

        if (random.nextBoolean()) {
            return new Fraction(numerator, 1);
        }

        // 根据随机结果选择生成真分数或带分数
        if (random.nextBoolean()) {
            // 生成真分数
            return new Fraction(numerator, denominator);
        } else {
            // 生成带分数
            int wholePart = random.nextInt(range - 1) + 1;
            numerator = wholePart * denominator + random.nextInt(denominator);
            return new Fraction(numerator, denominator);
        }
    }


    // 构造函数2，接收字符串格式的操作数，并判断是什么类型的操作数
    public Fraction(String string) {
        string = string.trim();
        int wholePart;
        int fractionPartStart = string.indexOf("'"); // 查找整数部分的位置
        int fractionPartEnd = string.indexOf("/"); // 查找分母的位置

        // 处理带整数部分的分数，如 "1'2/3"
        if (fractionPartStart != -1) {
            wholePart = Integer.parseInt(string.substring(0, fractionPartStart));
            denominator = Integer.parseInt(string.substring(fractionPartEnd + 1));
            // 总分子 = 整数*分母+输入分子
            numerator = wholePart * denominator + Integer.parseInt(string.substring(fractionPartStart + 1, fractionPartEnd));
        }
        // 处理简单分数，如 "2/3"
        else if (fractionPartEnd != -1) {
            // 字符串中是否包含斜杠，从而判断是否有分数
            denominator = Integer.parseInt(string.substring(fractionPartEnd + 1));
            numerator = Integer.parseInt(string.substring(0, fractionPartEnd));
        }
        // 处理整数，如 "2"
        else {
            numerator = Integer.parseInt(string);
            denominator = 1;
        }
        normalize(); // 简化分数
    }

    // 构造函数1，接收分子和分母
    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        normalize(); // 简化分数
    }

    // 绝对值
    public void abs() {
        numerator = Math.abs(numerator);
        denominator = Math.abs(denominator);
    }

    // 判断是否为负数
    public boolean isNegative() {
        return numerator < 0;
    }

    // 计算最大公约数(GCD)
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // 计算最小公倍数(LCM)
    public int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    // 简化分数，确保分母不为0且为真分数，并约分
    private void normalize() {
        if (denominator == 0) {
            throw new IllegalArgumentException("分母不能为0");
        }
        if (denominator < 0) { // 确保分母为正
            numerator = -numerator;
            denominator = -denominator;
        }
        int gcdValue = gcd(Math.abs(numerator), Math.abs(denominator)); // 计算最大公约数
        numerator /= gcdValue; // 约分分子
        denominator /= gcdValue; // 约分分母
    }

    // 比较大小
    public static boolean compare(Fraction left, Fraction right){
        Fraction result = left.subtract_f(right);
        return result.numerator >= 0;
    }

    //将计算结果转换为字符串
//    private String fractionToString(Fraction fraction) {
//        if (fraction.denominator == 1) {
//            // 分母为1时，直接返回整数部分
//            return String.valueOf(fraction.numerator);
//        } else if (Math.abs(fraction.numerator) >= fraction.denominator) {
//            // 处理带分数部分
//            int wholePart = fraction.numerator / fraction.denominator;
//            int remainder = Math.abs(fraction.numerator % fraction.denominator);
//
//            if (remainder == 0) {
//                return String.valueOf(wholePart); // 如果没有余数，返回整数
//            } else {
//                return wholePart + "'" + remainder + "/" + fraction.denominator; // 带分数
//            }
//        } else {
//            // 真分数情况
//            return fraction.numerator + "/" + fraction.denominator;
//        }
//    }
    private String fractionToString(Fraction fraction) {
        if (fraction.numerator >= fraction.denominator) {
            if(fraction.numerator == 0) {
                return "0";
            }else if(fraction.denominator == 1) {
                return String.valueOf(fraction.numerator);
            }else{
                // 带整数部分的分数字符串输出
                int integerPart = fraction.numerator / fraction.denominator;
                int fractionPartNumerator = fraction.numerator % fraction.denominator ;
                if(fractionPartNumerator > 0) {
                    return integerPart + "'" + fractionPartNumerator + "/" +fraction.denominator ;
                }
                else return String.valueOf(integerPart);
            }
        } else {
            // 不带整数部分的字符串输出
            return numerator + "/" + fraction.denominator;
        }
    }

    // 加法
    public String add_s(Fraction b) {
        int lcmValue = lcm(denominator, b.denominator);
        int multipleA = lcmValue / denominator;
        int multipleB = lcmValue / b.denominator;
        Fraction sum = new Fraction(multipleA * numerator + multipleB * b.numerator, lcmValue);
        return fractionToString(sum);
        /*return sum.toString();*/
    }

    // 加法
    public Fraction add_f(Fraction b) {
        int lcmValue = lcm(denominator, b.denominator);
        int multipleA = lcmValue / denominator;
        int multipleB = lcmValue / b.denominator;
        return new Fraction(multipleA * numerator + multipleB * b.numerator, lcmValue);
    }

    // 减法
    public String subtract_s(Fraction b) {
        // a-b
        int lcmValue = lcm(denominator, b.denominator);
        int multipleA = lcmValue / denominator;
        int multipleB = lcmValue / b.denominator;
        Fraction difference = new Fraction(multipleA * numerator - multipleB * b.numerator, lcmValue);
        return fractionToString(difference);
        /*return difference.toString();*/
    }

    // 减法

//    public Fraction subtract_f(Fraction b) {
//        // a - b 的逻辑
//        int lcmValue = lcm(denominator, b.denominator);
//        int multipleA = lcmValue / denominator;
//        int multipleB = lcmValue / b.denominator;
//
//        int newNumerator = multipleA * numerator - multipleB * b.numerator;
//
//        return new Fraction(newNumerator, lcmValue); // 保证返回简化后的分数
//    }

    public Fraction subtract_f(Fraction b) {
        // a-b
        int lcmValue = lcm(denominator, b.denominator);
        int multipleA = lcmValue / denominator;
        int multipleB = lcmValue / b.denominator;
        return new Fraction(multipleA * numerator - multipleB * b.numerator, lcmValue);
    }

    // 乘法
    public String multiply_s(Fraction b) {
        Fraction product = new Fraction(b.numerator * numerator, b.denominator * denominator);
        return fractionToString(product);
        /*return product.toString();*/
    }

    // 乘法
    public Fraction multiply_f(Fraction b) {
        return new Fraction(b.numerator * numerator, b.denominator * denominator);
    }

    // 除法
    public String divide_s(Fraction b) {
        // a/b
        Fraction quotient = new Fraction(b.denominator * numerator, b.numerator* denominator);
        return fractionToString(quotient);
        /*return quotient.toString();*/
    }

    // 除法
    public Fraction divide_f(Fraction b) {
        // a/b
        return new Fraction(b.denominator * numerator, b.numerator* denominator);
    }
}
