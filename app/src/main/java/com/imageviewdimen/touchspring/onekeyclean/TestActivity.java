package com.imageviewdimen.touchspring.onekeyclean;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by KIM on 2015/6/24 0024.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : appProcessInfos) {
            Log.i("TESTACTI","precess :"  + info.processName);
        }
    }
}
