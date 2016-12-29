package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonChangepinpass;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

public class PasswordTransactionActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvtitle;
    private ImageView ivback;
    private EditText et01;
    private EditText et02;
    private EditText et03;
    private Button btptconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_transaction);
        this.btptconfirm = (Button) findViewById(R.id.bt_pt_confirm);
        this.et03 = (EditText) findViewById(R.id.et_03);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        btptconfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (et01.getText().toString().trim().equals("")) {

            UtilToast.show(this, "请输入原支付密码");

        } else if (et02.getText().toString().trim().equals("")) {

            UtilToast.show(this, "请输入新支付密码");

        } else if (et02.getText().toString().trim().length() < 6 || et02.getText().toString().trim().length() > 15) {

            UtilToast.show(this, "请输入6~15位新支付密码");

        } else if (et03.getText().toString().trim().equals("")) {

            UtilToast.show(this, "请确认新密码");

        }  else if (!et03.getText().toString().trim().equals(et02.getText().toString().trim())) {

            UtilToast.show(this, "两次密码输入不一致");

        } else {
            getAccessToken();
        }
    }
    private void getAccessToken() {
        new JsonAccessToken("user/change_pinpass", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initChange(info.access_token);
                    } else {
                        UtilToast.show( PasswordTransactionActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(PasswordTransactionActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }
    private void initChange(String access_token) {
        new JsonChangepinpass(access_token,MyApplication.myPreferences.readUid(),et01.getText().toString(), et02.getText().toString(), et03.getText().toString(),new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    MyApplication.myPreferences.savePin_pass("1");
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(PasswordTransactionActivity.this, JsonChangepinpass.TOAST);
            }
        }).execute(this, true);
    }
}
