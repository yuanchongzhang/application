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
import com.haiying.p2papp.adapter.MyAssetsHistoryAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonCapital;
import com.haiying.p2papp.conn.JsonCapitalconfig;
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

public class MyAssetsHistoryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView ivback;
    private TextView et01;
    private ImageView iv01;
    private android.widget.LinearLayout ll02;
    private TextView et02;
    private ImageView iv02;
    private TextView et03;
    private ImageView iv03;
    private Button bt01;
    private RecyclerView rv01;
    private Spinner sp01;
    private TextView curEditView;
    private static final int DATE_DIALOG_ID = 0;
    private DatePickerDialog pickerDialog;
    private List<String> list = new ArrayList<>();
    private List<JsonCapitalconfig.Info.logtype> typelist = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private PtrFrameLayout store_house_ptr_frame;
    private PtrClassicDefaultHeader header;

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;

    private boolean canLoadMore = true;

    public String typeid, st, et;

    public List<JsonCapital.Info.ListContent> loglist = new ArrayList<>();

    private LinearLayout shijian_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assets_history);
        initView();
        initListener();
        initRefreshListener();
        getTypeAccessToken(false);
    }

    private void initView() {
        this.store_house_ptr_frame = (PtrFrameLayout) findViewById(R.id.store_house_ptr_frame);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.iv03 = (ImageView) findViewById(R.id.iv_03);
        this.et03 = (TextView) findViewById(R.id.et_03);
        this.iv02 = (ImageView) findViewById(R.id.iv_02);
        this.et02 = (TextView) findViewById(R.id.et_02);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.sp01 = (Spinner) findViewById(R.id.sp_01);
        this.et01 = (TextView) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);

        this.shijian_layout= (LinearLayout) findViewById(R.id.shijian_layout);
        shijian_layout.setOnClickListener(this);
        bt01.setSelected(false);
        rv01.setHasFixedSize(true);
        header = new PtrClassicDefaultHeader(this);
        store_house_ptr_frame.setHeaderView(header);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
       Drawable drawable = ContextCompat.getDrawable(this, R.drawable.line_divider2);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(this, drawable, 4));

        // specify an adapter (see also next example)
        mAdapter = new MyAssetsHistoryAdapter(this, loglist);
        rv01.setAdapter(mAdapter);
    }

    private void initListener() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                curEditView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                if (!loglist.isEmpty() && (!st.equals(et02.getText().toString()) || !et.equals(et03.getText().toString()))) {
                    loglist.clear();
                    bt01.setSelected(false);
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        sp01.setOnItemSelectedListener(this);
        et01.setOnClickListener(this);
        et02.setOnClickListener(this);
        et03.setOnClickListener(this);
        iv01.setOnClickListener(this);
        iv02.setOnClickListener(this);
        iv03.setOnClickListener(this);
        bt01.setOnClickListener(this);

    }

    private void getTypeAccessToken(final Boolean isClick) {
        new JsonAccessToken("user/capital_config", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        getType(info.access_token,isClick);
                    } else {
                        UtilToast.show(MyAssetsHistoryActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MyAssetsHistoryActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void getType(String access_token,final Boolean isClick) {

        new JsonCapitalconfig(access_token,MyApplication.myPreferences.readUid(), new AsyCallBack<JsonCapitalconfig.Info>() {


            @Override
            public void onSuccess(String toast, int type, JsonCapitalconfig.Info info) throws Exception {
                if (info != null) {
                    if (!list.isEmpty())
                        list.clear();
                    for (int i = 0; i < info.list.size(); i++) {
                        list.add(info.list.get(i).name);
                    }
                    typelist.addAll(info.list);
                    CmSpinnerAdapter cmSpinnerAdapter = new CmSpinnerAdapter(list);
                    sp01.setAdapter(cmSpinnerAdapter);
                    if (isClick) {
                        sp01.performClick();
                    }
                    if (list.isEmpty()) {
                        UtilToast.show(MyAssetsHistoryActivity.this, "无相关记录！");
                    }
                }
            }


            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(MyAssetsHistoryActivity.this, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_01:
            case R.id.et_01:
                if (list.isEmpty()) {
                    getTypeAccessToken(true);
                } else {
                    if (sp01.getSelectedItemPosition()==0)
                    {et01.setText(list.get(0));}
                    sp01.performClick();
                    sp01.setSelected(true);
                }
                break;
            case R.id.iv_02:
            case R.id.et_02:
                curEditView = et02;
                creatDataDialog();
                break;
            case R.id.iv_03:
            case R.id.et_03:
                curEditView = et03;
                creatDataDialog();
                break;
            case R.id.bt_01:
                if (TextUtils.isEmpty(et01.getText().toString())) {
                    ToastUtils.show(this, "请设置类型");
                } else if (TextUtils.isEmpty(et02.getText().toString())) {
                    ToastUtils.show(this, "请起始时间");
                } else if (TextUtils.isEmpty(et03.getText().toString())) {
                    ToastUtils.show(this, "请截止时间");
                } else {
                    String[] startTime = et02.getText().toString().split("-");
                    String[] endTime = et03.getText().toString().split("-");
                    if (Integer.parseInt(endTime[0]) < Integer.parseInt(startTime[0]) || ((Integer.parseInt(startTime[0]) == Integer.parseInt(endTime[0]) && Integer.parseInt(endTime[1]) < Integer.parseInt(startTime[1]))) || ((Integer.parseInt(startTime[0]) == Integer.parseInt(endTime[0]) && Integer.parseInt(startTime[1]) == Integer.parseInt(endTime[1]) && Integer.parseInt(endTime[2]) <= Integer.parseInt(startTime[2])))) {
                        ToastUtils.show(this, "请设置正确的截止时间");
                    } else {
                        typeid = typelist.get(sp01.getSelectedItemPosition()).id;
                        st = et02.getText().toString();
                        et = et03.getText().toString();
                        bt01.setSelected(true);
                        loglist.clear();
                        nowPage = 1;
                        getAccessToken(true,nowPage);
                    }
                }
                break;

            case R.id.shijian_layout:
                if (list.isEmpty()) {
                    getTypeAccessToken(true);
                } else {
                    if (sp01.getSelectedItemPosition()==0)
                    {et01.setText(list.get(0));}
                    sp01.performClick();
                    sp01.setSelected(true);
                }
                break;

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (sp01.isSelected()) {
            et01.setText(list.get(position));
            if (!loglist.isEmpty() && !typeid.equals(typelist.get(sp01.getSelectedItemPosition()).id)) {
                loglist.clear();
                bt01.setSelected(false);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getAccessToken(final boolean isShow,final int page) {
        new JsonAccessToken("user/capital", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token,isShow,page);
                    } else {
                        UtilToast.show(MyAssetsHistoryActivity.this, "安全验证失败！");
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
                UtilToast.show(MyAssetsHistoryActivity.this, "安全验证失败！");
                canLoadMore = true;
                if (isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
            }
        }).execute(this, isShow);

    }


    private void initData(String access_token,final boolean isShow,final int page) {
        new JsonCapital(access_token,MyApplication.myPreferences.readUid(), String.valueOf(page), typelist.get(sp01.getSelectedItemPosition()).id, et02.getText().toString(), et03.getText().toString(), new AsyCallBack<JsonCapital.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
//                if (isShow && nowPage == 1) {
////                    store_house_ptr_frame.autoRefresh();
//                }
            }

            @Override
            public void onSuccess(String toast, int type, JsonCapital.Info info) throws Exception {
                if (info != null) {
                    loglist.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
                    if (loglist.isEmpty()) {
                        UtilToast.show(MyAssetsHistoryActivity.this, "无记录！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(MyAssetsHistoryActivity.this, JsonCapital.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                canLoadMore = true;
                if (isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                mAdapter.notifyDataSetChanged();
            }

        }).execute(MyAssetsHistoryActivity.this, isShow);
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
                        getAccessToken(true,nowPage+1);
                    } else {
                        UtilToast.show(MyAssetsHistoryActivity.this, "当前页已是最后一页");
                    }
                }
            }
        });

        store_house_ptr_frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
                return (firstVisibleItemPosition == 0 || list.size() == 0) && bt01.isSelected();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loglist.clear();
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
