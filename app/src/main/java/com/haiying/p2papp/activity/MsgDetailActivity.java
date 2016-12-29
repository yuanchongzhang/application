package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonMsgview;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

public class MsgDetailActivity extends BaseActivity {

    private ImageView ivback;
    private TextView tvtitle;
    private TextView tv01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        initView();
        getAccessToken();
    }

    private void getAccessToken() {
        new JsonAccessToken("user/msgview", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(MsgDetailActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MsgDetailActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void initData(String access_token) {
        new JsonMsgview(access_token, MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"), new AsyCallBack<JsonMsgview.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonMsgview.Info info) throws Exception {
                if (info != null) {
                //    tvtitle.setText(info.title);
                    tv01.setText(info.inner_msg);
                } else {
                    UtilToast.show(MsgDetailActivity.this, JsonMsgview.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(MsgDetailActivity.this, true);

    }

    private void initView() {
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
    }
}
