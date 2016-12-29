package com.haiying.p2papp.activity;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;

import com.haiying.p2papp.adapter.CmSpinnerAdapter;
import com.haiying.p2papp.adapter.InvestManageDetail01Adapter;
import com.haiying.p2papp.adapter.InvestManageDetail02Adapter;
import com.haiying.p2papp.adapter.InvestManageDetail03Adapter;
import com.haiying.p2papp.adapter.InvestManageDetailAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonTendout;
import com.haiying.p2papp.view.SimpleDividerItemDecoration;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class InvestManageDetailActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private TextView tvtitle;
    private TextView tv01;
    private ImageView iv01;
    private Spinner sp01;
    private RecyclerView rv01;
    private List<String> stringList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<JsonTendout.Info.ListContent> list = new ArrayList<>();
    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;
    private ImageView ivback;
    private TextView tvtitleright;
    private String show_type = "tending";

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;
    private boolean canLoadMore = true;
    private TextView tv04;
    private LinearLayout ll01;
    private ImageView iv03;
    private ImageView iv02;
    private TextView tv02;
    private LinearLayout ll02;
    private TextView tv03;
    private LinearLayout ll03;
    private TextView et01;
    private ImageView iv04;
    private TextView et02;
    private ImageView iv05;
    private LinearLayout ll04;
    private Button bt01;

    private DatePickerDialog pickerDialog;
    private TextView curEditView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public String st, et;


    private TextView jingbiao_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_manage_detail);

        initView();
        initListener();
        initRefreshListener();
    }

    private void initListener() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                curEditView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
               /* if (!list.isEmpty() && (!st.equals(et01.getText().toString()) || !et.equals(et02.getText().toString()))) {
               // list.clear();
                    mAdapter.notifyDataSetChanged();
                }*/
                list.clear();
                mAdapter.notifyDataSetChanged();

            }
        };
        iv01.setOnClickListener(this);
        tv01.setOnClickListener(this);
        et01.setOnClickListener(this);
        et02.setOnClickListener(this);
        iv04.setOnClickListener(this);
        iv05.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.ll04 = (LinearLayout) findViewById(R.id.ll_04);
        this.iv05 = (ImageView) findViewById(R.id.iv_05);
        this.et02 = (TextView) findViewById(R.id.et_02);
        this.iv04 = (ImageView) findViewById(R.id.iv_04);
        this.et01 = (TextView) findViewById(R.id.et_01);
        this.ll03 = (LinearLayout) findViewById(R.id.ll_03);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.ll02 = (LinearLayout) findViewById(R.id.ll_02);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.iv02 = (ImageView) findViewById(R.id.iv_02);
        this.iv03 = (ImageView) findViewById(R.id.iv_03);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.tv04 = (TextView) findViewById(R.id.tv_04);
        this.sp01 = (Spinner) findViewById(R.id.sp_01);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        //jingbiao
        jingbiao_txt = (TextView) findViewById(R.id.jingbiao_txt);
        stringList.clear();
        stringList.add("竞标中的投资");
        stringList.add("回收中的投资");
        stringList.add("已回收的投资");
        stringList.add("逾期的投资");
        CmSpinnerAdapter cmSpinnerAdapter = new CmSpinnerAdapter(stringList);
        sp01.setAdapter(cmSpinnerAdapter);
        sp01.setOnItemSelectedListener(this);
        sp01.setSelection(getIntent().getIntExtra("id", 0));
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);
        mLayoutManager = new LinearLayoutManager(this);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, 40));

        rv01.setLayoutManager(mLayoutManager);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_01:
            case R.id.iv_01:
                sp01.performClick();
                break;

            case R.id.bt_01:
                if (TextUtils.isEmpty(et01.getText().toString())) {
                    ToastUtils.show(this, "请起始时间");
                } else if (TextUtils.isEmpty(et02.getText().toString())) {
                    ToastUtils.show(this, "请截止时间");
                } else {
                    String[] startTime = et01.getText().toString().split("-");
                    String[] endTime = et02.getText().toString().split("-");
                    if (Integer.parseInt(endTime[0]) < Integer.parseInt(startTime[0]) || ((Integer.parseInt(startTime[0]) == Integer.parseInt(endTime[0]) && Integer.parseInt(endTime[1]) < Integer.parseInt(startTime[1]))) || ((Integer.parseInt(startTime[0]) == Integer.parseInt(endTime[0]) && Integer.parseInt(startTime[1]) == Integer.parseInt(endTime[1]) && Integer.parseInt(endTime[2]) <= Integer.parseInt(startTime[2])))) {
                        ToastUtils.show(this, "请设置正确的截止时间");
                    } else {
                        list.clear();
                        nowPage = 1;
                        getAccessToken(true, nowPage);
                    }
                }
                break;

            case R.id.iv_04:
            case R.id.et_01:
                curEditView = et01;
                creatDataDialog();
                break;
            case R.id.iv_05:
            case R.id.et_02:
                curEditView = et02;
                creatDataDialog();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvtitle.setText(stringList.get(position));
        tv01.setText(stringList.get(position));
        if (!list.isEmpty()) {
            list.clear();
        }
        et01.setText("");
        et02.setText("");
        nowPage = 1;
        switch (position) {
            case 0:
                show_type = "tending";
                iv02.setVisibility(View.GONE);
                iv03.setVisibility(View.GONE);
                ll01.setVisibility(View.VISIBLE);
                ll02.setVisibility(View.GONE);
                ll03.setVisibility(View.GONE);
                ll04.setVisibility(View.GONE);
                bt01.setVisibility(View.GONE);

                mAdapter = new InvestManageDetailAdapter(this, list);
                rv01.setAdapter(mAdapter);
                getAccessToken(true, nowPage);
                break;
            case 1:
                iv02.setVisibility(View.GONE);
                iv03.setVisibility(View.GONE);
                ll01.setVisibility(View.VISIBLE);
                ll02.setVisibility(View.GONE);
                ll03.setVisibility(View.GONE);
                ll04.setVisibility(View.VISIBLE);
                bt01.setVisibility(View.VISIBLE);
                show_type = "tendbacking";
                jingbiao_txt.setText("您目前回收的投资总额为");
                mAdapter = new InvestManageDetail03Adapter(this, list);
                rv01.setAdapter(mAdapter);
                getAccessToken(true, nowPage);
                break;
            case 2:
                show_type = "tenddone";
                iv02.setVisibility(View.GONE);
                iv03.setVisibility(View.INVISIBLE);
                ll01.setVisibility(View.VISIBLE);
                ll02.setVisibility(View.GONE);
                ll03.setVisibility(View.GONE);
                ll04.setVisibility(View.GONE);
                bt01.setVisibility(View.GONE);
                mAdapter = new InvestManageDetail02Adapter(this, list);
                rv01.setAdapter(mAdapter);
                getAccessToken(true, nowPage);
                break;
            case 3:
                ll01.setVisibility(View.GONE);
                ll02.setVisibility(View.GONE);
                ll03.setVisibility(View.GONE);
                ll04.setVisibility(View.GONE);
                bt01.setVisibility(View.GONE);
                show_type = "tendbreak";
                mAdapter = new InvestManageDetail01Adapter(this, list);
                rv01.setAdapter(mAdapter);
                getAccessToken(true, nowPage);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getAccessToken(final boolean isShow, final int page) {
        new JsonAccessToken("user/tendout", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token, isShow, page);
                    } else {
                        UtilToast.show(InvestManageDetailActivity.this, "安全验证失败！");
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
                UtilToast.show(InvestManageDetailActivity.this, "安全验证失败！");
                canLoadMore = true;
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }

    private void initData(String access_token, final boolean isShow, final int page) {
        new JsonTendout(access_token, String.valueOf(page), MyApplication.myPreferences.readUid(), show_type, et01.getText().toString(), et02.getText().toString(),
                new AsyCallBack<JsonTendout.Info>() {

                    @Override
                    public void onStart(int type) throws Exception {
                        super.onStart(type);
                    }

                    @Override
                    public void onSuccess(String toast, int type, JsonTendout.Info info) throws Exception {
                        if (info != null) {
                            list.addAll(info.list);
                            tv04.setText("￥" + info.total_money + "元");
                            tv02.setText(","+"共" + info.total_num + "笔投资");
                            tv03.setText("￥" + info.total_money);

                            nowPage = info.page.nowPage;
                            totalPages = info.page.totalPages;
                            pageSize = info.page.pageSize;
                            totalRows = info.page.totalRows;
                            if (list.isEmpty()) {
                                ToastUtils.show(InvestManageDetailActivity.this, "无记录！");
                            }
                        }
                    }

                    @Override
                    public void onFail(String toast, int type) throws Exception {
                        super.onFail(toast, type);
                        UtilToast.show(InvestManageDetailActivity.this, JsonTendout.TOAST);
                    }

                    @Override
                    public void onEnd(String toast, int type) throws Exception {
                        if (!isShow) {
                            store_house_ptr_frame.refreshComplete();
                        }
                        canLoadMore = true;
                        mAdapter.notifyDataSetChanged();
                    }

                }).execute(InvestManageDetailActivity.this, isShow);
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
                        UtilToast.show(InvestManageDetailActivity.this, "当前页已是最后一页");
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


    private void creatDataDialog() {
        if (TextUtils.isEmpty(curEditView.getText().toString())) {
            pickerDialog = new DatePickerDialog(this, mDateSetListener, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        } else {
            String[] data = curEditView.getText().toString().split("-");
            pickerDialog = new DatePickerDialog(this, mDateSetListener, Integer.parseInt(data[0]), (Integer.parseInt(data[1]) - 1), Integer.parseInt(data[2]));
        }
        pickerDialog.show();
    }
}
