package com.base.remiany.remianlibrary.utils;


/**
 * Created by wangxiaoyan on 15/4/20.
 */
public class Log {

    public final static String TAG = "remiany";

    public static int LEVEL = android.util.Log.VERBOSE;

    public static boolean isDebug() {
        return Environment.isDebug();
    }

    public static void i(String msg) {
        android.util.Log.i(TAG, msg);
    }

    public static void e(String msg) {
        android.util.Log.e(TAG, msg);
    }
}
