package cn.orange.core.util;

import android.util.Log;

import cn.orange.core.BuildConfig;

/**
 * Created by Orange on 2019/4/1.
 * Email:addskya@163.com
 */
public class LogUtil {

    public static boolean sDebug = BuildConfig.DEBUG;

    public static void i(String tag, String message) {
        if (sDebug) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (sDebug) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (sDebug) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (sDebug) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable error) {
        if (sDebug) {
            Log.e(tag, message, error);
        }
    }
}
