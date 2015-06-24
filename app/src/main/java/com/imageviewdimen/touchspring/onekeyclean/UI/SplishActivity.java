package com.imageviewdimen.touchspring.onekeyclean.UI;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import com.imageviewdimen.touchspring.onekeyclean.R;
import com.imageviewdimen.touchspring.onekeyclean.base.BaseActivity;

import java.util.Random;

/**
 * Created by KIM on 2015/6/23 0023.
 */
public class SplishActivity extends BaseActivity {
    ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_splash);
        initview();

    }

    private void initview() {
        mImageView = (ImageView)findViewById(R.id.img_splash);
        int index = new Random().nextInt(2);

    }
}
