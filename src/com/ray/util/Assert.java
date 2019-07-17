package com.ray.util;

/**
 * Simple Assert Util
 * 
 * 
 * @author rays1
 *
 */
public class Assert {
    
    public static <T> void assertNotNull(T obj) {
        if (obj == null) exception("object is null");
    }
    
    public static <T> void assertEquals(T o1, T o2) {
        if (o1 == null) exception("o1 is null");
        if (o2 == null) exception("o2 is null");        
        if (!o1.equals(o2)) exception("o1 is not equals o2");
    }
    
    private static void exception(String msg) {
        throw new RuntimeException(msg);
    }
    
}
