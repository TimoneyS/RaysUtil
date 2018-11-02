package com.ray.util;

import java.util.Random;

import com.ray.io.Out;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ArrayUtil {
	
	/**
     * 检查数组是否已经排序
     * @param arr
     */
    public static void checkSorted(Comparable[] arr) {
    	Out.pf("经检查，数组%s排序。\n", isSorted(arr) ? "已" : "未");
    }
	
	   /**
     * 检查数组局部是否已经排序
     * @param arr
     */
    public static void checkSorted(Comparable[] arr, int lo, int hi) {
        Out.pf("经检查，数组%s排序。\n", isSorted(arr, lo, hi) ? "已" : "未");
    }
    
    /**
     * 数组最小元素的索引
     * @param arr
     * @param i
     * @param j
     */
    public static <T> int indexOfMin(Comparable[] arr) {
        return indexOfMin(arr, 0, arr.length-1);
    }
	
	/**
	 * 某一区域内最小元素的索引
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
     * 数组最小元素的索引
     * @param arr
     * @param i
     * @param j
     */
    public static int indexOfMin(int[] arr) {
        return indexOfMin(arr, 0, arr.length-1);
    }
    
    /**
     * 某一区域内最小元素的索引
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
     * 获取 整形数组
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
	 * 获取整形旋转数组，可以指定旋转的位置
	 * @param size			数组尺寸
	 * @param deviation		偏移量
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
	 * 获取 整形数组
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
	 * 检查数组是否已经排序
	 * @param arr
	 * @return
	 */
	public static boolean isSorted(Comparable[] arr){
	    return isSorted(arr, 0, arr.length - 1);
	}
	
	/**
     * 检查数组局部是否已经排序
     * @param arr
     * @return
     */
    public static boolean isSorted(Comparable[] arr, int lo, int hi){
        for(int i = lo; i < hi; i ++)
            if(arr[i].compareTo(arr[i+1]) > 0) return false;
        return true;
    }
	
    /**
	 * 比较数组元素
	 * @param arr
	 * @param i
	 * @param j
	 * @return
	 * true  如果 arr[i] <  arr[j]<br>
	 * false 如果 arr[i] >= arr[j]<br>
	 */
	public static boolean less(Comparable[] arr, int i,  int j){
		return arr[i].compareTo(arr[j]) < 0;
	}	
	
    /**
     * 将一串数字保存为数组
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
     * 重新调整数组的大小,默认调整为原数组的两倍
     * @param arr
     * @return
     */
    public static int[] resize(int[] arr) { return resize(arr, arr.length*2); }
    
    /**
     * 重新调整数据的大小
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
     * 重新调整数组的大小,默认调整为原数组的两倍
     * @param arr
     * @return
     */
    public static boolean[] resize(boolean[] arr) { return resize(arr, arr.length*2); }
    
    /**
     * 重新调整数据的大小
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
     * 重新调整数组的大小,默认调整为原数组的两倍
     * @param arr
     * @return
     */
    public static <T> T[] resize(T[] arr) { return resize(arr, arr.length*2); }
    
    /**
     * 重新调整数据的大小
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
	 * 打乱数组顺序
	 * @param arr
	 */
	public static <T> void shuffle(T[] arr) {
	    shuffle(arr, 42);
	}
	
	/**
     * 打乱数组顺序
     * @param arr
     */
    public static <T> void shuffle(T[] arr, int seed) {
        Random r = new Random(seed);
        for(int i = 0; i < arr.length; i ++)
            swap(arr, i, r.nextInt(arr.length));
    }
	
	/**
	 * 交换数组元素位置
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
	 * 交换数组的两个区域（要求两个区域必须没有交集，而且大小一致）
	 * @param arr 	数组
	 * @param l1	区域1开始边界
	 * @param l2	区域2开始边界
	 * @param size	区域大小
	 */
	public static <T> void swap(T[] arr,  int l1, int l2, int size) {
		for (int i = 0; i < size; i ++)
			swap(arr, l1 + i, l2 + i);
	}
	
    /**
     * 交换数组元素位置
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
     * 交换数组的两个区域（要求两个区域必须没有交集，而且大小一致）
     * @param arr   数组
     * @param l1    区域1开始边界
     * @param l2    区域2开始边界
     * @param size  区域大小
     */
    public static void swap(int[] arr,  int l1, int l2, int size) {
        for (int i = 0; i < size; i ++)
            swap(arr, l1 + i, l2 + i);
    }
    
    /**
     * 交换数组元素位置
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
     * 交换数组的两个区域（要求两个区域必须没有交集，而且大小一致）
     * @param arr   数组
     * @param l1    区域1开始边界
     * @param l2    区域2开始边界
     * @param size  区域大小
     */
    public static void swap(boolean[] arr,  int l1, int l2, int size) {
        for (int i = 0; i < size; i ++)
            swap(arr, l1 + i, l2 + i);
    }
	
    public static void main(String[] args) {
        Out.p(numberToArray(12345));
    }
	
}
