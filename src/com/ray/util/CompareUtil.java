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
	
}