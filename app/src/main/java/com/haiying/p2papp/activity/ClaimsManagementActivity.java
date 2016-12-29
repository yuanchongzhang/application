package com.haiying.p2papp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.ClaimsManage01Adapter;
import com.haiying.p2papp.adapter.ClaimsManage02Adapter;
import com.haiying.p2papp.adapter.ClaimsManage03Adapter;
import com.haiying.p2papp.adapter.ClaimsManage04Adapter;
import com.haiying.p2papp.adapter.CmSpinnerAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonInvest;
import com.haiying.p2papp.conn.JsonUserbond;
import com.haiying.p2papp.view.SimpleDividerItemDecoration;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class ClaimsManagementActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView tv01;
    private ImageView iv01;
    private Spinner sp01;
    private List<String> stringList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView rv01;
    private List<JsonUserbond.Info.ListContent> list = new ArrayList<>();
    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;
    private ImageView ivback;
    private TextView tvtitleright;
    private String show_type = "cantransfer";

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;
    private boolean canLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claims_management);
        initView();
        initRefreshListener();
        iv01.setOnClickListener(this);
        tv01.setOnClickListener(this);

    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.sp01 = (Spinner) findViewById(R.id.sp_01);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        stringList.clear();
        stringList.add("可转债权");
        stringList.add("进行中债权");
        stringList.add("成功转让债权");
        stringList.add("已购买债权");
        stringList.add("回收中债权");
        stringList.add("已撤销债权");
        CmSpinnerAdapter cmSpinnerAdapter = new CmSpinnerAdapter(stringList);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, 40));
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);


        sp01.setAdapter(cmSpinnerAdapter);
        sp01.setOnItemSelectedListener(this);
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);

        // specify an adapter (see also next example)

    }

    @Override
    public void onClick(View v) {
        sp01.performClick();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!list.isEmpty()) {
            list.clear();
        }
        nowPage = 1;
        tv01.setText(stringList.get(position));
        switch (position) {
            case 0:
                show_type = "cantransfer";
                mAdapter = new ClaimsManage01Adapter(this, list);
                break;
            case 1:
                show_type = "onbonds";
                mAdapter = new ClaimsManage02Adapter(this, list);
                break;
            case 2:
                show_type = "successclaims";
                mAdapter = new ClaimsManage03Adapter(0, this, list);
                break;
            case 3:
                show_type = "buybond";
                mAdapter = new ClaimsManage03Adapter(1, this, list);
                break;
            case 4:
                show_type = "onbond";
                mAdapter = new ClaimsManage03Adapter(2, this, list);
                break;
            case 5:
                show_type = "cancellist";
                mAdapter = new ClaimsManage04Adapter(this, list);
                break;
        }
        rv01.setAdapter(mAdapter);
        getAccessToken(true, nowPage);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getAccessToken(final boolean isShow, final int page) {
        new JsonAccessToken("user/bond", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token, isShow, page);
                    } else {
                        UtilToast.show(ClaimsManagementActivity.this, "安全验证失败！");
                        canLoadMore = true;
                        if (isShow) {
                            store_house_ptr_frame.refreshComplete();
                        }
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
             /*   UtilToast.show(ClaimsManagementActivity.this, "安全验证失败！");
                canLoadMore = true;
                if (isShow) {
                    store_house_ptr_frame.refreshComplete();
                }*/
                UtilToast.show(ClaimsManagementActivity
                        .this, JsonInvest.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                super.onEnd(toast, type);
                canLoadMore = false;
                if (isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }

    private void initData(String access_token, final boolean isShow, final int page) {
        new JsonUserbond(access_token, String.valueOf(page), MyApplication.myPreferences.readUid(), show_type,
                new AsyCallBack<JsonUserbond.Info>() {

                    @Override
                    public void onStart(int type) throws Exception {
                        super.onStart(type);
                    }

                    @Override
                    public void onSuccess(String toast, int type, JsonUserbond.Info info) throws Exception {
                        if (info != null) {
                            list.addAll(info.list);
                            nowPage = info.page.nowPage;
                            totalPages = info.page.totalPages;
                            pageSize = info.page.pageSize;
                            totalRows = info.page.totalRows;
                            if (list.isEmpty()) {
                                ToastUtils.show(ClaimsManagementActivity.this, "无记录！");
                            }
                        }
                    }

                    @Override
                    public void onFail(String toast, int type) throws Exception {
                        super.onFail(toast, type);
                        UtilToast.show(ClaimsManagementActivity.this, JsonUserbond.TOAST);
                    }

                    @Override
                    public void onEnd(String toast, int type) throws Exception {
                        canLoadMore = true;
                        if (isShow) {
                            store_house_ptr_frame.refreshComplete();
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                }).execute(ClaimsManagementActivity.this, isShow);
    }

    private void initRefreshListener() {
        rv01.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                if (dy > 0 && canLoadMore && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    if (nowPage < totalPages) {
                        getAccessToken(true, nowPage + 1);
                    } else {
                        UtilToast.show(ClaimsManagementActivity.this, "当前页已是最后一页");
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
