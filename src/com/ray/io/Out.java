package com.ray.io;

import java.util.Arrays;

/**
 * 锟皆斤拷锟侥简化憋拷准锟斤拷锟斤拷母锟斤拷锟斤拷锟�
 * @author Ray
 *
 */
public class Out {
    
    /**
     * 锟斤拷印锟斤拷锟斤拷锟斤拷
     * @param t
     */
    public static <T> void p() {
        System.out.println();
    }
	
	/**
	 * 锟斤拷印锟斤拷锟斤拷锟斤拷
	 * @param t
	 */
	public static <T> void p(T t) {
		System.out.println(t);
	}
	
	
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static <T> void p(T[] t)      { p(Arrays.toString(t)); }
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static void p(boolean[] t)    { p(Arrays.toString(t)); }
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static void p(short[] t)      { p(Arrays.toString(t)); }
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static void p(int[] t)        { p(Arrays.toString(t)); }
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static void p(long[] t)       { p(Arrays.toString(t)); }
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static void p(double[] t)     { p(Arrays.toString(t)); }
	/** 锟斤拷印锟斤拷锟介并锟斤拷锟斤拷 */public static void p(char[] t)       { p(Arrays.toString(t)); }
	
	/**
	 * 锟斤拷印锟斤拷维锟斤拷锟斤拷
	 * @param arr
	 * @param fmt
	 */
	public static void p(int[][] arr, String fmt) {
	    for (int[] is : arr) {
            for (int i : is) {
                Out.pf(fmt, i);
            }
            Out.p();
        }
	}
	
   /**
     * 锟斤拷印锟斤拷维锟斤拷锟斤拷
     * @param arr
     * @param fmt
     */
    public static <T> void p(T[][] arr, String fmt) {
        for (T[] is : arr) {
            for (T i : is) {
                Out.pf(fmt, i);
            }
            Out.p();
        }
    }
	
	   /**
     * 锟斤拷印锟斤拷维锟斤拷锟斤拷
     * @param arr
     * @param fmt
     */
    public static void p(double[][] arr, String fmt) {
        for (double[] is : arr) {
            for (double i : is) {
                Out.pf(fmt, i);
            }
            Out.p();
        }
    }
	
	
	/**
	 * 锟斤拷式锟斤拷锟斤拷锟�
	 * @param s
	 * @param args
	 */
	public static <T> void pf(String s, Object...args){
		System.out.printf(s, args);
	}
	
	/**
	 * 只锟斤拷印锟斤拷锟斤拷锟斤拷
	 * @param t
	 */
	public static <T> void pt(T t) {
		System.out.print(t);
	}
	
	/**
	 * 锟斤拷印锟街革拷锟斤拷
	 * 
	 */
	public static void sep() {
		sep(100);
	}
	
	/**
	 * 锟斤拷印锟街革拷锟斤拷
	 * @param size
	 */
	public static void sep(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) sb.append("=");
		p(sb.toString());
	}
	
}
