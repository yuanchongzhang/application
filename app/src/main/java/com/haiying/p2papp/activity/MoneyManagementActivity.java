package com.haiying.p2papp.activity;

import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;

import com.haiying.p2papp.adapter.MoneyManagement01Adapter;
import com.haiying.p2papp.adapter.MoneyManagement02Adapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonProductout;
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

public class MoneyManagementActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Boolean Mode = true;//默认回收中
    private ImageView ivback;
    private TextView tvmm01;
    private TextView tvmm02;
    private TextView tv01;
    private ImageView iv01;
    private ImageView iv02;
    private TextView tv02;
    private TextView tv03;
    private LinearLayout ll01;
    private TextView et01;
    private ImageView iv03;
    private TextView et02;
    private ImageView iv04;
    private LinearLayout ll02;
    private Button bt01;
    private RecyclerView rv01;
    private DatePickerDialog pickerDialog;
    private TextView curEditView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public String st, et;
    private List<JsonProductout.Info.ListContent> list = new ArrayList<>();
    private PtrFrameLayout store_house_ptr_frame;

    private PtrClassicDefaultHeader header;

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;


    private boolean canLoadMore = true;

    private String show_type;
    private View first_view;
    private View second_view;

    private TextView title_text;

    private TextView touzi_text_zi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_management);
        initView();
        initListener();
        initRefreshListener();
    }

    private void initListener() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                curEditView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                if (!list.isEmpty() && (!st.equals(et01.getText().toString()) || !et.equals(et02.getText().toString()))) {
                    list.clear();
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        tvmm01.setOnClickListener(this);
        tvmm02.setOnClickListener(this);
        et01.setOnClickListener(this);
        et02.setOnClickListener(this);
        iv01.setOnClickListener(this);
        iv02.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.ll02 = (LinearLayout) findViewById(R.id.ll_02);
        this.iv04 = (ImageView) findViewById(R.id.iv_04);
        this.et02 = (TextView) findViewById(R.id.et_02);
        this.iv03 = (ImageView) findViewById(R.id.iv_03);
        this.et01 = (TextView) findViewById(R.id.et_01);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.iv02 = (ImageView) findViewById(R.id.iv_02);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.tvmm02 = (TextView) findViewById(R.id.tv_mm_02);
        this.tvmm01 = (TextView) findViewById(R.id.tv_mm_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        title_text = (TextView) findViewById(R.id.title_text);
        touzi_text_zi= (TextView) findViewById(R.id.touzi_text_zi);
        first_view = findViewById(R.id.first_view);
        second_view = findViewById(R.id.second_view);
        second_view.setVisibility(View.GONE);
        first_view.setVisibility(View.VISIBLE);
        rv01.setHasFixedSize(true);
        // use a linear layout manager
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        tv03.setVisibility(View.VISIBLE);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, 40));

        mAdapter = new MoneyManagement01Adapter(this, list);
        rv01.setAdapter(mAdapter);
        tvmm01.setSelected(true);
        getAccessToken(true, nowPage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

            case R.id.tv_mm_01:
                Mode = true;
                initViewMode();
                second_view.setVisibility(View.GONE);
                first_view.setVisibility(View.VISIBLE);
                getAccessToken(true, nowPage);
                break;
            case R.id.tv_mm_02:
                Mode = false;
                initViewMode();
                second_view.setVisibility(View.VISIBLE);
                first_view.setVisibility(View.GONE);
                break;
            case R.id.iv_01:
            case R.id.et_01:
                curEditView = et01;
                creatDataDialog();
                break;
            case R.id.iv_02:
            case R.id.et_02:
                curEditView = et02;
                creatDataDialog();
                break;

        }
    }

    private void initViewMode() {
        if (Mode) {
            show_type = "tendbacking";
            tvmm01.setSelected(true);
            tvmm02.setSelected(false);
            ll01.setVisibility(View.GONE);
            ll02.setVisibility(View.VISIBLE);
            iv01.setVisibility(View.VISIBLE);
            iv02.setVisibility(View.VISIBLE);
            bt01.setVisibility(View.VISIBLE);
            title_text.setText("您目前的投资金额是：");
            tv03.setVisibility(View.VISIBLE);
            touzi_text_zi.setVisibility(View.VISIBLE);
            mAdapter = new MoneyManagement01Adapter(this, list);
            rv01.setAdapter(mAdapter);
            list.clear();
            nowPage = 1;
        } else {
            show_type = "tenddone";
            tvmm02.setSelected(true);
            tvmm01.setSelected(false);
            title_text.setText("您目前已回收的投资金额是：");
            tv03.setVisibility(View.GONE);
            touzi_text_zi.setVisibility(View.GONE);
            ll01.setVisibility(View.GONE);
            ll02.setVisibility(View.GONE);
            iv01.setVisibility(View.GONE);
            iv02.setVisibility(View.GONE);
            bt01.setVisibility(View.GONE);
            mAdapter = new MoneyManagement02Adapter(this, list);
            rv01.setAdapter(mAdapter);
            list.clear();
            nowPage = 1;
            getAccessToken(true, nowPage);
        }
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

    private void getAccessToken(final boolean isShow, final int page) {
        new JsonAccessToken("user/productout", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token, isShow, page);
                    } else {
                        UtilToast.show(MoneyManagementActivity.this, "安全验证失败！");
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
                UtilToast.show(MoneyManagementActivity.this, "安全验证失败！");
                canLoadMore = true;
                if (isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }


    private void initData(String access_token, final boolean isShow, final int page) {
        new JsonProductout(access_token, MyApplication.myPreferences.readUid(), String.valueOf(page), show_type, et01.getText().toString(), et02.getText().toString(), new AsyCallBack<JsonProductout.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonProductout.Info info) throws Exception {
                if (info != null) {
                    tv01.setText("￥" + info.total_money);
                    tv02.setText(","+"共" + info.total_num + "笔" + "投标。");
                    tv03.setText("￥" + info.total_money);
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                    if (list.isEmpty()) {
                        UtilToast.show(MoneyManagementActivity.this, "无相关记录！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MoneyManagementActivity.this, JsonProductout.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                mAdapter.notifyDataSetChanged();
                canLoadMore = true;
            }

        }).execute(MoneyManagementActivity.this, isShow);
    }

    private void initRefreshListener() {
        rv01.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                if (dy > 0 && canLoadMore && lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    if (nowPage < totalPages) {
                        canLoadMore = false;
                        getAccessToken(true, nowPage + 1);
                    } else {
                        UtilToast.show(MoneyManagementActivity.this, "当前页已是最后一页");
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
