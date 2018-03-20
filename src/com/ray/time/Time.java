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
    private static DateFormat dateFormat1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS");
    /**
     * 创建日期对象
     * @param yy    年份
     * @param mm    月份
     * @param dd    日期
     * @param hh    小时
     * @param mi    分钟
     * @param ss    秒分
     * @param ms    毫秒
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
     * 创建日期对象
     * @param yy    年
     * @param mm    月
     * @param dd    日
     * @param hh    时
     * @param mi    分
     * @param ss    秒
     * @return
     */
    public static Date create(int yy, int mm, int dd, int hh, int mi, int ss) {
        return create(yy, mm, dd, hh, mi, ss, 0);
    }
    
    /**
     * 创建日期对象
     * @param yy    年
     * @param mm    月
     * @param dd    日
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
