package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonIdcardapply;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilMatches;
import com.zcx.helper.util.UtilToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.trinea.android.common.util.ToastUtils;

public class VerifiedActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private EditText et01;
    private EditText et02;
    private Button bt01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        bt01.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(et01.getText().toString().trim())) {
            ToastUtils.show(this, "请输入真实姓名");
        } else if (TextUtils.isEmpty(et02.getText().toString().trim())) {
            ToastUtils.show(this, "请输入身份证号");
        } else if (!UtilMatches.checkIdentity(et02.getText().toString().trim())) {
            ToastUtils.show(this, "请输入正确的身份证号");
        } else {
            getAccessToken();
        }
    }

    private void getAccessToken() {
        new JsonAccessToken("user/idcard_apply", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        idcardApply(info.access_token);
                    } else {
                        UtilToast.show(VerifiedActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(VerifiedActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void idcardApply(String access_token) {
        new JsonIdcardapply(access_token, MyApplication.myPreferences.readUid(), et01.getText().toString().trim(), et02.getText().toString().trim(), new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    MyApplication.myPreferences.saveId_status("0");
                    MyApplication.myPreferences.saveReal_name(et01.getText().toString());
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(VerifiedActivity.this, JsonIdcardapply.TOAST);
            }
        }).execute(this, true);


    }

    public Boolean IsIdCard(String string) {
        Pattern p = Pattern.compile("^(\\d{15}$|^\\d{17}(\\d|X|x))$");// 正则表达式
        Matcher m = p.matcher(string); // 操作的字符串
        boolean b = m.matches();
        return b;
    }
}
