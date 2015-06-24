package com.imageviewdimen.touchspring.onekeyclean.base;

import android.app.Application;

/**
 * Created by KIM on 2015/6/23 0023.
 */
public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    public BaseApplication getInstance(){
        return baseApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
