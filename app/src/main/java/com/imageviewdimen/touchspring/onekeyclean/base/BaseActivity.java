package com.imageviewdimen.touchspring.onekeyclean.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

import com.imageviewdimen.touchspring.onekeyclean.Dialogs.ProgressDialogFragment;
import com.imageviewdimen.touchspring.onekeyclean.utils.MyToast;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by KIM on 2015/6/19 0019.
 */
public class BaseActivity extends FragmentActivity {
    private int mScreenWidth;
    private int mScreenHeight;
    private float mDensity;

    public  String logName;
    private Context mContext;
    protected ActivityTack tack = ActivityTack.getInstance();
    private static String mDialogTag = "basedialog";
    ProgressDialogFragment mProgressDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        mDensity = metrics.density;
        logName = this.getClass().getSimpleName();
        mContext = this;
        tack.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
    }

    /**
     * 通过class跳转Activity
     * @param cls
     */
    protected  void startActivity(Class<?> cls){
        startActivity(cls, null);
    }

    /**
     * 带有bundle的跳转
     * @param cls
     * @param bundle
     */
    protected  void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(mContext,cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected  void startActivity(String action){
        startActivity(action,null);
    }

    protected  void startActivity(String action,Bundle bundle){
        Intent intent = new Intent();
        intent.setAction(action);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(action, bundle);
    }

    public void showDialogLoading(){
         showDialogLoading(null);
    }

    public void showDialogLoading(String message){
        if(mProgressDialogFragment == null){
            mProgressDialogFragment = ProgressDialogFragment.newInstance(0,null);
        }
        if(message != null){
            mProgressDialogFragment.setMessage(message);
        }

        mProgressDialogFragment.show(getSupportFragmentManager(),mDialogTag);

    }


    public void dismissDialogLoading() {
        if (mProgressDialogFragment != null) {
            mProgressDialogFragment.dismiss();
        }
    }

    /**
     * showToast
     */
    public void showShort(String message){
         MyToast.showShort(mContext, message);
    }

    public void showLong(String message){
        MyToast.showShort(mContext, message);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //友盟提供用户终端操作行为
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟提供用户终端操作行为
        MobclickAgent.onResume(this);
    }

    @Override
    public void finish() {
        super.finish();
        tack.removeActivity(this);
    }
}
