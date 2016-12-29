package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonLogin;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    public final static String FLAG = "flag";
    @Bind(R.id.bt_registered)
    TextView btRegistered;
    @Bind(R.id.tv_forgotpw)
    TextView tvForgotpw;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_login)
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btRegistered.setOnClickListener(this);
        tvForgotpw.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_registered:
                Intent intent = new Intent();
                intent.putExtra(FLAG, 1);//注册模式
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forgotpw:
                Intent intent1 = new Intent();
                intent1.putExtra(FLAG, 2);//忘记密码模式
                intent1.setClass(this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.bt_login:
                onLogin();
                break;
        }
    }

    public void onLogin() {

        if (etAccount.getText().toString().equals("")) {

            UtilToast.show(this, "请输入手机号");

        } else if (etAccount.getText().toString().length() < 6 || etAccount.getText().toString().length() > 16) {

            UtilToast.show(this, "请输入正确的用户名");

        } else if (etPassword.getText().toString().length() < 6 || etPassword.getText().toString().length() > 20) {

            UtilToast.show(this, etPassword.getHint());

        } else {
            getAccessToken();
        }

    }

    private void initLogin(String access_token) {
        new JsonLogin(access_token,etAccount.getText().toString(), etPassword.getText().toString(), new AsyCallBack<JsonLogin.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonLogin.Info info) throws Exception {
                if (info!=null) {

                    MyApplication.myPreferences.saveUid(info.uid);

                    MyApplication.myPreferences.saveUserName(info.user_name);

                    MyApplication.myPreferences.saveUser_phone(etAccount.getText().toString().trim());

                    Log.e("DDDDDDDDDDD", etAccount.getText().toString().trim());

                    startActivity(new Intent(context,MainActivity.class));
//                    finish();
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(LoginActivity.this,JsonLogin.TOAST);
            }

        }).execute(this,true);
    }

    private void getAccessToken() {
        new JsonAccessToken("common/login", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initLogin(info.access_token);
                    } else {
                        UtilToast.show(LoginActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(LoginActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }
}
