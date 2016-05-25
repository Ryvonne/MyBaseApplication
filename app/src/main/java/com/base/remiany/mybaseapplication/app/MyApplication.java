package com.base.remiany.mybaseapplication.app;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    public static String TAG = "MyApplication";
    private static Map<String,Activity> mActMap;
    private static MyApplication instance;

    public static MyApplication instance() {
        if (instance == null) {
            throw new IllegalStateException("Application has not been created");
        }

        return instance;
    }

    public static MyApplication _instance() {
        return instance;
    }

    public MyApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActMap = new HashMap<>();
        CrashHandler crashHandler = CrashHandler.getInstance();
        //注册crashHandler
        crashHandler.init(getApplicationContext());
    }

    public static void addActivity(String name,Activity act) {
        Log.i(TAG,"add "+name);
        mActMap.put(name,act);
    }

    public static Activity removeActivity(String name) {
        return mActMap.remove(name);
    }
}
