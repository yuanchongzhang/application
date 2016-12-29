package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.haiying.p2papp.adapter.NewsAndMsgAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonArticlelist;
import com.haiying.p2papp.conn.JsonMsglist;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class NewsActivity extends BaseActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView rv01;
    private List<JsonMsglist.Info.ListContent> list = new ArrayList<>();
    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;

    private boolean canLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initRefreshListener();
        getAccessToken(true,nowPage);
    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        // use a linear layout manager
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new NewsAndMsgAdapter(this, 1, list);
        rv01.setAdapter(mAdapter);
    }
    private void getAccessToken(final boolean isShow,final int page) {
        new JsonAccessToken("index/article_list", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token,isShow,page);
                    } else {
                        UtilToast.show(NewsActivity.this, "安全验证失败！");
                        canLoadMore = true;
                        if (!isShow){
                            store_house_ptr_frame.refreshComplete();
                        }
                    }

                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(NewsActivity.this, "安全验证失败！");
                canLoadMore = true;
                if (!isShow){
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }


    private void initData(String access_token,final boolean isShow,final int page) {
        new JsonArticlelist(access_token,getIntent().getStringExtra("id"), String.valueOf(page), new AsyCallBack<JsonArticlelist.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonArticlelist.Info info) throws Exception {
                if (info != null) {
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                    if (list.isEmpty()) {
                        UtilToast.show(NewsActivity.this, "无内容！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(NewsActivity.this, JsonArticlelist.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                canLoadMore = true;
                if (!isShow){
                    store_house_ptr_frame.refreshComplete();
                }
                mAdapter.notifyDataSetChanged();
            }

        }).execute(NewsActivity.this, isShow);
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
                        getAccessToken(true,nowPage+1);
                    } else {
                        UtilToast.show(NewsActivity.this, "当前页已是最后一页");
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
}
