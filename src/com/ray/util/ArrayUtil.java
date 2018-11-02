package com.ray.util;

import java.util.Random;

import com.ray.io.Out;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ArrayUtil {
	
	/**
     * ��������Ƿ��Ѿ�����
     * @param arr
     */
    public static void checkSorted(Comparable[] arr) {
    	Out.pf("����飬����%s����\n", isSorted(arr) ? "��" : "δ");
    }
	
	   /**
     * �������ֲ��Ƿ��Ѿ�����
     * @param arr
     */
    public static void checkSorted(Comparable[] arr, int lo, int hi) {
        Out.pf("����飬����%s����\n", isSorted(arr, lo, hi) ? "��" : "δ");
    }
    
    /**
     * ������СԪ�ص�����
     * @param arr
     * @param i
     * @param j
     */
    public static <T> int indexOfMin(Comparable[] arr) {
        return indexOfMin(arr, 0, arr.length-1);
    }
	
	/**
	 * ĳһ��������СԪ�ص�����
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static <T> int indexOfMin(Comparable[] arr, int lo, int hi) {
	    int index = lo;
	    for (int i = lo; i <= hi; i++) {
            if (less(arr, i, index)) index = i;
        }
	    return index;
    }
	
    /**
     * ������СԪ�ص�����
     * @param arr
     * @param i
     * @param j
     */
    public static int indexOfMin(int[] arr) {
        return indexOfMin(arr, 0, arr.length-1);
    }
    
    /**
     * ĳһ��������СԪ�ص�����
     * @param arr
     * @param i
     * @param j
     */
    public static int indexOfMin(int[] arr, int lo, int hi) {
        int index = lo;
        for (int i = lo; i <= hi; i++) {
            if (arr[i] < arr[index]) index = i;
        }
        return index;
    }
	
	/**
     * ��ȡ ��������
     * @param size
     * @return
     */
    public static int[] intArr(int size) {
        int[] arr = new int[size]; 
        for(int i = 0; i < arr.length; i ++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    /**
	 * ��ȡ������ת���飬����ָ����ת��λ��
	 * @param size			����ߴ�
	 * @param deviation		ƫ����
	 * @return
	 */
	public static Integer[] intArrRotate(int size, int deviation) {
		Integer[] arr = new Integer[size]; 
		for(int i = 0; i < arr.length; i ++) {
			arr[i] = ( i - deviation + size) % size;
		}
		return arr;
	}

    /**
	 * ��ȡ ��������
	 * @param size
	 * @return
	 */
	public static Integer[] integerArr(int size) {
		Integer[] arr = new Integer[size]; 
		for(int i = 0; i < arr.length; i ++) {
			arr[i] = i + 1;
		}
		return arr;
	}
	
    /**
	 * ��������Ƿ��Ѿ�����
	 * @param arr
	 * @return
	 */
	public static boolean isSorted(Comparable[] arr){
	    return isSorted(arr, 0, arr.length - 1);
	}
	
	/**
     * �������ֲ��Ƿ��Ѿ�����
     * @param arr
     * @return
     */
    public static boolean isSorted(Comparable[] arr, int lo, int hi){
        for(int i = lo; i < hi; i ++)
            if(arr[i].compareTo(arr[i+1]) > 0) return false;
        return true;
    }
	
    /**
	 * �Ƚ�����Ԫ��
	 * @param arr
	 * @param i
	 * @param j
	 * @return
	 * true  ��� arr[i] <  arr[j]<br>
	 * false ��� arr[i] >= arr[j]<br>
	 */
	public static boolean less(Comparable[] arr, int i,  int j){
		return arr[i].compareTo(arr[j]) < 0;
	}	
	
    /**
     * ��һ�����ֱ���Ϊ����
     * @return
     */
    public static int[] numberToArray(int num) {
        
        String[] arr = String.valueOf(num).split("");
        int[] nums = new int[arr.length];
        
        for (int i = 0; i < arr.length; i++)
            nums[i] = Integer.valueOf(arr[i]);
        return nums;
    }
	
    /**
     * ���µ�������Ĵ�С,Ĭ�ϵ���Ϊԭ���������
     * @param arr
     * @return
     */
    public static int[] resize(int[] arr) { return resize(arr, arr.length*2); }
    
    /**
     * ���µ������ݵĴ�С
     * @param arr
     * @param newSize
     * @return
     */
    public static int[] resize(int[] arr, int newSize) {
        if (newSize < arr.length) throw new IllegalArgumentException("new size must bigger then old size");
        int[] newArr = new int[newSize];
        for (int i = 0; i < arr.length; i++) newArr[i] = arr[i];
        return newArr;
    }
    
    /**
     * ���µ�������Ĵ�С,Ĭ�ϵ���Ϊԭ���������
     * @param arr
     * @return
     */
    public static boolean[] resize(boolean[] arr) { return resize(arr, arr.length*2); }
    
    /**
     * ���µ������ݵĴ�С
     * @param arr
     * @param newSize
     * @return
     */
    public static boolean[] resize(boolean[] arr, int newSize) {
        if (newSize < arr.length) throw new IllegalArgumentException("new size must bigger then old size");
        boolean[] newArr = new boolean[newSize];
        for (int i = 0; i < arr.length; i++) newArr[i] = arr[i];
        return newArr;
    }
    
    /**
     * ���µ�������Ĵ�С,Ĭ�ϵ���Ϊԭ���������
     * @param arr
     * @return
     */
    public static <T> T[] resize(T[] arr) { return resize(arr, arr.length*2); }
    
    /**
     * ���µ������ݵĴ�С
     * @param arr
     * @param newSize
     * @return
     */
    public static <T> T[] resize(T[] arr, int newSize) {
        if (newSize < arr.length) throw new IllegalArgumentException("new size must bigger then old size");
        T[] newArr = (T[]) new Object[newSize];
        for (int i = 0; i < arr.length; i++) newArr[i] = arr[i];
        return newArr;
    }
    
    /**
	 * ��������˳��
	 * @param arr
	 */
	public static <T> void shuffle(T[] arr) {
	    shuffle(arr, 42);
	}
	
	/**
     * ��������˳��
     * @param arr
     */
    public static <T> void shuffle(T[] arr, int seed) {
        Random r = new Random(seed);
        for(int i = 0; i < arr.length; i ++)
            swap(arr, i, r.nextInt(arr.length));
    }
	
	/**
	 * ��������Ԫ��λ��
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static <T> void swap(T[] arr, int i, int j) {
		T temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
    /**
	 * �����������������Ҫ�������������û�н��������Ҵ�Сһ�£�
	 * @param arr 	����
	 * @param l1	����1��ʼ�߽�
	 * @param l2	����2��ʼ�߽�
	 * @param size	�����С
	 */
	public static <T> void swap(T[] arr,  int l1, int l2, int size) {
		for (int i = 0; i < size; i ++)
			swap(arr, l1 + i, l2 + i);
	}
	
    /**
     * ��������Ԫ��λ��
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * �����������������Ҫ�������������û�н��������Ҵ�Сһ�£�
     * @param arr   ����
     * @param l1    ����1��ʼ�߽�
     * @param l2    ����2��ʼ�߽�
     * @param size  �����С
     */
    public static void swap(int[] arr,  int l1, int l2, int size) {
        for (int i = 0; i < size; i ++)
            swap(arr, l1 + i, l2 + i);
    }
    
    /**
     * ��������Ԫ��λ��
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(boolean[] arr, int i, int j) {
        boolean temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * �����������������Ҫ�������������û�н��������Ҵ�Сһ�£�
     * @param arr   ����
     * @param l1    ����1��ʼ�߽�
     * @param l2    ����2��ʼ�߽�
     * @param size  �����С
     */
    public static void swap(boolean[] arr,  int l1, int l2, int size) {
        for (int i = 0; i < size; i ++)
            swap(arr, l1 + i, l2 + i);
    }
	
    public static void main(String[] args) {
        Out.p(numberToArray(12345));
    }
	
}
