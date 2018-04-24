package com.ray.util;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class RFrame {
    
    public static JFrame commonFrame(String name) {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }
    
    public static void showFrame(JFrame frame) {
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * ˢ��Frame
     */
    public static void refreshFrame(JFrame frame, int delay, TimeUnit unit) {
        // Ԥ��ˢ��
        Executors
        .newSingleThreadScheduledExecutor()
        .scheduleWithFixedDelay(
                () -> frame.repaint(),
                100,
                delay,
                unit
            );
    }
    
}
