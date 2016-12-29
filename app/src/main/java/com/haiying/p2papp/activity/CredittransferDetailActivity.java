package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.MyViewPager01Adapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBonddetail;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ScreenUtils;
import cn.trinea.android.common.util.ToastUtils;

public class CredittransferDetailActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView tvtitle;
    private ImageView ivback;
    private TextView tvname;
    private TextView tvsumMoney;
    private TextView tvrate;
    private TextView tvtime;
    private TextView tvtimeUnit;
    private TextView tv08;
    private TextView tvinvest01;
    private TextView tvinvest02;
    private TextView tvinvest03;
    private TextView tvinvest15;
    private TextView tvinvest05;
    private TextView tvinvest06;
    private TextView tvinvest07;
    private TextView tvinvest09;
    private TextView tvinvest10;
    private TextView tvinvest11;
    private TextView tvinvest12;
    private ViewPager vp01;
    private Button btinvestnow;
    private MyViewPager01Adapter myViewPager01Adapter;
    private List<View> viewList = new ArrayList<View>();
    private String borrow_status, id, borrow_uid;
    private String webUrl1 = "";
    private String webUrl2 = "";
    private int[] heightList = new int[4];
    private LinearLayout ll01;

    View tab_1;
    View tab_2;
    View tab_3;
    View tab_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.haiying.p2papp.activity.R.layout.activity_credittransfer_detail);

        initView();
        tab_1.setVisibility(View.VISIBLE);
        tab_2.setVisibility(View.GONE);
        tab_3.setVisibility(View.GONE);
        tab_4.setVisibility(View.GONE);
        getAccessToken1();
        getAccessToken2();
        getAccessToken();
        tvinvest12.setOnClickListener(this);
        tvinvest11.setOnClickListener(this);
        tvinvest10.setOnClickListener(this);
        tvinvest09.setOnClickListener(this);
        ll01.setOnClickListener(this);
        vp01.addOnPageChangeListener(this);
        vp01.setOffscreenPageLimit(3);
        btinvestnow.setOnClickListener(this);
    }

    private void getAccessToken1() {
        new JsonAccessToken("index/bond_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {

                        webUrl1 = " http://vhost119.zihaistar.com/api/index/bond_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_info" + "&access_token=" + info.access_token;
                        //webUrl1 = "https://www.haiying365.com/api/index/bond_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_info"+"&access_token="+info.access_token;
                    } else {
                        UtilToast.show(CredittransferDetailActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(CredittransferDetailActivity.this, "安全验证失败！");
            }
        }).execute(this, true);
    }

    private void getAccessToken2() {
        new JsonAccessToken("index/bond_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {

                        webUrl2 = " http://vhost119.zihaistar.com/api/index/bond_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_risk" + "&access_token=" + info.access_token;
                        // webUrl2 = "https://www.haiying365.com/api/index/bond_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_risk"+"&access_token="+info.access_token;
                    } else {
                        UtilToast.show(CredittransferDetailActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(CredittransferDetailActivity.this, "安全验证失败！");
            }
        }).execute(this, true);
    }

    private void getAccessToken() {
        new JsonAccessToken("index/bond_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(CredittransferDetailActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(CredittransferDetailActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initData(String access_token) {
        new JsonBonddetail(access_token, getIntent().getStringExtra("id"), "detail", new AsyCallBack<JsonBonddetail.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonBonddetail.Info info) throws Exception {
                if (info != null) {
                    tvname.setText(info.borrowinfo.borrow_name);
                    tvrate.setText(info.borrowinfo.borrow_interest_rate + "%");
                    tvtime.setText(info.borrowinfo.period);
                    tvtimeUnit.setText(info.borrowinfo.borrow_duration_cn);
                    tvinvest01.setText(info.borrowinfo.borrow_duration + info.borrowinfo.borrow_duration_cn);
                    tvinvest02.setText(info.borrowinfo.repayment_type_cn);
                    tvinvest03.setText(info.borrowinfo.borrow_name);
                    tvinvest05.setText(info.borrowinfo.investor_interest + "元");
                    tvinvest06.setText(info.borrowinfo.investor_all + "元");
                    if (info.borrowinfo.borrow_max.equals("不限")) {
                        tvinvest07.setText(info.borrowinfo.borrow_max);
                    } else {
                        tvinvest07.setText(info.borrowinfo.borrow_max + "元");
                    }

                    tvinvest15.setText(info.borrowinfo.investor_capital + "元");
                    tv08.setText(info.borrowinfo.transfer_price );
//                    btinvestnow.setText(info.borrowinfo.status_cn);
                    borrow_status = info.borrowinfo.status;
                    id = info.borrowinfo.id;
                    borrow_uid = info.borrowinfo.borrow_uid;
                    tvname.setText(info.borrowinfo.borrow_name);
                    myViewPager01Adapter = new MyViewPager01Adapter(CredittransferDetailActivity.this, viewList, webUrl1, webUrl2, info.investList, info.payingList);
                    vp01.setAdapter(myViewPager01Adapter);
                    vp01.setCurrentItem(0);
                    tvinvest09.setSelected(true);
                } else {
                    UtilToast.show(CredittransferDetailActivity.this, JsonBonddetail.TOAST);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(this, false);
    }

    private void initView() {
        this.btinvestnow = (Button) findViewById(com.haiying.p2papp.activity.R.id.bt_invest_now);
        this.vp01 = (ViewPager) findViewById(com.haiying.p2papp.activity.R.id.vp_01);
        this.tvinvest12 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_12);
        this.tvinvest11 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_11);
        this.tvinvest10 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_10);
        this.tvinvest09 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_09);
        this.tvinvest07 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_07);
        this.tvinvest06 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_06);
        this.tvinvest05 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_05);
        this.tvinvest15 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_15);
        this.ll01 = (LinearLayout) findViewById(com.haiying.p2papp.activity.R.id.ll_01);
        this.tvinvest03 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_03);
        this.tvinvest02 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_02);
        this.tvinvest01 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_invest_01);
        this.tv08 = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_08);
        this.tvtimeUnit = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_timeUnit);
        this.tvtime = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_time);
        this.tvrate = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_rate);
        this.tvsumMoney = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_sumMoney);
        this.tvname = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_name);
        this.ivback = (ImageView) findViewById(com.haiying.p2papp.activity.R.id.iv_back);
        this.tvtitle = (TextView) findViewById(com.haiying.p2papp.activity.R.id.tv_title);

        tab_1 = findViewById(R.id.tab_1);
        tab_2 = findViewById(R.id.tab_2);
        tab_3 = findViewById(R.id.tab_3);
        tab_4 = findViewById(R.id.tab_4);

        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(com.haiying.p2papp.activity.R.layout.vp_01_web_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(com.haiying.p2papp.activity.R.layout.vp_02_web_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(com.haiying.p2papp.activity.R.layout.vp_03_list_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(com.haiying.p2papp.activity.R.layout.vp_04_list_item_view, null))));
        heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 328)));
        heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 328)));
        heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 328)));
        heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 328)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.haiying.p2papp.activity.R.id.ll_01:
                if (!TextUtils.isEmpty(borrow_uid)) {
                    Intent intent = new Intent();
                    intent.putExtra("id", borrow_uid);
                    intent.setClass(context, InvestmentActivity.class);
                    context.startActivity(intent);
                } else {
                    ToastUtils.show(this, "请获取数据后在点击！");
                }
                break;
            case com.haiying.p2papp.activity.R.id.tv_invest_09:
                vp01.setCurrentItem(0);
                tab_1.setVisibility(View.VISIBLE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.GONE);
                heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                break;
            case com.haiying.p2papp.activity.R.id.tv_invest_10:
                vp01.setCurrentItem(1);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.VISIBLE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.GONE);
                heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                break;
            case com.haiying.p2papp.activity.R.id.tv_invest_11:
                vp01.setCurrentItem(2);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.VISIBLE);

                tab_4.setVisibility(View.GONE);
                heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                break;
            case com.haiying.p2papp.activity.R.id.tv_invest_12:
                vp01.setCurrentItem(3);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.VISIBLE);
                heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 328)));
                break;
            case com.haiying.p2papp.activity.R.id.bt_invest_now:
                if (TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
                    ToastUtils.show(this, "请登录后再操作！");
                } else if (!TextUtils.isEmpty(borrow_status) && borrow_status.equals("2")) {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("flag", 2);
                    intent.setClass(context, BondCancelActivity.class);
                    context.startActivity(intent);
                } else {
                    ToastUtils.show(this, "该项目不能转让！");
                }
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetViewPagerHeight(position);
        switch (position) {
            case 0:
                initTab();
                tvinvest09.setSelected(true);
                tab_1.setVisibility(View.VISIBLE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.GONE);
                break;
            case 1:
                initTab();
                tvinvest10.setSelected(true);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.VISIBLE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.GONE);
                break;
            case 2:
                initTab();
                tvinvest11.setSelected(true);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.VISIBLE);

                tab_4.setVisibility(View.GONE);
                break;
            case 3:
                initTab();
                tvinvest12.setSelected(true);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void resetViewPagerHeight(int position) {
        measureViewPagerHeight(position);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vp01
                .getLayoutParams();
        params.height = heightList[position];
        vp01.setLayoutParams(params);
    }

    public void measureViewPagerHeight(int position) {
        View child = vp01.getChildAt(position);
        if (child != null) {
            child.measure(0, 0);
            int h = child.getMeasuredHeight();
            if (h > Math.round(ScreenUtils.dpToPx(this, 328))) {
                if (position == 3) {
                    heightList[0] = h;
                } else {
                    heightList[position + 1] = h;
                }

            }
        }

    }

    private void initTab() {
        tvinvest12.setSelected(false);
        tvinvest11.setSelected(false);
        tvinvest10.setSelected(false);
        tvinvest09.setSelected(false);
    }
}
