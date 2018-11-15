package com.ray.io;

import java.util.Arrays;

/**
 * �Խ��ļ򻯱�׼����ĸ�����
 * @author Ray
 *
 */
public class Out {
    
    /**
     * ��ӡ������
     * @param t
     */
    public static <T> void p() {
        System.out.println();
    }
	
	/**
	 * ��ӡ������
	 * @param t
	 */
	public static <T> void p(T t) {
		System.out.println(t);
	}
	
	
	/** ��ӡ���鲢���� */public static <T> void p(T[] t)      { p(Arrays.toString(t)); }
	/** ��ӡ���鲢���� */public static void p(boolean[] t)    { p(Arrays.toString(t)); }
	/** ��ӡ���鲢���� */public static void p(short[] t)      { p(Arrays.toString(t)); }
	/** ��ӡ���鲢���� */public static void p(int[] t)        { p(Arrays.toString(t)); }
	/** ��ӡ���鲢���� */public static void p(long[] t)       { p(Arrays.toString(t)); }
	/** ��ӡ���鲢���� */public static void p(double[] t)     { p(Arrays.toString(t)); }
	/** ��ӡ���鲢���� */public static void p(char[] t)       { p(Arrays.toString(t)); }
	
	/**
	 * ��ӡ��ά����
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
     * ��ӡ��ά����
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
     * ��ӡ��ά����
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
	 * ��ʽ�����
	 * @param s
	 * @param args
	 */
	public static <T> void pf(String s, Object...args){
		System.out.printf(s, args);
	}
	
	/**
	 * ֻ��ӡ������
	 * @param t
	 */
	public static <T> void pt(T t) {
		System.out.print(t);
	}
	
	/**
	 * ��ӡ�ָ���
	 * 
	 */
	public static void sep() {
		sep(100);
	}
	
	/**
	 * ��ӡ�ָ���
	 * @param size
	 */
	public static void sep(int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) sb.append("=");
		p(sb.toString());
	}
	
}
