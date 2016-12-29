package com.haiying.p2papp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBanksave;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import cn.trinea.android.common.util.ToastUtils;

public class BankcardAddActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private SimpleDraweeView ivlogo;
    private TextView tv01;
    private LinearLayout ll01;
    private EditText et01;
    private EditText et02;
    private EditText et03;
    private Button bt01;
    private EditText et04;
    private String bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard_add);

        initView();
        initListener();
    }

    private void initListener() {
        ll01.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    private void initView() {
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.et04 = (EditText) findViewById(R.id.et_04);
        this.et03 = (EditText) findViewById(R.id.et_03);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivlogo = (SimpleDraweeView) findViewById(R.id.iv_logo);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        et01.setText(MyApplication.myPreferences.readReal_name());
        et03.setText(MyApplication.myPreferences.readUser_phone());
        et01.setEnabled(false);
        et01.setFocusable(false);
        et03.setFocusableInTouchMode(false);
        et03.setEnabled(false);
        et03.setFocusable(false);
        et03.setFocusableInTouchMode(false);


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
                Intent intent = new Intent();
                intent.putExtra("mode", 1);
                intent.setClass(this, ChoseBankActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.bt_01:
                if (TextUtils.isEmpty(bankId)) {
                    ToastUtils.show(this, "请选择银行！");
                } else if (TextUtils.isEmpty(et02.getText().toString())) {
                    ToastUtils.show(this, "请输入银行卡号！");
                } else if (et02.getText().toString().length() < 15 || et02.getText().toString().length() > 19) {
                    ToastUtils.show(this, "请输入15-19位银行卡号！");
                }
                              /*  else if (TextUtils.isEmpty(et04.getText().toString())) {
                    ToastUtils.show(this, "请输入开户行！");
                } */
                                else {
                    getAccessToken();
                }
                break;
        }
    }
    private void getAccessToken() {
        new JsonAccessToken("user/banksave", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        addBankCard(info.access_token);
                    } else {
                        UtilToast.show(BankcardAddActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BankcardAddActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }

    private void addBankCard(String access_token) {
//        new JsonBanksave(access_token,MyApplication.myPreferences.readUid(), et02.getText().toString(), tv01.getText().toString(), et04.getText().toString(), new AsyCallBack() {

        new JsonBanksave(access_token,MyApplication.myPreferences.readUid(), et02.getText().toString(), tv01.getText().toString(), tv01.getText().toString(), new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(BankcardAddActivity.this, JsonBanksave.TOAST);
            }


        }).execute(BankcardAddActivity.this, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            tv01.setText(data.getStringExtra("name"));
            ivlogo.setImageURI(Uri.parse(data.getStringExtra("ico")));
            bankId = data.getStringExtra("id");
        }
    }
}
