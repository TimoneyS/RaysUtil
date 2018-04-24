package com.ray.util;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CompareUtil {
	
	public static boolean less(Comparable x, Comparable y){
		return x.compareTo(y) < 0;
	}
	
	public static boolean greater(Comparable x, Comparable y) {
		return x.compareTo(y) > 0;
	}
	
	public static boolean equals(Comparable x, Comparable y) {
		return x.compareTo(y) == 0;
	}
	
	public static Comparable min(Comparable a, Comparable b) {
	    if (less(a,b)) return a;
	    else return b;
	}
	
    public static <T extends Comparable<T>> T max(T a, T b) {
        if (less(a,b)) return b;
        else return a;
    }
	
}
