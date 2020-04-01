package com.ray.io;

/**
 * 路径辅助类
 * @author rays1
 *
 */
public class Dir {
    public static String getClassPath(Class<?> cls) {
        return cls.getResource("").getPath();
    }
    
    public static String getSourcePath(Class<?> cls) {
        return cls.getResource("").getPath().replaceAll("/bin/", "/src/");
    }
}
