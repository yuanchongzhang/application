package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonTendindex;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

public class InvestmentManagementActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private TextView tv01;
    private TextView tv02;
    private LinearLayout ll01;
    private TextView tv03;
    private LinearLayout ll02;
    private TextView tv04;
    private LinearLayout ll03;
    private TextView tv05;
    private LinearLayout ll04;
    private TextView tv06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_management);
        initView();
        initListener();
        getAccessToken();

    }

    private void getAccessToken() {
        new JsonAccessToken("user/tendindex", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(InvestmentManagementActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(InvestmentManagementActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }



    private void initData(String access_token) {
        new JsonTendindex(access_token,MyApplication.myPreferences.readUid(), new AsyCallBack<JsonTendindex.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonTendindex.Info info) throws Exception {
                if (info != null) {
                    tv02.setText(info.total_tending + "元");
                    tv03.setText(info.total_tendbacking + "元");
                    tv04.setText(info.total_tenddone + "元");
                    tv05.setText(info.total_tendbreak + "元");
                    tv06.setText(info.total_all + "元");
                } else {
                    UtilToast.show(InvestmentManagementActivity.this, JsonTendindex.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(InvestmentManagementActivity.this, false);
    }

    private void initListener() {
        ll01.setOnClickListener(this);
        ll02.setOnClickListener(this);
        ll03.setOnClickListener(this);
        ll04.setOnClickListener(this);
    }

    private void initView() {
        this.tv06 = (TextView) findViewById(R.id.tv_06);
        this.ll04 = (LinearLayout) findViewById(R.id.ll_04);
        this.tv05 = (TextView) findViewById(R.id.tv_05);
        this.ll03 = (LinearLayout) findViewById(R.id.ll_03);
        this.tv04 = (TextView) findViewById(R.id.tv_04);
        this.ll02 = (LinearLayout) findViewById(R.id.ll_02);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_01:
                intent.putExtra("id", 0);
                break;
            case R.id.ll_02:
                intent.putExtra("id", 1);
                break;
            case R.id.ll_03:
                intent.putExtra("id", 2);
                break;
            case R.id.ll_04:
                intent.putExtra("id", 3);
                break;
        }
        intent.setClass(this, InvestManageDetailActivity.class);
        startActivity(intent);
    }
}
