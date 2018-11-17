package com.ray.util;

/**
 * 字符串辅助工具
 * @author rays1
 *
 */
public class StringUtil {
    
    /**
     * 将字符串重复n次
     * @param s
     * @param times
     * @return
     */
    public static String multiString(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++)
            sb.append(s);
        return sb.toString();
    }
    
    /**
     * 将字符重复n次
     * @param c
     * @param times
     * @return
     */
    public static String multiString(char c, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++)
            sb.append(c);
        return sb.toString();
    }
    
}
