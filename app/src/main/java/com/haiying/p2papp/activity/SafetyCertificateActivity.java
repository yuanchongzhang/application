package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.zcx.helper.util.UtilMatches;

import cn.trinea.android.common.util.ToastUtils;

public class SafetyCertificateActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private TextView tv01;
    private ImageView ivam01;
    private TextView tv02;
    private ImageView ivam02;
    private ImageView iv01;
    private ImageView iv02;


    private LinearLayout real_name_layout;

    private LinearLayout bind_mobil_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_certificate);
        initView();
        initListener();
    }

    private void initView() {
        this.ivam02 = (ImageView) findViewById(R.id.iv_am_02);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.iv02 = (ImageView) findViewById(R.id.iv_02);
        this.ivam01 = (ImageView) findViewById(R.id.iv_am_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);

        bind_mobil_layout = (LinearLayout) findViewById(R.id.bind_mobil_layout);

        bind_mobil_layout.setOnClickListener(this);
        real_name_layout = (LinearLayout) findViewById(R.id.real_name_layout);
        real_name_layout.setOnClickListener(this);

        real_name_layout.setSelected(true);
        iv02.setSelected(true);
        if (MyApplication.myPreferences.readId_status().equals("0")) {
            iv01.setSelected(false);
            tv01.setText("未认证");
        } else if (MyApplication.myPreferences.readId_status().equals("2")) {
            iv01.setSelected(false);
            tv01.setText("审核中");
        } else if (MyApplication.myPreferences.readId_status().equals("0")) {
            real_name_layout.setSelected(false);
        } else if (MyApplication.myPreferences.readId_status().equals("2")) {
            real_name_layout.setSelected(false);
        } else {
            iv01.setSelected(true);
//            tv01.setText(MyApplication.myPreferences.readReal_name());
            tv01.setText("已认证");
            ivam01.setVisibility(View.VISIBLE);

            real_name_layout.setSelected(true);

        }
        String phone = MyApplication.myPreferences.readUser_phone();
        if (!TextUtils.isEmpty(phone) && UtilMatches.checkMobile(phone)) {
            tv02.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
        }


    }

    private void initListener() {
        ivam01.setOnClickListener(this);
        ivam02.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_am_01:
                if (MyApplication.myPreferences.readId_status().equals("0")) {
                    startVerifyActivity(VerifiedActivity.class);

                } else if (MyApplication.myPreferences.readId_status().equals("1")) {
                    ToastUtils.show(this, "您已通过实名认证");
                } else if (MyApplication.myPreferences.readId_status().equals("2")) {
                    ToastUtils.show(this, "您的实名认证申请正在审核中");
                }
                break;
            case R.id.iv_am_02:
                startVerifyActivity(ChangePhoneNumbersActivity.class);
                break;
            case R.id.real_name_layout:
                if (MyApplication.myPreferences.readId_status().equals("0")) {
                    startVerifyActivity(VerifiedActivity.class);

                } else if (MyApplication.myPreferences.readId_status().equals("1")) {
                    ToastUtils.show(this, "您已通过实名认证");
                } else if (MyApplication.myPreferences.readId_status().equals("2")) {
                    ToastUtils.show(this, "您的实名认证申请正在审核中");
                }
                break;

            case R.id.bind_mobil_layout:
                startVerifyActivity(ChangePhoneNumbersActivity.class);
                break;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.myPreferences.readId_status().equals("0")) {
            iv01.setSelected(false);
            tv01.setText("未认证");
        } else if (MyApplication.myPreferences.readId_status().equals("2")) {
            iv01.setSelected(false);
            tv01.setText("审核中");
        } else {
            iv01.setSelected(true);

            //         tv01.setText(MyApplication.myPreferences.readReal_name());
            tv01.setText("已认证");
            ivam01.setVisibility(View.VISIBLE);
        }
        String phone = MyApplication.myPreferences.readUser_phone();
        if (!TextUtils.isEmpty(phone) && UtilMatches.checkMobile(phone)) {
            tv02.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
        }


    }
}
