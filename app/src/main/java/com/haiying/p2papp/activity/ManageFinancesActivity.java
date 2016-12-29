package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.MyViewPager02Adapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonProductdetail;
import com.haiying.p2papp.view.ProgressBar;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ScreenUtils;
import cn.trinea.android.common.util.ToastUtils;

public class ManageFinancesActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private TextView tvtitle;
    private ImageView ivback;
    private TextView tvname;
    private TextView tvsumMoney;
    private TextView tvrate;
    private TextView tvtime;
    private TextView tvtimeUnit;
    private ProgressBar crpv;
    private TextView tvprogress;
    private TextView tvmf01;
    private TextView tvmf02;
    private TextView tvmf03;
    private TextView tvmf04;
    private TextView tvmf05;
    private TextView tvinvest09;
    private TextView tvinvest10;
    private EditText etmf01;
    private Button btinvestnow;
    private String borrow_status, webUrl, id;
    private ViewPager vp01;
    private MyViewPager02Adapter viewPager02Adapter;
    private List<View> viewList = new ArrayList<View>();

    private View tab_2;
    private View tab_1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_finances);
        initView();
        tab_1.setVisibility(View.VISIBLE);
        tab_2.setVisibility(View.GONE);
        getAccessToken1();
        getAccessToken();
        vp01.addOnPageChangeListener(this);
        vp01.setOffscreenPageLimit(1);
        tvinvest09.setOnClickListener(this);
        tvinvest10.setOnClickListener(this);
        btinvestnow.setOnClickListener(this);

    }

    private void initView() {
        this.btinvestnow = (Button) findViewById(R.id.bt_invest_now);
        this.etmf01 = (EditText) findViewById(R.id.et_mf_01);
        this.vp01 = (ViewPager) findViewById(R.id.vp_01);
        this.tvinvest10 = (TextView) findViewById(R.id.tv_invest_10);
        this.tvinvest09 = (TextView) findViewById(R.id.tv_invest_09);
        this.tvmf05 = (TextView) findViewById(R.id.tv_mf_05);
        this.tvmf04 = (TextView) findViewById(R.id.tv_mf_04);
        this.tvmf03 = (TextView) findViewById(R.id.tv_mf_03);
        this.tvmf02 = (TextView) findViewById(R.id.tv_mf_02);
        this.tvmf01 = (TextView) findViewById(R.id.tv_mf_01);
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

        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.vp_01_web_item_view, null))));
        viewList.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.vp_03_list_item_view, null))));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_invest_09:
                vp01.setCurrentItem(0);
                tab_1.setVisibility(View.VISIBLE);
                tab_2.setVisibility(View.GONE);
                break;
            case R.id.tv_invest_10:
                vp01.setCurrentItem(1);
                tab_1.setVisibility(View.GONE);
                tab_2.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_invest_now:
                if (TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
                    ToastUtils.show(this, "请登录后再操作！");
                } else if (!TextUtils.isEmpty(borrow_status) && borrow_status.equals("2")) {
                    Intent intent = new Intent();
                    intent.putExtra("id", id);
                    intent.putExtra("mode", false);
                    intent.setClass(this, PayActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.show(this, "该项目不能投资！");
                }
                break;
        }
    }

    private void getAccessToken() {
        new JsonAccessToken("index/product_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(ManageFinancesActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ManageFinancesActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void getAccessToken1() {
        new JsonAccessToken("index/product_detail", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        webUrl = "https://www.haiying365.com/api/index/product_detail?id=" + getIntent().getStringExtra("id") + "&show_type=borrow_info&access_token=" + info.access_token;
                    } else {
                        UtilToast.show(ManageFinancesActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ManageFinancesActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void initData(String access_token) {
        new JsonProductdetail(access_token, getIntent().getStringExtra("id"), "detail", new AsyCallBack<JsonProductdetail.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonProductdetail.Info info) throws Exception {
                if (info != null) {
                    tvname.setText(info.borrowinfo.borrow_name);
                    tvsumMoney.setText(info.borrowinfo.borrow_money);
                    tvrate.setText(info.borrowinfo.borrow_interest_rate + "%");
                    tvtime.setText(info.borrowinfo.borrow_duration );
                    tvtimeUnit.setText(info.borrowinfo.borrow_duration_cn);
                    tvprogress.setText(info.borrowinfo.progress + "%");
                 /*   crpv.setStartAngle(getStartAngle(Float.parseFloat(info.borrowinfo.progress)));
                    crpv.setPercent(Float.parseFloat(info.borrowinfo.progress));*/
                    // crpv.setProgress(Float.parseFloat(info.borrowinfo.progress));
                    int a;
                    String string = info.borrowinfo.progress;
                    if (string.contains("100")){
                        crpv.setProgress2(100);
                    }else {
                        Double d=Double.valueOf(string);

                        crpv.setProgress(d);
                    }




                   // a = Integer.parseInt(string);
                   /* if (a<100){

                    }else {
                        crpv.setProgress2(a);
                    }

                    crpv.setProgress(10);*/

                    /*   try {
                        a = Integer.parseInt(string);
                        Log.d(a + "", "adsfkjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjl");
                        if (a < 100) {
                            crpv.setProgress3(Integer.parseInt(string) + 1);
                        } else {
                            crpv.setProgress3(Integer.parseInt(string));
                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }*/

                    // crpv.setProgress(Float.parseFloat(info.borrowinfo.progress));

                    tvmf01.setText(info.borrowinfo.has_borrow + "元");
                    tvmf02.setText(info.borrowinfo.repayment_type_cn);
                    tvmf03.setText(info.borrowinfo.deadline);
                    Log.d(info.borrowinfo.deadline,"44444444444444444444444444");

                    tvmf04.setText(info.borrowinfo.interest_type);
                    tvname.setText(info.borrowinfo.borrow_name);
                    borrow_status = info.borrowinfo.borrow_status;
                    id = info.borrowinfo.id;
                    btinvestnow.setText(info.borrowinfo.borrow_status_cn);
                    viewPager02Adapter = new MyViewPager02Adapter(ManageFinancesActivity.this, viewList, webUrl, info.list);
                    vp01.setAdapter(viewPager02Adapter);
                    vp01.setCurrentItem(0);
                    tvinvest09.setSelected(true);
                } else {
                    UtilToast.show(ManageFinancesActivity.this, JsonProductdetail.TOAST);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(this, false);
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
        switch (position) {
            case 0:
                resetViewPagerHeight(1);
                tvinvest10.setSelected(false);
                tvinvest09.setSelected(true);
                break;
            case 1:
                resetViewPagerHeight(0);
                tvinvest09.setSelected(false);
                tvinvest10.setSelected(true);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void resetViewPagerHeight(int position) {
        View child = vp01.getChildAt(position);
        if (child != null) {
            child.measure(0, 0);
            int h = child.getMeasuredHeight();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vp01
                    .getLayoutParams();
            if (h < ScreenUtils.dpToPx(this, 142)) {
                params.height = Math.round(ScreenUtils.dpToPx(this, 142));
            } else {
                params.height = h;
            }
            vp01.setLayoutParams(params);
        }
    }
}
