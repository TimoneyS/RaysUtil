package com.ray.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ray.io.Out;

/**
 * 计时器类
 * @author Ray
 *
 */
public class Timer {
	
    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss:ms]");
	
	private String name;
    private int record_count = 0;
    private long[] dateArr = new long[16];

    private static final Timer DEFAULT = create("TIMER");
    
    public static void CLICK() {
        DEFAULT.click();
    }
    
    public static void STOP() {
        DEFAULT.stop();
    }
    
    
    /**
	 * 时间戳
	 */
	public static String tag() {
        return DATE_FORMATTER.format(new Date());
	}
	
	public static Timer create(String name) {
	    return new Timer(name);
	}
	
	private Timer(String name) {
	    if (name == null) name = "";
	    this.name = name;
    }
	
	/**
     * "按下" 计时器<br/>
     * 第一次按下 开始记录时间，之后每次按下将显示距离上一次的时间，并开始下一次计时
     */
	public void click() {
        adjustDateArray();
        dateArr[record_count ++] = new Date().getTime();
	}
	
    /**
     * "停止" 计时器
     */
    public void stop() {
        click();
        show();
        record_count = 0;
    }
    
    /**
     * "显示" 计时器结果
     */
    public void show() {
       for (int i = 1; i < record_count; i++) {
           Out.pf("[%s]第%2d次耗时 %s ms\n", name, i, dateArr[i] - dateArr[i-1]);
       } 
    }
    
    /**
     * 调整数组
     */
    private void adjustDateArray() {
        if (record_count+1 >= dateArr.length) {
            long[] temp = new long[dateArr.length * 2];
            for (int i = 0; i < dateArr.length; i++) {
                temp[i] = dateArr[i];
            }
            dateArr = temp;
        }
    }
    
    public static void main(String[] args) {
        Timer.CLICK();
    }
	   
}
