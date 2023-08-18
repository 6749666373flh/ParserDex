package com.fan.parserdex;

import android.content.Context;

public class App extends android.app.Application{
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.e("fan","app onCreate");
        instance = this;
    }

    public static Context getApplication() {
        return instance.getApplicationContext();
    }
}
