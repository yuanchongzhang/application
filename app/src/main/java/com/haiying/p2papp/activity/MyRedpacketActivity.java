package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.PromotionDetailsAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBonus;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class MyRedpacketActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView ivback;
    private TextView tvtitleright;
    private TextView tv01;
    private RecyclerView rv01;
    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;

    private boolean canLoadMore = true;
    public String bonus_url;
    public String bonus_url_token;


    public List<JsonBonus.Info.ListContent> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_redpacket);
        initView();
        initRefreshListener();
        getAccessToken(true, nowPage);
        getAccessToken1(true);
    }

    private void getAccessToken(final boolean isShow, final int page) {
        new JsonAccessToken("user/bonus", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token, isShow, page);
                    } else {
                        UtilToast.show(MyRedpacketActivity.this, "安全验证失败！");
                        canLoadMore = true;
                        if (!isShow) {
                            store_house_ptr_frame.refreshComplete();
                        }
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MyRedpacketActivity.this, "安全验证失败！");
                canLoadMore = true;
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }


    private void getAccessToken1(final boolean isShow) {
        new JsonAccessToken("user/bonus", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        bonus_url_token = info.access_token;
                    } else {
                        UtilToast.show(MyRedpacketActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MyRedpacketActivity.this, "安全验证失败！");
            }
        }).execute(this, isShow);

    }


    private void initData(String access_token, final boolean isShow, final int page) {
        new JsonBonus(access_token, MyApplication.myPreferences.readUid(), String.valueOf(page), new AsyCallBack<JsonBonus.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonBonus.Info info) throws Exception {
                if (info != null) {
                    if (TextUtils.isEmpty(info.canUse)) {
                        tv01.setText("0.00");
                    } else {
                        tv01.setText(info.canUse+"元");
                    }
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                    bonus_url = info.bonus_url;
                    if (list.isEmpty()) {
                        UtilToast.show(MyRedpacketActivity.this, "无相关记录！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MyRedpacketActivity.this, JsonBonus.TOAST);

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                canLoadMore = true;
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                mAdapter.notifyDataSetChanged();
            }

        }).execute(MyRedpacketActivity.this, isShow);
    }

    private void initView() {
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.tvtitleright = (TextView) findViewById(R.id.tv_title_right);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        mLayoutManager = new LinearLayoutManager(this);
        header = new PtrClassicDefaultHeader(this);
        tvtitleright.setOnClickListener(this);
        store_house_ptr_frame.setHeaderView(header);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new PromotionDetailsAdapter(this, 4, (List) list);
        rv01.setAdapter(mAdapter);
    }

    private void initRefreshListener() {
        rv01.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                if (dy > 0 && canLoadMore && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    canLoadMore = false;
                    if (nowPage < totalPages) {
                        getAccessToken(true, nowPage + 1);
                    } else {
                        UtilToast.show(MyRedpacketActivity.this, "当前页已是最后一页");
                    }
                }
            }
        });

        store_house_ptr_frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
                return firstVisibleItemPosition == 0 || list.size() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
                nowPage = 1;
                getAccessToken(false,nowPage);
            }
        });
        store_house_ptr_frame.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
                header.onUIReset(frame);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                header.onUIRefreshPrepare(frame);
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {
                header.onUIRefreshBegin(frame);
            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {
                header.onUIRefreshComplete(frame);
            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                header.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == tvtitleright) {
            if (!TextUtils.isEmpty(bonus_url)) {
                Intent intent = new Intent();
                intent.putExtra("title", "红包使用说明");
                intent.putExtra("url", bonus_url + "&access_token=" + bonus_url_token);
                intent.setClass(this, AdvertisingActivity.class);
                startActivity(intent);
            }

        }
    }
}
