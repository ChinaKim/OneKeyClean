package com.imageviewdimen.touchspring.onekeyclean.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.imageviewdimen.touchspring.onekeyclean.R;
import com.imageviewdimen.touchspring.onekeyclean.bean.AppProcessInfo;

import java.util.List;

/**
 * Created by KIM on 2015/6/24 0024.
 */
public class CoreService extends Service {
    private Context mContext;
    private PackageManager packageManager;
    private ActivityManager activityManager;

    List<AppProcessInfo> list = null;

    public static final String ACTION_CLEAN_AND_EXIT = "com.yzy.service.cleaner.CLEAN_AND_EXIT";

    private static final String TAG = "CleanerService";

    private ProcessServiceBinder mBinder = new ProcessServiceBinder();

    public class ProcessServiceBinder extends Binder {
        public CoreService getService() {
            return CoreService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        packageManager = getPackageManager();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            if (action.equals(ACTION_CLEAN_AND_EXIT)) {
                setOnActionListener(new OnPeocessActionListener() {
                    @Override
                    public void onScanStarted(Context context) {

                    }

                    @Override
                    public void onScanProgressUpdated(Context context, int current, int max) {

                    }

                    @Override
                    public void onScanCompleted(Context context, List<AppProcessInfo> apps) {

                    }

                    @Override
                    public void onCleanStarted(Context context) {

                    }

                    @Override
                    public void onCleanCompleted(Context context, long cacheSize) {
                        String msg = getString(R.string.cleaned, Formatter.formatShortFileSize(CoreService.this, cacheSize));
                        Log.d(TAG, "onCleanCompleted：" + msg);

                        Toast.makeText(CoreService.this, msg, Toast.LENGTH_LONG).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                stopSelf();
                            }
                        }, 5000);
                    }
                });
                scanRunProcess();
            }
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void scanRunProcess() {
        new TaskScan().execute();
    }

    /**
     * 扫描
     */
    class TaskScan extends  AsyncTask<Void,Integer,List<AppProcessInfo>>{
        private int mAppCount = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(peocessActionListener != null){
                peocessActionListener.onScanStarted(CoreService.this);
            }
        }

        @Override
        protected List<AppProcessInfo> doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(List<AppProcessInfo> appProcessInfos) {
            super.onPostExecute(appProcessInfos);
        }
    }

    /**
     * 清理
     */
    class TaskClean extends AsyncTask<Void, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (peocessActionListener != null) {
                peocessActionListener.onCleanStarted(CoreService.this);
            }
        }

        @Override
        protected Long doInBackground(Void... params) {
            long beforeMemory = 0;
            long endMemory = 0;
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            beforeMemory = memoryInfo.availMem;
            List<ActivityManager.RunningAppProcessInfo> appProcessList =  activityManager.getRunningAppProcesses();
            for(ActivityManager.RunningAppProcessInfo info : appProcessList){
                killBackgroundProgress(info.processName);
            }
            activityManager.getMemoryInfo(memoryInfo);
            endMemory = memoryInfo.availMem;
            return endMemory - beforeMemory ;
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            if(peocessActionListener != null){
                peocessActionListener.onCleanCompleted(CoreService.this,result);
            }
        }
    }


    private void killBackgroundProgress(String processName) {
        String packageName;
        if(processName.indexOf(":") == -1){
            packageName = processName;
        }else{
            packageName = processName.split(":")[0];
        }
        activityManager.killBackgroundProcesses(packageName);
    }

    private OnPeocessActionListener peocessActionListener;


    public static interface OnPeocessActionListener {
        public void onScanStarted(Context context);

        public void onScanProgressUpdated(Context context, int current, int max);

        public void onScanCompleted(Context context, List<AppProcessInfo> apps);

        public void onCleanStarted(Context context);

        public void onCleanCompleted(Context context, long cacheSize);
    }


    public void setOnActionListener(OnPeocessActionListener listener) {
        peocessActionListener = listener;
    }

}
