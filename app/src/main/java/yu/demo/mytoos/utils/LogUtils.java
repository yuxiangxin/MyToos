package yu.demo.mytoos.utils;

import android.util.Log;

public class LogUtils {

    public static final boolean DEBUG = true;

    public static void v (String tag, String msg) {
        if (DEBUG) {
            //改数据error
            Log.v(tag, msg);
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
        Log.v(tag, msg);
    }

    public static void d (String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void i (String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void w (String tag, String msg) {
        if (DEBUG)
            Log.w(tag, msg);
    }

    public static void e (String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

    public static void e (Exception e) {
        e("LogUtils:Exception", e.getMessage());
    }

    public static void e (String tag, Exception e) {
        if (DEBUG)
            e.printStackTrace();
    }

    public static void e (String tag, String msg, Exception e) {
        e(tag, msg);
        if (DEBUG)
            e.printStackTrace();
    }

    public static void e (String tag, String msg, Throwable e) {
        e(tag, msg);
        if (DEBUG)
            e.printStackTrace();
    }
}