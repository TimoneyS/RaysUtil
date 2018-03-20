package com.ray.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ray.util.io.Out;

public class Time extends Date {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5721385854718119819L;
    private static DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    private static DateFormat dateFormat1 = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��SS");
    /**
     * �������ڶ���
     * @param yy    ���
     * @param mm    �·�
     * @param dd    ����
     * @param hh    Сʱ
     * @param mi    ����
     * @param ss    ���
     * @param ms    ����
     * @return
     */
    public static Date create(int yy, int mm, int dd, int hh, int mi, int ss, int ms) {
        try {
            return dateFormat2.parse(
                    String.format("%d-%d-%d %d:%d:%d:%d",
                    yy, mm, dd, hh, mi, ss, ms )
                    );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * �������ڶ���
     * @param yy    ��
     * @param mm    ��
     * @param dd    ��
     * @param hh    ʱ
     * @param mi    ��
     * @param ss    ��
     * @return
     */
    public static Date create(int yy, int mm, int dd, int hh, int mi, int ss) {
        return create(yy, mm, dd, hh, mi, ss, 0);
    }
    
    /**
     * �������ڶ���
     * @param yy    ��
     * @param mm    ��
     * @param dd    ��
     * @return
     */
    public static Date create(int yy, int mm, int dd) {
        return create(yy, mm, dd, 0, 0, 0, 0);
    }
    
    public static String formate(Date date) {
        return dateFormat1.format(date);
    }
    
    public static void main(String[] args) {
        Date t = Time.create(2018, 10, 20, 19, 38, 10, 1);
        Out.p(Time.formate(t));
        
        t = Time.create(2018, 10, 20, 19, 38, 10, 2);
        Out.p(Time.formate(t));
    }
    
}
