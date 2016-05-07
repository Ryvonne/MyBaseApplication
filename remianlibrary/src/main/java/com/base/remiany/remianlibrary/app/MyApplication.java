package com.base.remiany.remianlibrary.app;

import android.app.Application;

public class MyApplication extends Application {
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
        CrashHandler crashHandler = CrashHandler.getInstance();
        //注册crashHandler
        crashHandler.init(getApplicationContext());
    }
}
