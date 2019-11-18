package yu.demo.mytoos.utils;


import android.util.Log;

public class LogUtils {

    public static final boolean DEBUG = true;

    public static void v (String tag, String msg) {
        if (DEBUG) {
            //改数据error
            System.out.println(tag + ": " + msg);
        }
    }

    public static void v (String tag, String msg, Object... args) {
        if (!DEBUG) {
            return;
        }
        if (args.length > 0) {
            msg = String.format(msg, args);
        }
        //改数据error
        System.out.println(tag + ": " + msg);
    }

    public static void d (String tag, String msg) {
        if (DEBUG)
            System.out.println(tag + ": " + msg);
    }

    public static void i (String tag, String msg) {
        if (DEBUG)
            System.out.println(tag + ": " + msg);
    }

    public static void w (String tag, String msg) {
        if (DEBUG)
            System.out.println(tag + ": " + msg);
    }

    public static void e (String tag, String msg) {
        if (DEBUG)
            System.out.println(tag + ": " + msg);
    }

    public static void e (Exception e) {
        e("LogUtils:Exception", e.getMessage());
    }

    public static void e (String tag, Exception e) {
        if (DEBUG)
            e.printStackTrace();
    }

    public static void e (String tag, String msg, Exception e) {
        System.out.println(tag + ": " + msg);
        if (DEBUG)
            e.printStackTrace();
    }

    public static void e (String tag, String msg, Throwable e) {
        System.out.println(tag + ": " + msg);
        if (DEBUG)
            e.printStackTrace();
    }
}