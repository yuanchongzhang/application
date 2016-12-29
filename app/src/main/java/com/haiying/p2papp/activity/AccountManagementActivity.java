package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonVip;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import cn.trinea.android.common.util.ToastUtils;

/*
* 账户管理
* */
public class AccountManagementActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvtitle;
    private ImageView ivback;
    private LinearLayout ll01;
    private LinearLayout ll02;
    private LinearLayout ll03;
    private LinearLayout ll04;
    private LinearLayout ll05;
    private LinearLayout ll06;
    private LinearLayout ll07;
    private LinearLayout ll08;

    private TextView et_nicheng;

    String str_nicheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        initView();
        initListener();


    }

    private void initView() {
        this.ll08 = (LinearLayout) findViewById(R.id.ll_08);
        this.ll07 = (LinearLayout) findViewById(R.id.ll_07);
        this.ll06 = (LinearLayout) findViewById(R.id.ll_06);
        this.ll05 = (LinearLayout) findViewById(R.id.ll_05);
        this.ll04 = (LinearLayout) findViewById(R.id.ll_04);
        this.ll03 = (LinearLayout) findViewById(R.id.ll_03);
        this.ll02 = (LinearLayout) findViewById(R.id.ll_02);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);


    }

    private void initListener() {
        ll01.setOnClickListener(this);
        ll02.setOnClickListener(this);
        ll03.setOnClickListener(this);
        ll04.setOnClickListener(this);
        ll05.setOnClickListener(this);
        ll06.setOnClickListener(this);
        ll07.setOnClickListener(this);
        ll08.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_01:
                startVerifyActivity(PersonalSettingsActivity.class);
                break;
            case R.id.ll_02:
                startVerifyActivity(AmountApplyActivity.class);
                break;
            case R.id.ll_03:
                startVerifyActivity(PasswordManageActivity.class);
                break;
            case R.id.ll_04:
                startVerifyActivity(SafetyCertificateActivity.class);
                break;
            case R.id.ll_05:
                if (MyApplication.myPreferences.readVip_status().equals("0")) {
                    startVerifyActivity(VipApplyActivity.class);
                } else if (MyApplication.myPreferences.readVip_status().equals("2")) {
                    ToastUtils.show(this, "审核中");
                } else {
                    getVipDataAccessToken();
                }

                break;
            case R.id.ll_06:
                startVerifyActivity(BankcardManagementActivity.class);
                break;
            case R.id.ll_07:
                startVerifyActivity(InviteFriendsActivity.class);
                break;
            case R.id.ll_08:
               startVerifyActivity(MsgListActivity.class);
                break;
        }
    }

    private void getVipDataAccessToken() {
        new JsonAccessToken("user/vip", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( AccountManagementActivity.this, "安全验证失败！");
            }
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        getVipData(info.access_token);
                    } else {
                        UtilToast.show( AccountManagementActivity.this, "安全验证失败！");
                    }
                }
            }
        }).execute(this,true);

    }


    private void getVipData(String access_token) {
        new JsonVip(access_token,MyApplication.myPreferences.readUid(), new AsyCallBack<JsonVip.Info>() {
            @Override
            public void onFail(String toast, int type) throws Exception {
                ToastUtils.show(AccountManagementActivity.this, JsonVip.TOAST);
            }
        }).execute(AccountManagementActivity.this, true);
    }
}
