package com.ray.io;

import java.util.Arrays;

/**
 * 自定义输出流辅助类
 * @author Ray
 *
 */
public class Out {
    
    /** 输出并换行 */ public static <T> void p() { System.out.println(); }
    /** 输出并换行 */ public static <T> void p(T t) { System.out.println(t); }
    /** 输出不换行 */ public static <T> void pt(T t) { System.out.print(t); }
    /** 格式化输出 */ public static <T> void pf(String s, Object...args){ System.out.printf(s, args); }
    /** 输出间隔符 */ public static     void sep() { sep(100); }
    
    /**
     * 输出间隔符
     * @param size
     */
    public static void sep(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) sb.append("=");
        p(sb.toString());
    }

    /********************************** 数组相关方法 **************************************/
    
    /** 打印数组 */public static <T> void p(T[]       t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(byte[]    t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(boolean[] t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(short[]   t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(int[]     t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(long[]    t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(double[]  t) { p(Arrays.toString(t)); }
    /** 打印数组 */public static void     p(char[]    t) { p(Arrays.toString(t)); }
    
    /** 打印数组 */public static <T> void p(T[]       t, String fmt) { for (T       i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(boolean[] t, String fmt) { for (boolean i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(short[]   t, String fmt) { for (short   i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(int[]     t, String fmt) { for (int     i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(byte[]    t, String fmt) { for (byte    i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(long[]    t, String fmt) { for (long    i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(double[]  t, String fmt) { for (double  i : t) pf(fmt, i); p(); }
    /** 打印数组 */public static void     p(char[]    t, String fmt) { for (char    i : t) pf(fmt, i); p(); }
    
   /**
     * 打印二维数组
     * @param arr
     * @param fmt
     */
    public static <T> void p(T[][] arr, String fmt) {
        for (T[] is : arr) {
            for (T i : is)
                pf(fmt, i);
            p();
        }
    }

    /**
     * 打印二维数组
     * @param arr
     * @param fmt
     */
    public static void p(int[][] arr, String fmt) {
        for (int[] is : arr) {
            for (int i : is)
                Out.pf(fmt, i);
            Out.p();
        }
    }

    /**
     * 打印二维数组
     * @param arr
     * @param fmt
     */
    public static void p(boolean[][] arr) {
        for (boolean[] is : arr) {
            for (boolean i : is)
                Out.pt(i? "T " : "F ");
            Out.p();
        }
    }
    
    /**
     * 打印二维数组
     * @param arr
     * @param fmt
     */
    public static void p(double[][] arr, String fmt) {
        for (double[] is : arr) {
            for (double i : is)
                Out.pf(fmt, i);
            Out.p();
        }
    }
    
    /**
     * 打印二维数组
     * @param arr
     * @param fmt
     */
    public static void p(long[][] arr, String fmt) {
        for (long[] is : arr) {
            for (long i : is)
                Out.pf(fmt, i);
            Out.p();
        }
    }
    
    /**
     * 打印二进制
     * @param n
     */
    public static void printBinary(int n) {
        System.out.println(Integer.toBinaryString(n));
    }
    
    public static void main(String[] args) {
        
    }

}
