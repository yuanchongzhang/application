package com.haiying.p2papp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonPromotion;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

public class InviteFriendsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private TextView tv01;
    private TextView tv02;
    private LinearLayout et01;
    private Button bt01;
    private Button bt02;

    private TextView tv_04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        initView();
        initListener();
        getAccessToken();
    }

    private void getAccessToken() {
        new JsonAccessToken("user/promotion", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(InviteFriendsActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(InviteFriendsActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initData(String access_token) {
        new JsonPromotion(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonPromotion.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonPromotion.Info info) throws Exception {
                if (info != null) {
                    initTextView(info.promotion_desc);
                    tv02.setText(info.promotion_link);
                    tv_04.setText(info.promotion_award);

                    String str=info.promotion_award;

                    int bstart=str.indexOf("千");
                    int bend=bstart+"分之2".length()+1;
                    SpannableStringBuilder style=new SpannableStringBuilder(str);
                  //  style.setSpan(new BackgroundColorSpan(Color.RED),bstart,bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#FB1010")), bstart,bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new AbsoluteSizeSpan(40), bstart, bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tv_04.setText(style);



                    Log.d(info.promotion_award, "111111111111112222222222222222222222");


                } else {
                    UtilToast.show(InviteFriendsActivity.this, JsonPromotion.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(InviteFriendsActivity.this, false);
    }

    private void initView() {
        this.bt02 = (Button) findViewById(R.id.bt_02);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.et01 = (LinearLayout) findViewById(R.id.et_01);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);

        this.tv_04 = (TextView) findViewById(R.id.tv_04);
    }

    private void initTextView(String s) {
        SpannableString ss = new SpannableString(s);
        int i = s.indexOf("千");
        //ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FD4A7F")), i, i + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FD4A7F")), i, i + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv01.setText(ss);
        tv02.setTextIsSelectable(true);
        //  tv_04.setTextIsSelectable(true);


    }

    private void initListener() {
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_01:
                intent.setClass(this, BonusRecordActivity.class);
                break;
            case R.id.bt_02:
                intent.setClass(this, PromotionDetailsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
