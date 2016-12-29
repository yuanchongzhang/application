package com.haiying.p2papp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.BonusRecordAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonPromotionlog;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class BonusRecordActivity extends BaseActivity {

    private ImageView ivback;
    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    private RecyclerView rv01;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<JsonPromotionlog.Info.ListContent> list = new ArrayList<>();
    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;
    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;

    public boolean canLoadMore = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_record);
        initView();
        initRefreshListener();
        getAccessToken(true, nowPage);
    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        mAdapter = new BonusRecordAdapter(this, list);
        rv01.setAdapter(mAdapter);

    }

    private void getAccessToken(final boolean isShow, final int page) {
        new JsonAccessToken("user/promotion_log", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token, isShow, page);
                    } else {
                        UtilToast.show(BonusRecordActivity.this, "安全验证失败！");
                        canLoadMore = true;
                        store_house_ptr_frame.refreshComplete();
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BonusRecordActivity.this, "安全验证失败！");
                canLoadMore = true;
                store_house_ptr_frame.refreshComplete();
            }
        }).execute(this, false);

    }

    private void initData(String access_token, final boolean isShow, final int page) {
        new JsonPromotionlog(access_token, MyApplication.myPreferences.readUid(), String.valueOf(page), new AsyCallBack<JsonPromotionlog.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonPromotionlog.Info info) throws Exception {
                if (info != null) {


                    //   tv01.setText("截止 " + info.last_time+","+"您目前的奖金金额是: "+"￥"+info.reward_money + "元"+","+"您历史上累计获得奖金总额是"+"￥"+info.total_award + "元");
                    String str = "截止 " + info.last_time + "," + "您目前的奖金金额是: " + "￥" + info.reward_money  + "," + "您历史上累计获得奖金总额是" + "￥" + info.total_award+"。" ;
                    SpannableStringBuilder style = new SpannableStringBuilder(str);
                    int bstart = str.indexOf("￥");
                    int bend = bstart + info.reward_money.length() + 1;
                    int bs_str1 = str.indexOf("￥");

                    int bend2 = bstart + info.total_award.length() + 1;

                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD9809")), bstart, bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new AbsoluteSizeSpan(50), bstart, bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    int str2=3+info.last_time.length()+12+info.reward_money.length()+2+13;

                    int bend_end=str2+info.total_award.length()+1;
                    style.setSpan(new ForegroundColorSpan(Color.parseColor("#FD9809")), str2, bend_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style.setSpan(new AbsoluteSizeSpan(50), str2, bend_end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                /*    SpannableString ss = new SpannableString(str);

                    ss.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 13,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
                    tv01.setText(style);


                    //


             /*       String str2="您历史上累计获得奖金总额是"+"￥"+info.total_award + "元";
                    SpannableStringBuilder style2=new SpannableStringBuilder(str2);
                    int bstart2=str1.indexOf("￥");
                    int bend2=bstart+info.reward_money.length()+1;
                    style2.setSpan(new ForegroundColorSpan(Color.parseColor("#FB1010")), bstart,bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    style2.setSpan(new AbsoluteSizeSpan(50), bstart2, bend2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/


//                tv02.setText(style2);

                    // tv02.setText("您目前的奖金金额" + info.reward_money + "元");
                    tv03.setText("您历史上累计获得奖金总额是" + info.total_award + "元");
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
//                    if (list.isEmpty()) {
//                        UtilToast.show(BonusRecordActivity.this, "无相关记录！");
//                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BonusRecordActivity.this, JsonPromotionlog.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                canLoadMore = true;
                mAdapter.notifyDataSetChanged();
            }

        }).execute(this, isShow);
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
                        UtilToast.show(BonusRecordActivity.this, "没有更多了");
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
