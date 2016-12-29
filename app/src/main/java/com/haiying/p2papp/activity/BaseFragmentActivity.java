package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.haiying.p2papp.MyApplication;
import com.zcx.helper.activity.AppV4Activity;
import com.zcx.helper.bound.BoundViewHelper;

/**
 * Created by Administrator on 2/17/2016.
 */
public class BaseFragmentActivity extends AppV4Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.INSTANCE.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) getWindow().getDecorView()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.INSTANCE.finishActivity(this);
    }

    public void onBack(View view){
        finish();
    }
}
