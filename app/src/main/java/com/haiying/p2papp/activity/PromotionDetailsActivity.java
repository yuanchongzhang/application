package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.PromotionDetailsAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonPromotionfriend;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PromotionDetailsActivity extends BaseActivity {

    private ImageView ivback;
    private TextView tv01;
    private RecyclerView rv01;
    private TextView tv02;
    private RecyclerView rv02;
    private RecyclerView.Adapter mAdapter, adapter;
    private RecyclerView.LayoutManager mLayoutManager, layoutManager;
    private List<JsonPromotionfriend.Info.Members> memberslist = new ArrayList();

    private List<JsonPromotionfriend.Info.AwardMoneyLog> moneyLogList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_details);
        initView();
        initList();
        getAccessToken();
    }

    private void getAccessToken() {
        new JsonAccessToken("user/promotion_friend", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show( PromotionDetailsActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( PromotionDetailsActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }

    private void initData(String access_token) {
        new JsonPromotionfriend(access_token,MyApplication.myPreferences.readUid(), new AsyCallBack<JsonPromotionfriend.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonPromotionfriend.Info info) throws Exception {
                if (info != null) {
                    tv01.setText("截至 " + info.last_time);
                    tv02.setText("截至 " + info.last_time);
                    memberslist = info.memberslist;
                    moneyLogList = info.moneyLogList;
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                mAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }


        }).execute(this, true);
    }

    private void initView() {
        this.rv02 = (RecyclerView) findViewById(R.id.rv_02);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        tv01.setText("截至 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
        tv02.setText("截至 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
    }

    private void initList() {

        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new PromotionDetailsAdapter(this, 1, (List) memberslist);
        rv01.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(this);
        rv02.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        adapter = new PromotionDetailsAdapter(this, 0, (List) moneyLogList);
        rv02.setAdapter(adapter);
    }

}
