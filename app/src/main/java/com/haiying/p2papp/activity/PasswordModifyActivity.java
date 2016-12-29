package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonChangepass;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PasswordModifyActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_01)
    EditText et01;
    @Bind(R.id.et_02)
    EditText et02;
    @Bind(R.id.et_03)
    EditText et03;
    @Bind(R.id.bt_pwm_confirm)
    Button btPwmConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_modify);
        ButterKnife.bind(this);
        btPwmConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onEditPW();
    }

    private void onEditPW() {
        if (TextUtils.isEmpty(et01.getText().toString())) {

            UtilToast.show(this, "请输入原密码");

        } else if (TextUtils.isEmpty(et02.getText().toString())) {

            UtilToast.show(this, "请输入新密码");

        } else if (et02.getText().toString().length() < 6 || et02.getText().toString().length() > 15) {

            UtilToast.show(this, "请输入6~15位新密码");

        } else if (et03.getText().toString().equals("")) {

            UtilToast.show(this, "请确认新密码");

        }  else if (!et03.getText().toString().equals(et02.getText().toString())) {

            UtilToast.show(this, "两次密码输入不一致");

        } else {

            getAccessToken();
        }
    }


    private void getAccessToken() {
        new JsonAccessToken("user/change_pass", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initChange(info.access_token);
                    } else {
                        UtilToast.show( PasswordModifyActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(PasswordModifyActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }

    private void initChange(String access_token) {
        new JsonChangepass(access_token,MyApplication.myPreferences.readUid(),et01.getText().toString(), et02.getText().toString(), et03.getText().toString(),new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    MyApplication.INSTANCE.finishActivity(PasswordManageActivity.class);
                    MyApplication.INSTANCE.finishActivity(AccountManagementActivity.class);
                    MyApplication.myPreferences.clear();
                    Intent intent= new Intent();
                    intent.setClass(PasswordModifyActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(PasswordModifyActivity.this, JsonChangepass.TOAST);
            }
        }).execute(this, true);
    }
}
