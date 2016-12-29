package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;

public class MyAssetsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private TextView tvtitleright;
    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    private TextView tv04;
    private Button bt01;
    private Button bt02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assets);
        initView();
        initListener();
    }

    private void initView() {
        this.bt02 = (Button) findViewById(R.id.bt_02);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.tv04 = (TextView) findViewById(R.id.tv_04);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.tvtitleright = (TextView) findViewById(R.id.tv_title_right);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        tv01.setText("￥"+MyApplication.myPreferences.readAccount_money() + "元");
        tv02.setText("￥"+MyApplication.myPreferences.readMoney_collect() + "元");
        tv03.setText("￥"+MyApplication.myPreferences.readMoney_freeze() + "元");
        tv04.setText("￥"+MyApplication.myPreferences.readAll_money() + "元");

    }

    private void initListener() {
        tvtitleright.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_title_right:
                intent.setClass(this, MyAssetsHistoryActivity.class);
                break;
            case R.id.bt_01:
                intent.putExtra("mode", false);//false为充值
                intent.setClass(this, WithdrawOrRechargeActivity.class);
                break;
            case R.id.bt_02:
                intent.putExtra("mode", true);//true为提现
                intent.setClass(this, WithdrawOrRechargeActivity.class);
                break;
        }
        startActivity(intent);
    }
}
