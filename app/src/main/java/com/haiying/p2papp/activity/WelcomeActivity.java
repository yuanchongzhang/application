package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.haiying.p2papp.MyApplication;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler() {

            @Override
            public void handleMessage(Message msg) {

                startVerifyActivity(MyApplication.guidePreferences.readIsGuide() ? MainActivity.class : GuideActivity.class);

                finish();

            }

        }.sendEmptyMessageDelayed(0, 3000);

    }

}
