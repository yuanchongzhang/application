package com.haiying.p2papp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.ApplyRecordlistAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBonus;
import com.haiying.p2papp.conn.JsonMoneylimitlog;
import com.haiying.p2papp.view.SimpleDividerItemDecoration;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class ApplyRecordActivity extends BaseActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView rv01;
    private List<JsonMoneylimitlog.Info.ListContent> list = new ArrayList<>();
    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;
    private ImageView ivback;
    private TextView tvtitleright;

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;

    public boolean canLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_record);
        initView();
        initRefreshListener();
        getAccessToken(true, nowPage);
    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.tvtitleright = (TextView) findViewById(R.id.tv_title_right);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        // use a linear layout manager
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);


        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
     //   rv01.addItemDecoration(new SimpleDividerItemDecoration(ApplyRecordActivity.this,1));
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_divider2);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, 3));

        mAdapter = new ApplyRecordlistAdapter(this, list);

        rv01.setAdapter(mAdapter);
    }

    private void getAccessToken(final boolean isShow, final int page) {
        new JsonAccessToken("user/moneylimit_log", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token, isShow, page);
                    } else {
                        canLoadMore = true;
                        UtilToast.show(ApplyRecordActivity.this, "安全验证失败！");
                        store_house_ptr_frame.refreshComplete();
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                canLoadMore = true;
                UtilToast.show(ApplyRecordActivity.this, "安全验证失败！");
                store_house_ptr_frame.refreshComplete();
            }
        }).execute(this, false);

    }


    private void initData(String access_token, final boolean isShow, final int page) {
        new JsonMoneylimitlog(access_token, MyApplication.myPreferences.readUid(), String.valueOf(page), new AsyCallBack<JsonMoneylimitlog.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonMoneylimitlog.Info info) throws Exception {
                if (info != null) {
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ApplyRecordActivity.this, JsonBonus.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                canLoadMore = true;
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                mAdapter.notifyDataSetChanged();
            }

        }).execute(ApplyRecordActivity.this, isShow);
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
                        UtilToast.show(ApplyRecordActivity.this, "当前页已是最后一页");
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
                getAccessToken(false, nowPage);
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

}
