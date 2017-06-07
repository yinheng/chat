package com.yinheng.logger;

/**
 * Created by 尹恒 on 2017/5/27.
 */
public class Logger {

    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(Object o) {
        System.out.println(o);
    }

    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
}
