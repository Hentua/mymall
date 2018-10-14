package com.mall.modules.member.utils;

/**
 * base24编码
 * @author wank
 * @date 2018/01/10
 */
public class Base32 {

    private static final String baseDigits = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int BASE = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".length();
    private static final char[] digitsChar = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
    private static final int FAST_SIZE = 122;
    private static final int[] digitsIndex = new int[123];

    public Base32() {
    }

    public static long decode(String s) {
        long result = 0L;
        long multiplier = 1L;

        for (int pos = s.length() - 1; pos >= 0; --pos) {
            int index = getIndex(s, pos);
            result += (long) index * multiplier;
            multiplier *= (long) BASE;
        }

        return result;
    }

    public static String encode(Long number) {
        if (number < 0L) {
            throw new IllegalArgumentException("Number(Base32) must be positive: " + number);
        } else if (number == 0L) {
            return "0";
        } else {
            StringBuilder buf;
            for (buf = new StringBuilder(); number != 0L; number /= (long) BASE) {
                buf.append(digitsChar[(int) (number % (long) BASE)]);
            }

            return buf.reverse().toString();
        }
    }

    private static int getIndex(String s, int pos) {
        char c = s.charAt(pos);
        if (c > 'z') {
            throw new IllegalArgumentException("Unknow character for Base32: " + s);
        } else {
            int index = digitsIndex[c];
            if (index == -1) {
                throw new IllegalArgumentException("Unknow character for Base32: " + s);
            } else {
                return index;
            }
        }
    }

    static {
        int i;
        for (i = 0; i < 122; ++i) {
            digitsIndex[i] = -1;
        }

        for (i = 0; i < BASE; digitsIndex[digitsChar[i]] = i++) {
            ;
        }

    }
}
