package com.ray.util;

/**
 * �ַ�����������
 * @author rays1
 *
 */
public class StringUtil {
    
    /**
     * ���ַ����ظ�n��
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
     * ���ַ��ظ�n��
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
