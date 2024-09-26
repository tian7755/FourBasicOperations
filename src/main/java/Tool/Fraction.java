package Tool;

import java.util.Random;

public class Fraction {

    private int numerator; // 分子
    private int denominator; // 分母
    private static Random random = new Random();




    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        } else if (Math.abs(numerator) >= denominator) {
            int wholePart = numerator / denominator;
            int remainder = Math.abs(numerator % denominator);

            if (remainder == 0) {
                return String.valueOf(wholePart);
            } else {
                return wholePart + "'" + remainder + "/" + denominator;
            }
        } else {
            return numerator + "/" + denominator;
        }
    }





    public static Fraction createFraction(int range) {
        if (range == 1) {
            return new Fraction(1, 1);
        }

        int numerator = random.nextInt(range - 1) + 1;
        int denominator = random.nextInt(range - 1) + 1;

        if (random.nextBoolean()) {
            return new Fraction(numerator, 1);
        }

        // 根据随机结果选择生成真分数或带分数
        if (random.nextBoolean()) {
            // 生成真分数
            return new Fraction(numerator, denominator);
        } else {
            // 生成带分数
            int wholePart = random.nextInt(range);
            numerator = wholePart * denominator + random.nextInt(denominator) + 1;
            return new Fraction(numerator, denominator);
        }
    }

    public Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }



}