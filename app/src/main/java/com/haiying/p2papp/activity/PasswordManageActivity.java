package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;

public class PasswordManageActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvtitle;
    private ImageView ivback;
    private ImageView ivpm01;
    private TextView tv01;
    private ImageView ivpm02;


    LinearLayout change_password_layout;

    LinearLayout transaction_password_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manage);
        initView();
        initListener();
    }



    private void initView() {
        this.ivpm02 = (ImageView) findViewById(R.id.iv_pm_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivpm01 = (ImageView) findViewById(R.id.iv_pm_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        change_password_layout= (LinearLayout) findViewById(R.id.change_password_layout);
        transaction_password_layout= (LinearLayout) findViewById(R.id.transaction_password_layout);
        if (MyApplication.myPreferences.readPin_pass().equals("1")) {
            tv01.setText("已设置");
        } else {
            tv01.setText("未设置");
        }
    }

    private void initListener() {
        ivpm01.setOnClickListener(this);
        ivpm02.setOnClickListener(this);
        change_password_layout.setOnClickListener(this);
        transaction_password_layout.setOnClickListener(this);
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

            case R.id.iv_pm_01:
                intent.setClass(this, PasswordModifyActivity.class);



                break;
            case R.id.change_password_layout:

                intent.setClass(this, PasswordModifyActivity.class);
                break;

            case R.id.iv_pm_02:
                intent.setClass(this, PasswordTransactionActivity.class);
                break;

            case R.id.transaction_password_layout:
                intent.setClass(this, PasswordTransactionActivity.class);
                break;

        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.myPreferences.readPin_pass().equals("1")) {
            tv01.setText("已设置");
        } else {
            tv01.setText("未设置");
        }
    }
}
