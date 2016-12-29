package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonChangemobiledo;
import com.haiying.p2papp.conn.JsonSendcode;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

public class ChangePhoneNumbersNextActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private EditText et01;
    private EditText et02;
    private Button btgetcode;
    private Button bt01;
    private String codeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_numbers_next);
        initView();
    }

    private void initView() {
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.btgetcode = (Button) findViewById(R.id.bt_getcode);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        bt01.setOnClickListener(this);
        btgetcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_getcode:
                sentCode();
                break;
            case R.id.bt_01:
                if (bt01.isSelected()) {
                    if (!TextUtils.isEmpty(et02.getText().toString())) {
                        getNextAccessToken();
                    } else {
                        UtilToast.show(ChangePhoneNumbersNextActivity.this, "请输入收到的验证码");
                    }

                } else {
                    UtilToast.show(ChangePhoneNumbersNextActivity.this, "请点击按钮获取验证码");
                }
                break;
        }

    }
    private void getNextAccessToken() {
        new JsonAccessToken("user/change_mobile_do", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        goNext(info.access_token);
                    } else {
                        UtilToast.show(ChangePhoneNumbersNextActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ChangePhoneNumbersNextActivity.this,"安全验证失败！");
            }
        }).execute(this, true);

    }



    private void goNext(String access_token) {
        new JsonChangemobiledo(access_token,MyApplication.myPreferences.readUid(),et02.getText().toString(),codeId,et01.getText().toString(),new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    MyApplication.myPreferences.saveUser_phone(et01.getText().toString());
                    MyApplication.INSTANCE.finishActivity(ChangePhoneNumbersActivity.class);
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(ChangePhoneNumbersNextActivity.this, JsonChangemobiledo.TOAST);
            }

        }).execute(this, true);

    }

    private void sentCode() {
        if (et01.getText().toString().equals("")) {
            UtilToast.show(this, "请输入手机号");
        } else {
            getSentAccessToken();
        }

    }

    private void getSentAccessToken() {
        new JsonAccessToken("common/sendcode", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initSent(info.access_token);
                    } else {
                        UtilToast.show(ChangePhoneNumbersNextActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ChangePhoneNumbersNextActivity.this,"安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initSent(String access_token) {
        new JsonSendcode(access_token,et01.getText().toString(), "", new AsyCallBack<JsonSendcode.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonSendcode.Info info) throws Exception {
                if (info != null) {
                    codeId = info.codeId;
                    bt01.setSelected(true);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(ChangePhoneNumbersNextActivity.this, JsonSendcode.TOAST);
            }

        }).execute(this, true);
    }
}
