package com.haiying.p2papp.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.BankcardlistAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBankdel;
import com.haiying.p2papp.conn.JsonBanklist;
import com.haiying.p2papp.view.SimpleDividerItemDecoration;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;

public class BankcardManagementActivity extends BaseActivity implements View.OnClickListener, BankcardlistAdapter.IonSlidingViewClickListener {

    private BankcardlistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView ivback;
    private RecyclerView rv01;
    private LinearLayout lladd;
    private List<JsonBanklist.Info.ListContent> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcard_management);
        initView();
        initListener();
//        getAccessToken();
    }

    private void getAccessToken() {
        new JsonAccessToken("user/banklist", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(BankcardManagementActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BankcardManagementActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    private void initData(String access_token) {
        new JsonBanklist(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonBanklist.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonBanklist.Info info) throws Exception {
                if (info != null) {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    list.addAll(info.list);

                } else {
                    UtilToast.show(BankcardManagementActivity.this, JsonBanklist.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                mAdapter.notifyDataSetChanged();
            }

        }).execute(BankcardManagementActivity.this, true);
    }

    private void initView() {
        this.lladd = (LinearLayout) findViewById(R.id.ll_add);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new BankcardlistAdapter(this, list);
        rv01.setItemAnimator(new DefaultItemAnimator());
       // mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, 40));
       // rv01.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        rv01.setAdapter(mAdapter);
    }


    private void initListener() {
        lladd.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (MyApplication.myPreferences.readId_status().equals("0")) {
            ToastUtils.show(this, "请先通过实名认证，实名认证通过后方可绑定银行卡。");
        } else if (MyApplication.myPreferences.readId_status().equals("2")) {
            ToastUtils.show(this, "实名认证审核中，实名认证通过后方可绑定银行卡。");
        } else if (MyApplication.myPreferences.readId_status().equals("1")) {
            Intent intent = new Intent();
            intent.setClass(this, BankcardAddActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void getDeleteAccessToken(final int position) {
        new JsonAccessToken("user/bankdel", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        deteleItem(info.access_token, position);
                    } else {
                        UtilToast.show(BankcardManagementActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BankcardManagementActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    @Override
    public void onDeleteBtnCilck(View view, final int position) {
        getDeleteAccessToken(position);
    }

    private void deteleItem(String access_token, final int position) {
        new JsonBankdel(access_token, MyApplication.myPreferences.readUid(), list.get(position).id, new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    list.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(BankcardManagementActivity.this, JsonBankdel.TOAST);
            }


        }).execute(BankcardManagementActivity.this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAccessToken();
    }
}
