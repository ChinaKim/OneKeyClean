package com.imageviewdimen.touchspring.onekeyclean.base;

import android.app.Activity;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

/**
 * 栈管理
 * Created by KIM on 2015/6/23 0023.
 */
public class ActivityTack {

    private ArrayList<Activity> activityArrayList = new ArrayList<Activity>();
    private static ActivityTack tack = new ActivityTack();

    public static ActivityTack getInstance() {
        return tack;
    }

    private ActivityTack() {}
    public  void addActivity(Activity activity){
        activityArrayList.add(activity);
    }

    public  void removeActivity(Activity activity){
        activityArrayList.remove(activity);
    }


    /**
     * 完全退出
     */
    public void exit(Context context){
        MobclickAgent.onKillProcess(context);
        while(activityArrayList.size() > 0 ){
            activityArrayList.get(activityArrayList.size() - 1).finish();
        }
        System.exit(0);
    }

    /**
     * 根据className获取activity
     */
    public Activity getActivityByName(String className){
        for(Activity ac : activityArrayList){
            if(ac.getClass().getName().equals(className)){
                return ac;
            }
        }
        return null;
    }

    /**
     * 根据class 获取activity
     * @param cl
     * @return
     */
    public Activity getActivityByClass(Class cl){
        for(Activity ac : activityArrayList){
            if(ac.getClass().equals(cl)){
                return ac;
            }
        }
        return null;
    }











}
