package com.nhom22.findhostel;

import android.app.Application;

public class YourApplication extends Application {
    private static YourApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static YourApplication getInstance() {
        return instance;
    }

}