package com.haiying.p2papp.activity;

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
import com.haiying.p2papp.conn.JsonChargelog;
import com.haiying.p2papp.conn.JsonWithdrawlog;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class WithdrawOrRechargeRecordActivity extends BaseActivity {

    private boolean Mode = true;//默认提现模式
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView tvtitle;
    private ImageView ivback;
    private RecyclerView rv01;
    private List<JsonChargelog.Info.ListContent> list = new ArrayList();
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
        setContentView(R.layout.activity_withdraw_or_recharge_record);
        initView();
        initMode();
        initRefreshListener();
        initData(true,nowPage);
    }

    private void initData(boolean b,final int page) {
        if (Mode) {
            getWithdrawAccessToken(b,page);
        } else {
            getChargeAccessToken(b,page);
        }
    }


    private void initMode() {

        Mode = getIntent().getBooleanExtra("mode", true);
        if (!Mode) {
            tvtitle.setText(getResources().getText(R.string.wor_title_right_02));
        }
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        if (Mode) {
            mAdapter = new PromotionDetailsAdapter(this, 2, (List) list);
        } else {
            mAdapter = new PromotionDetailsAdapter(this, 3, (List) list);
        }
        rv01.setAdapter(mAdapter);
    }

    private void initView() {
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        this.tvtitle = (TextView) findViewById(R.id.tv_title_right);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
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
                        initData(true,nowPage+1);
                    } else {
                        UtilToast.show(WithdrawOrRechargeRecordActivity.this, "没有更多了");
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
                initData(false,nowPage);
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

    private void getChargeAccessToken(final boolean isShow,final int page) {
        new JsonAccessToken("user/charge_log", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initChargeData(info.access_token, isShow,page);
                    } else {
                        canLoadMore = true;
                        UtilToast.show(WithdrawOrRechargeRecordActivity.this, "安全验证失败！");
                        if (!isShow) {
                            store_house_ptr_frame.refreshComplete();
                        }
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                canLoadMore = true;
                UtilToast.show(WithdrawOrRechargeRecordActivity.this, "安全验证失败！");
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }


    private void initChargeData(String access_token, final boolean isShow,final int page) {
        //todo 只写了充值
        new JsonChargelog(access_token, MyApplication.myPreferences.readUid(), String.valueOf(page), new AsyCallBack<JsonChargelog.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonChargelog.Info info) throws Exception {
                if (info != null) {
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                    if (list.isEmpty()) {
                        UtilToast.show(WithdrawOrRechargeRecordActivity.this, "无充值记录！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(WithdrawOrRechargeRecordActivity.this, JsonChargelog.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (!isShow) {store_house_ptr_frame.refreshComplete();}
                mAdapter.notifyDataSetChanged();
                canLoadMore = true;
            }

        }).execute(WithdrawOrRechargeRecordActivity.this, isShow);
    }


    private void getWithdrawAccessToken(final boolean isShow,final int page) {
        new JsonAccessToken("user/withdraw_log", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initWithdrawData(info.access_token, isShow,page);
                    } else {
                        canLoadMore = true;
                        UtilToast.show(WithdrawOrRechargeRecordActivity.this, "安全验证失败！");
                        if (!isShow) {
                            store_house_ptr_frame.refreshComplete();
                        }
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                canLoadMore = true;
                UtilToast.show(WithdrawOrRechargeRecordActivity.this, "安全验证失败！");
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }

            }
        }).execute(this, isShow);

    }


    private void initWithdrawData(String access_token, final boolean isShow,final int page) {
        //todo 只写了充值
        new JsonWithdrawlog(access_token, MyApplication.myPreferences.readUid(), String.valueOf(page), new AsyCallBack<JsonWithdrawlog.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonWithdrawlog.Info info) throws Exception {
                if (info != null) {
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                    if (list.isEmpty()) {
                        UtilToast.show(WithdrawOrRechargeRecordActivity.this, "无相关记录！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(WithdrawOrRechargeRecordActivity.this, JsonChargelog.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (!isShow) {store_house_ptr_frame.refreshComplete();}
                mAdapter.notifyDataSetChanged();
                canLoadMore = true;
            }

        }).execute(WithdrawOrRechargeRecordActivity.this, isShow);
    }
}
