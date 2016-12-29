package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonChangemobile;
import com.haiying.p2papp.conn.JsonChangenickname;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

/**
 * Created by Administrator on 2016/12/27.
 */
public class ChangeNickNameActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;

    private TextView text_finish_btn;

    private EditText et_01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changenickname);
        initVIew();
        et_01.setText(getIntent().getStringExtra("nickname"));
    }


    public void initVIew() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        text_finish_btn = (TextView) findViewById(R.id.text_finish_btn);
        text_finish_btn.setOnClickListener(this);
        et_01 = (EditText) findViewById(R.id.et_01);
        et_01.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.text_finish_btn:
                if (et_01.getText().toString().length() < 4 || et_01.getText().toString().length() > 10) {
                    UtilToast.show(ChangeNickNameActivity.this, "请输入4~10位的昵称");
                }  /*  if (!TextUtils.isEmpty(et_01.getText().toString())){

            }else
*/ else {
                    //   UtilToast.show(ChangeNickNameActivity.this, "请输入昵称");
                    getNextAccessToken();
                }


                break;

            case R.id.et_01:

                break;

        }


    }

    private void getNextAccessToken() {
        new JsonAccessToken("user/setnick_name", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        goNext(info.access_token);
                    } else {
                        UtilToast.show(ChangeNickNameActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ChangeNickNameActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void goNext(String access_token) {
        new JsonChangenickname(access_token, MyApplication.myPreferences.readUid(), et_01.getText().toString(), new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
//                    Intent intent = new Intent();
//                    intent.setClass(ChangeNickNameActivity.this, ChangePhoneNumbersNextActivity.class);
//                    startActivity(intent);
                    MyApplication.myPreferences.saveNickName(et_01.getText().toString());

                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(ChangeNickNameActivity.this, JsonChangemobile.TOAST);
            }

        }).execute(this, true);

    }

}
