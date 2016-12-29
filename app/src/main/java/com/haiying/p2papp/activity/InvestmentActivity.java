package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.MyViewPager01Adapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonInvestdetail;
import com.haiying.p2papp.view.ProgressBar;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.trinea.android.common.util.ScreenUtils;
import cn.trinea.android.common.util.ToastUtils;


public class InvestmentActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private CountdownView cvinvesttime;
    private TextView tvinvest09;
    private TextView tvinvest10;
    private TextView tvinvest11;
    private TextView tvinvest12;
    private TextView tvtitle;
    private ImageView ivback;
    private TextView tvname;
    private TextView tvsumMoney;
    private TextView tvrate;
    private TextView tvtime;
    private TextView tvtimeUnit;
    private ProgressBar crpv;
    private TextView tvprogress;
    private TextView tvinvest01;
    private TextView tvinvest02;
    private TextView tvinvest03;
    private TextView tvinvest05;
    private TextView tvinvest06;
    private TextView tvinvest07;
    private TextView tvinvest08;
    private Button btinvestnow;
    private String borrow_status, webUrl1, webUrl2, id;
    private List<View> viewList = new ArrayList<View>();
    private ViewPager vp01;
    private MyViewPager01Adapter myViewPager01Adapter;
    private int[] heightList = new int[4];

    View tab_1;
    View tab_2;
    View tab_3;
    View tab_4;
    private ScrollView scroll_huadonglayout;

private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        getAccessToken1();
        getAccessToken2();
        initView();
        tab_1.setVisibility(View.VISIBLE);
        tab_2.setVisibility(View.GONE);
        tab_3.setVisibility(View.GONE);
        tab_4.setVisibility(View.GONE);


        getAccessToken();
        tvinvest12.setOnClickListener(this);
        tvinvest11.setOnClickListener(this);
        tvinvest10.setOnClickListener(this);
        tvinvest09.setOnClickListener(this);
        btinvestnow.setOnClickListener(this);
        vp01.addOnPageChangeListener(this);
        vp01.setOffscreenPageLimit(3);


    }

    private void getAccessToken1() {
        new JsonAccessToken("index/invest_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        //   http://vhost119.zihaistar.com/api/index/invest_detail?id=3&show_type=borrow_info&access_token=4d2685fae1729bfa0d9ac59c939996a0
                        webUrl1 = " http://vhost119.zihaistar.com/api/index/invest_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_info" + "&access_token=" + info.access_token;
//                        webUrl1 = "https://www.haiying365.com/api/index/invest_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_info"+"&access_token="+info.access_token;
                    } else {
                        UtilToast.show(InvestmentActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(InvestmentActivity.this, "安全验证失败！");
            }
        }).execute(this, true);
    }

    private void getAccessToken2() {
        new JsonAccessToken("index/invest_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        //   http://vhost119.zihaistar.com/api/index/invest_detail?id=3&show_type=borrow_info&access_token=4d2685fae1729bfa0d9ac59c939996a0
                        webUrl2 = "http://vhost119.zihaistar.com/api/index/invest_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_risk" + "&access_token=" + info.access_token;
                    } else {
                        UtilToast.show(InvestmentActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(InvestmentActivity.this, "安全验证失败！");
            }
        }).execute(this, true);
    }

    private void getAccessToken() {
        new JsonAccessToken("index/invest_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(InvestmentActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(InvestmentActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initData(String access_token) {
        new JsonInvestdetail(access_token, getIntent().getStringExtra("id"), "detail", new AsyCallBack<JsonInvestdetail.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonInvestdetail.Info info) throws Exception {
                if (info != null) {
                    tvname.setText(info.borrowinfo.borrow_name);
                    tvsumMoney.setText(info.borrowinfo.borrow_money );
                    tvrate.setText(info.borrowinfo.borrow_interest_rate + "%");
                    tvtime.setText(info.borrowinfo.borrow_duration );
                    tvtimeUnit.setText(info.borrowinfo.borrow_duration_cn);
                    tvprogress.setText(info.borrowinfo.progress + "%");
                 /*   crpv.setStartAngle(getStartAngle(Float.parseFloat(info.borrowinfo.progress)));
                    crpv.setPercent(Float.parseFloat(info.borrowinfo.progress));*/


                    crpv.setProgress(Double.valueOf(info.borrowinfo.progress));

                    tvinvest02.setText(info.borrowinfo.repayment_type_cn);
                    tvinvest03.setText(info.borrowinfo.reward_type_cn);
                    tvinvest05.setText(info.borrowinfo.need + "元");
                    tvinvest06.setText(info.borrowinfo.borrow_max + "元");
                    tvinvest07.setText(info.borrowinfo.borrow_min + "元");
                    tvinvest08.setText(info.borrowinfo.reward_type_cn);
                    btinvestnow.setText(info.borrowinfo.borrow_status_cn);
                    borrow_status = info.borrowinfo.borrow_status;
                    id = info.borrowinfo.id;
//                    cvinvesttime.start(Integer.parseInt(info.borrowinfo.lefttime) * 1000);//ms
                    str=info.borrowinfo.lefttime;
                    try {
                        int a=Integer.parseInt(str);
                        int b=a*100;
                        cvinvesttime.start(b);//ms
                    }catch (Exception e){
                        e.getMessage();
                    }



                    tvname.setText(info.borrowinfo.borrow_name);
                    myViewPager01Adapter = new MyViewPager01Adapter(InvestmentActivity.this, viewList, webUrl1, webUrl2, info.investList, info.payingList);
                    scroll_huadonglayout.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {



                            return false;
                        }
                    });
                    vp01.setAdapter(myViewPager01Adapter);
                    vp01.setCurrentItem(0);
                    tvinvest09.setSelected(true);
                } else {
                    UtilToast.show(InvestmentActivity.this, JsonInvestdetail.TOAST);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(this, false);
    }

    private void initView() {
        this.btinvestnow = (Button) findViewById(R.id.bt_invest_now);
        this.vp01 = (ViewPager) findViewById(R.id.vp_01);
        this.tvinvest12 = (TextView) findViewById(R.id.tv_invest_12);
        this.tvinvest11 = (TextView) findViewById(R.id.tv_invest_11);
        this.tvinvest10 = (TextView) findViewById(R.id.tv_invest_10);
        this.tvinvest09 = (TextView) findViewById(R.id.tv_invest_09);
        this.tvinvest08 = (TextView) findViewById(R.id.tv_invest_08);
        this.tvinvest07 = (TextView) findViewById(R.id.tv_invest_07);
        this.tvinvest06 = (TextView) findViewById(R.id.tv_invest_06);
        this.tvinvest05 = (TextView) findViewById(R.id.tv_invest_05);
        this.cvinvesttime = (CountdownView) findViewById(R.id.cv_invest_time);
        this.tvinvest03 = (TextView) findViewById(R.id.tv_invest_03);
        this.tvinvest02 = (TextView) findViewById(R.id.tv_invest_02);
        this.tvinvest01 = (TextView) findViewById(R.id.tv_invest_01);
        this.tvprogress = (TextView) findViewById(R.id.tv_progress);
        this.crpv = (ProgressBar) findViewById(R.id.crpv);
        this.tvtimeUnit = (TextView) findViewById(R.id.tv_timeUnit);
        this.tvtime = (TextView) findViewById(R.id.tv_time);
        this.tvrate = (TextView) findViewById(R.id.tv_rate);
        this.tvsumMoney = (TextView) findViewById(R.id.tv_sumMoney);
        this.tvname = (TextView) findViewById(R.id.tv_name);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        tab_1 = findViewById(R.id.tab_1);
        tab_2 = findViewById(R.id.tab_2);
        tab_3 = findViewById(R.id.tab_3);
        tab_4 = findViewById(R.id.tab_4);


        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.vp_01_web_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.vp_02_web_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.vp_03_list_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.vp_04_list_item_view, null))));
     heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 385)));
        heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 385)));
        heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 385)));
        heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 385)));
        scroll_huadonglayout = (ScrollView) findViewById(R.id.scroll_huadonglayout);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
  cvinvesttime.stop();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_invest_09:
                vp01.setCurrentItem(0);
                tab_1.setVisibility(View.VISIBLE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.GONE);

          heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 385)));

                break;
            case R.id.tv_invest_10:
                vp01.setCurrentItem(1);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.VISIBLE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.GONE);
            heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 385)));

                break;
            case R.id.tv_invest_11:
                vp01.setCurrentItem(2);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.VISIBLE);

                tab_4.setVisibility(View.GONE);
              heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 385)));

                break;
            case R.id.tv_invest_12:
                vp01.setCurrentItem(3);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.GONE);

                tab_3.setVisibility(View.GONE);

                tab_4.setVisibility(View.VISIBLE);
             heightList[0] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[1] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[2] = (Math.round(ScreenUtils.dpToPx(this, 385)));
                heightList[3] = (Math.round(ScreenUtils.dpToPx(this, 385)));

                break;
            case R.id.bt_invest_now:
                if (TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
                    ToastUtils.show(this, "请登录后再操作！");
                } else if (!TextUtils.isEmpty(borrow_status) && borrow_status.equals("2")) {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("mode", true);
                    intent.setClass(this, PayActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.show(this, "该项目不能投资！");
                }
                break;
        }
    }

    private void initTab() {
        tvinvest12.setSelected(false);
        tvinvest11.setSelected(false);
        tvinvest10.setSelected(false);
        tvinvest09.setSelected(false);
    }

    private float getStartAngle(float i) {
        if (i > 95) {
            return (1 - ((i + 5 - 100) / 100)) * 360;
        } else {
            return (1 - ((i + 5) / 100)) * 360;
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

   /* public void resetViewPagerHeight(int position) {
        View child = vp01.getChildAt(position);
        if (child != null) {
            child.measure(0, 0);
            int h = child.getMeasuredHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vp01
                    .getLayoutParams();
            if (h < ScreenUtils.dpToPx(this, 85)) {
                params.height = Math.round(ScreenUtils.dpToPx(this, 85));
            } else {
                params.height = h;
            }
            vp01.setLayoutParams(params);
        }
    }*/

    public void resetViewPagerHeight(int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vp01
                .getLayoutParams();
        params.height = heightList[position];
        vp01.setLayoutParams(params);
        measureViewPagerHeight(position);

    }

    public void measureViewPagerHeight(int position) {
        View child = vp01.getChildAt(position);
        if (child != null) {
            child.measure(0, 0);
            int h = child.getMeasuredHeight();
            if (h > Math.round(ScreenUtils.dpToPx(this, 385))) {
                if (position == 3) {
                    heightList[0] = h;
                } else {
                    heightList[position + 1] = h;
                }

            }
        }
    }

}
