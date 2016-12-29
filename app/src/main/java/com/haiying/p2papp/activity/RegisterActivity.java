package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonFindpass;
import com.haiying.p2papp.conn.JsonReg;
import com.haiying.p2papp.conn.JsonSendcode;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilMatches;
import com.zcx.helper.util.UtilToast;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_01)
    EditText et01;
    @Bind(R.id.et_02)
    EditText et02;
    @Bind(R.id.bt_getcode)
    Button btGetcode;
    @Bind(R.id.et_03)
    EditText et03;
    @Bind(R.id.et_04)
    EditText et04;
    @Bind(R.id.et_05)
    EditText et05;
    @Bind(R.id.bt_registered)
    Button btRegistered;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
   /* @Bind(R.id.iv_confirm_1)
    ImageView ivConfirm1;*/
   /* @Bind(R.id.iv_confirm_2)
    ImageView ivConfirm2;*/
    @Bind(R.id.tv_recommend)
    LinearLayout tvRecommend;
   /* @Bind(R.id.et_07)
    EditText et07;*/
   /* @Bind(R.id.tv_username)
    LinearLayout tvUsername;*/

    private boolean isRegistMode = true;//true 注册模式；false 找回密码
    private String codeId = "";
    private String codeMode = "reg";//默认为空，发普通短信 reg:注册短信 findpass:找回密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        if ((getIntent().getIntExtra(LoginActivity.FLAG, 0)) == 2) {
            isRegistMode = false;
            codeMode = "findpass";
            tvTitle.setText(getResources().getText(R.string.register_title_forgot));
            tvRecommend.setVisibility(View.GONE);
          //  tvUsername.setVisibility(View.GONE);

        }
        btGetcode.setOnClickListener(this);
        btRegistered.setOnClickListener(this);
        btRegistered.setSelected(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getcode:
                sentCode();
                break;
            case R.id.bt_registered:
                if (btRegistered.isSelected()) {
                    if (isRegistMode) {
                        onRegist();
                    } else {
                        onResetPW();
                    }
                } else if (et01.getText().toString().equals("")) {
                    UtilToast.show(RegisterActivity.this, "请输入手机号");
                } else if (!UtilMatches.checkMobile(et01.getText().toString())) {
                    UtilToast.show(RegisterActivity.this, "请输入正确的手机号");
                } else {
                    UtilToast.show(RegisterActivity.this, "请点击按钮获取验证码");
                }


                break;
        }
    }

    private void sentCode() {
        if (et01.getText().toString().equals("")) {

            UtilToast.show(this, "请输入手机号");

        } else if (!UtilMatches.checkMobile(et01.getText().toString())) {

            UtilToast.show(this, "请输入正确的手机号");

        } else {
            getSendCodeAccessToken();

        }

    }

    private void getSendCodeAccessToken() {
        new JsonAccessToken("common/sendcode", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initSendCode(info.access_token);
                    } else {
                        UtilToast.show(RegisterActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(RegisterActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initSendCode(String access_token) {
        new JsonSendcode(access_token, et01.getText().toString(), codeMode, new AsyCallBack<JsonSendcode.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonSendcode.Info info) throws Exception {
                if (info != null) {
                    codeId = info.codeId;
                    btRegistered.setSelected(true);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(RegisterActivity.this, JsonSendcode.TOAST);
            }

        }).execute(this, true);
    }

    private void onResetPW() {
        if (et01.getText().toString().equals("")) {

            UtilToast.show(this, "请输入手机号");

        } else if (!UtilMatches.checkMobile(et01.getText().toString())) {

            UtilToast.show(this, "请输入正确的手机号");

        } else if (et02.getText().toString().equals("")) {

            UtilToast.show(this, "请输入验证码");

        } else if (et03.getText().toString().length() < 6 || et03.getText().toString().length() > 15) {

            UtilToast.show(this, et03.getHint());

        } else if (et03.getText().toString().contains(" ")) {

            UtilToast.show(this, "密码不能包含空格");

        } else if (!et03.getText().toString().equals(et04.getText().toString())) {

            UtilToast.show(this, "两次密码输入不一致");

        } else {

            getFindPassAccessToken();

        }
    }

    private void getFindPassAccessToken() {
        new JsonAccessToken("common/findpass", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initFindPass(info.access_token);
                    } else {
                        UtilToast.show(RegisterActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(RegisterActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initFindPass(String access_token) {
        new JsonFindpass(access_token, et01.getText().toString(), et02.getText().toString(), codeId, et03.getText().toString(), new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(RegisterActivity.this, JsonFindpass.TOAST);
            }

        }).execute(this, true);
    }


    public void onRegist() {

        if (et01.getText().toString().equals("")) {

            UtilToast.show(this, "请输入手机号");

        } else if (!UtilMatches.checkMobile(et01.getText().toString())) {

            UtilToast.show(this, "请输入正确的手机号");

        } else if (et02.getText().toString().equals("")) {

            UtilToast.show(this, "请输入验证码");

        } /*else if (et07.getText().toString().length() < 6 || et07.getText().toString().length() > 16) {

            UtilToast.show(this, et07.getHint());

        }*/ else if (et03.getText().toString().length() < 6 || et03.getText().toString().length() > 15) {

            UtilToast.show(this, et03.getHint());

        } else if (!et03.getText().toString().equals(et04.getText().toString())) {

            UtilToast.show(this, "两次密码输入不一致");

        } else {

            getAccessToken();

        }
    }

    private void getAccessToken() {
        new JsonAccessToken("common/reg", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initReg(info.access_token);
                    } else {
                        UtilToast.show(RegisterActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(RegisterActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initReg(String access_token) {
        new JsonReg(access_token, et01.getText().toString(), et03.getText().toString(), et02.getText().toString(), codeId, et05.getText().toString(), new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(RegisterActivity.this, JsonReg.TOAST);
            }
        }).execute(this, true);
    }
}
