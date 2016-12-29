package com.haiying.p2papp.fragment;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.adapter.MyInvestlistAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonInvest;
import com.haiying.p2papp.conn.JsonInvestfilter;
import com.haiying.p2papp.conn.JsonProduct;
import com.haiying.p2papp.view.SimpleDividerItemDecoration;
import com.zcx.helper.activity.AppV4Fragment;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends AppV4Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    @Bind(R.id.ll_tab_left)
    LinearLayout llTabLeft;
    @Bind(R.id.ll_tab_right)
    LinearLayout llTabRight;
    @Bind(R.id.rv_01)
    RecyclerView rv01;
    @Bind(R.id.tv_sp_1)
    TextView tvSp1;
    @Bind(R.id.iv_sp_1)
    ImageView ivSp1;
    @Bind(R.id.ll_sp_1)
    LinearLayout llSp1;
    @Bind(R.id.tv_sp_2)
    TextView tvSp2;
    @Bind(R.id.iv_sp_2)
    ImageView ivSp2;
    @Bind(R.id.ll_sp_2)
    LinearLayout llSp2;
    @Bind(R.id.tv_sp_3)
    TextView tvSp3;
    @Bind(R.id.iv_sp_3)
    ImageView ivSp3;
    @Bind(R.id.ll_sp_3)
    LinearLayout llSp3;
    @Bind(R.id.tv_sp_4)
    TextView tvSp4;
    @Bind(R.id.iv_sp_4)
    ImageView ivSp4;
    @Bind(R.id.ll_sp_4)
    LinearLayout llSp4;
    /* @Bind(R.id.sp_01)
     Spinner sp01;
     @Bind(R.id.sp_02)
     Spinner sp02;
     @Bind(R.id.sp_03)
     Spinner sp03;
     @Bind(R.id.sp_04)
     Spinner sp04;*/
    @Bind(R.id.store_house_ptr_frame)
    PtrFrameLayout store_house_ptr_frame;

    private PtrClassicDefaultHeader header;
    private int isInvest = 1;//MyInvestlistAdapter.INVESTMODE
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> list0 = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private List<String> list3 = new ArrayList<>();
    public List<JsonInvestfilter.Info.ListContent> borrowTypeList;
    public List<JsonInvestfilter.Info.ListContent> borrowInterestRateList;
    public List<JsonInvestfilter.Info.ListContent> borrowDurationList;
    public List<JsonInvestfilter.Info.ListContent> borrowStatusList;
    private List<JsonInvest.Info.ListContent> list = new ArrayList<>();

    public int nowPage = 1;
    public int totalPages = 1;
    public int pageSize = 0;
    public int totalRows = 0;

    public boolean canLoadMore = true;

    private String show_type = "normal";

    private TextView tv_invest_list;

    private TextView tv_regular_list;
    private PopupWindow popLeft;
    private View layoutLeft;

    private ListView menulistLeft;

    private String select_id;

    private String shouyi_id;

    private String qixian_id;

    private String toubiao_id;

    private LinearLayout pop_layout;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) inflater.inflate(R.layout.fragment_second, null)));
        ButterKnife.bind(this, view);
        tv_invest_list = (TextView) view.findViewById(R.id.tv_invest_list);
        tv_regular_list = (TextView) view.findViewById(R.id.tv_regular_list);
        pop_layout = (LinearLayout) view.findViewById(R.id.pop_layout);
        llTabLeft.setSelected(true);


        initView();
        initListener();
        initRefreshListener();
        return view;
    }


    private void getTypeAccessToken() {
        new JsonAccessToken("index/invest_filter", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initTypeData(info.access_token);
                    } else {
                        UtilToast.show(getActivity(), "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(getActivity(), "安全验证失败！");
            }
        }).execute(getActivity(), false);

    }


    private void getInvestDataAccessToken(final boolean b, final int page) {
        new JsonAccessToken("index/invest", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initInvestData(info.access_token, b, page);
                    } else {
                        canLoadMore = true;
                        UtilToast.show(getActivity(), "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                canLoadMore = true;
                UtilToast.show(getActivity(), "安全验证失败！");
            }
        }).execute(getActivity(), false);

    }


    private void getProductDataAccessToken(final boolean b, final int page) {
        new JsonAccessToken("index/product", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initProductData(info.access_token, b, page);
                    } else {
                        canLoadMore = true;
                        UtilToast.show(getActivity(), "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                canLoadMore = true;
                UtilToast.show(getActivity(), "安全验证失败！");
            }
        }).execute(getActivity(), false);

    }


    private void initTypeData(String access_token) {
        new JsonInvestfilter(access_token, new AsyCallBack<JsonInvestfilter.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonInvestfilter.Info info) throws Exception {
                if (info != null) {
                    borrowTypeList = info.borrowTypeList;
                    borrowInterestRateList = info.borrowInterestRateList;
                    borrowDurationList = info.borrowDurationList;
                    borrowStatusList = info.borrowStatusList;
                    for (int i = 0; i < borrowTypeList.size(); i++) {
                        list0.add(borrowTypeList.get(i).name);
                    }
                    for (int i = 0; i < borrowInterestRateList.size(); i++) {
                        list1.add(borrowInterestRateList.get(i).name);
                    }
                    for (int i = 0; i < borrowDurationList.size(); i++) {
                        list2.add(borrowDurationList.get(i).name);
                    }
                    for (int i = 0; i < borrowStatusList.size(); i++) {
                        list3.add(borrowStatusList.get(i).name);
                    }
                  /*  sp01.setAdapter(new CmSpinnerAdapter(list0));
                    sp01.setOnItemSelectedListener(SecondFragment.this);
                    sp02.setAdapter(new CmSpinnerAdapter(list1));
                    sp02.setOnItemSelectedListener(SecondFragment.this);
                    sp03.setAdapter(new CmSpinnerAdapter(list2));
                    sp03.setOnItemSelectedListener(SecondFragment.this);
                    sp04.setAdapter(new CmSpinnerAdapter(list3));
                    sp04.setOnItemSelectedListener(SecondFragment.this);*/
                    //  list.clear();
/*
                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    } else {
                        layoutLeft = getActivity().getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistLeft = (ListView) layoutLeft
                                .findViewById(R.id.menulist);
                        menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, list0));

                        menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // textCity.setText(list.get(position));
                                //   et03.setText(borrow_use.get(position).name);
                                tvSp1.setText(list0.get(position));
                                select_id = borrowTypeList.get(position).id;
                                // popupWindow.dismiss();
                                list.clear();
                                nowPage = 1;
                                initData(true, nowPage);
                                popLeft.dismiss();
                            }
                        });
                        popLeft = new PopupWindow(layoutLeft, llSp1.getWidth(),
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorWhite));
//            ColorDrawable cd = new ColorDrawable(-0000);
                        //ColorDrawable cd = new ColorDrawable(0xffff0000);
                        popLeft.setBackgroundDrawable(cd);
                        popLeft.setAnimationStyle(R.style.PopupAnimation);
                        popLeft.update();
                        popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popLeft.setTouchable(true); // 设置popupwindow可点击
                        popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popLeft.setFocusable(true); // 获取焦点

                        popLeft.showAsDropDown(llSp1, 0, 0);
                        popLeft.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popLeft.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });

                    }*/
                    select_id = borrowTypeList.get(0).id;
                    toubiao_id = borrowStatusList.get(0).id;

                    nowPage = 1;
                    initData(false, nowPage);
                } else {
                    UtilToast.show(getActivity(), JsonInvestfilter.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(getActivity(), false);

    }

    private void initView() {
        tvSp1.setText("类型");
        tvSp2.setText("收益");
        tvSp3.setText("期限");
        tvSp4.setText("状态");
        rv01.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());


        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new MyInvestlistAdapter(getActivity(), list, isInvest);
        Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.line_divider);
        rv01.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), drawable, 40));
        rv01.setAdapter(mAdapter);
        header = new PtrClassicDefaultHeader(getActivity());
        store_house_ptr_frame.setHeaderView(header);
        if (list0.isEmpty()) {
            getTypeAccessToken();
        } else {
       /*     sp01.setAdapter(new CmSpinnerAdapter(list0));
            sp01.setOnItemSelectedListener(SecondFragment.this);
            sp02.setAdapter(new CmSpinnerAdapter(list1));
            sp02.setOnItemSelectedListener(SecondFragment.this);
            sp03.setAdapter(new CmSpinnerAdapter(list2));
            sp03.setOnItemSelectedListener(SecondFragment.this);
            sp04.setAdapter(new CmSpinnerAdapter(list3));
            sp04.setOnItemSelectedListener(SecondFragment.this);*/
            list.clear();
            nowPage = 1;
            initData(false, nowPage);
        }
    }

    private void initListener() {
        llTabRight.setOnClickListener(this);
        llTabLeft.setOnClickListener(this);
        ivSp1.setOnClickListener(this);
        ivSp2.setOnClickListener(this);
        ivSp3.setOnClickListener(this);
        ivSp4.setOnClickListener(this);


        llSp1.setOnClickListener(this);
        llSp2.setOnClickListener(this);

        llSp3.setOnClickListener(this);
        llSp4.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_left:
                isInvest = MyInvestlistAdapter.INVESTMODE;
                llTabLeft.setSelected(true);
                llTabRight.setSelected(false);
             /*   sp01.setSelected(false);
                sp02.setSelected(false);
                sp03.setSelected(false);
                sp04.setSelected(false);*/
                //   llTabRight.setBackground(getResources().getDrawable(R.color.colorLakeBlue));
                tv_regular_list.setTextColor(getResources().getColor(R.color.touzilicai_color));
                tv_invest_list.setTextColor(getResources().getColor(R.color.colorWhite));
//                initView();
                mAdapter = new MyInvestlistAdapter(getActivity(), list, isInvest);
           /*     Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.line_divider);
                rv01.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), drawable, 40));*/
                rv01.setAdapter(mAdapter);
//                header = new PtrClassicDefaultHeader(getActivity());
//                store_house_ptr_frame.setHeaderView(header);
                list.clear();
                nowPage = 1;
                initData(false, nowPage);
                break;
            case R.id.ll_tab_right:
              //  isInvest = MyInvestlistAdapter.MANAGEFINANCESMODE;
                isInvest =2;
                llTabLeft.setSelected(false);
                llTabRight.setSelected(true);

              /*  sp01.setSelected(false);
                sp02.setSelected(false);
                sp03.setSelected(false);
                sp04.setSelected(false);*/
                //  llTabRight.setBackground(getResources().getDrawable(R.drawable.selector_color_main_02_tab_background));
                tv_invest_list.setTextColor(getResources().getColor(R.color.touzilicai_color));
                tv_regular_list.setTextColor(getResources().getColor(R.color.colorWhite));
//                initView();

                mAdapter = new MyInvestlistAdapter(getActivity(), list, isInvest);
               /* Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.drawable.line_divider);
                rv01.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), drawable2, 40));*/
                rv01.setAdapter(mAdapter);
//                header = new PtrClassicDefaultHeader(getActivity());
//                store_house_ptr_frame.setHeaderView(header);
                list.clear();
                nowPage = 1;
                initData(false, nowPage);
                break;
            case R.id.tv_sp_1:
            case R.id.iv_sp_1:
           /*     if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {
                    sp01.setSelected(true);
                    sp01.performClick();
                }*/

                break;
            case R.id.ll_sp_1:
                //BorrowUseData();

                if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {
//                    sp01.setSelected(true);
//                    sp01.performClick();
                   /* sp03.setSelected(false);
                    sp02.setSelected(false);
                    sp04.setSelected(false);*/
                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    } else {
                        layoutLeft = getActivity().getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistLeft = (ListView) layoutLeft
                                .findViewById(R.id.menulist);
                        menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, list0));

                        menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // textCity.setText(list.get(position));
                                //   et03.setText(borrow_use.get(position).name);
                                if (position == 0) {
                                    tvSp1.setText("类型");
                                } else {
                                    tvSp1.setText(list0.get(position));
                                }


                                select_id = borrowTypeList.get(position).id;
                                // popupWindow.dismiss();
                                list.clear();
                                nowPage = 1;
                                initData(true, nowPage);
                                popLeft.dismiss();
                            }
                        });
                        popLeft = new PopupWindow(layoutLeft, pop_layout.getWidth(),
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorWhite));
//            ColorDrawable cd = new ColorDrawable(-0000);
                        //ColorDrawable cd = new ColorDrawable(0xffff0000);
                        popLeft.setBackgroundDrawable(cd);
                        popLeft.setAnimationStyle(R.style.PopupAnimation);
                        popLeft.update();
                        popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popLeft.setTouchable(true); // 设置popupwindow可点击
                        popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popLeft.setFocusable(true); // 获取焦点

                        popLeft.showAsDropDown(pop_layout, 0, 0);
                        popLeft.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popLeft.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });

                    }

                }

                break;

            case R.id.tv_sp_2:
            case R.id.iv_sp_2:
           /*     if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {

                    sp02.setSelected(true);
                    sp02.performClick();


                }*/
                break;
            case R.id.ll_sp_2:
                if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {

//                    sp02.setSelected(true);
//                    sp02.performClick();

                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    } else {
                        layoutLeft = getActivity().getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistLeft = (ListView) layoutLeft
                                .findViewById(R.id.menulist);
                        menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, list1));

                        menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // textCity.setText(list.get(position));
                                //   et03.setText(borrow_use.get(position).name);


                                if (position == 0) {
                                    tvSp2.setText("收益");
                                } else {
                                    tvSp2.setText(list1.get(position));
                                }


                                // tvSp2.setText(list1.get(position));
                                // select_id = borrowTypeList.get(position).id;borrowInterestRateList
                                shouyi_id = borrowInterestRateList.get(position).id;

                                // popupWindow.dismiss();
                                list.clear();
                                nowPage = 1;
                                initData(true, nowPage);
                                popLeft.dismiss();
                            }
                        });
                        popLeft = new PopupWindow(layoutLeft, pop_layout.getWidth(),
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorWhite));
//            ColorDrawable cd = new ColorDrawable(-0000);
                        //ColorDrawable cd = new ColorDrawable(0xffff0000);
                        popLeft.setBackgroundDrawable(cd);
                        popLeft.setAnimationStyle(R.style.PopupAnimation);
                        popLeft.update();
                        popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popLeft.setTouchable(true); // 设置popupwindow可点击
                        popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popLeft.setFocusable(true); // 获取焦点

                        popLeft.showAsDropDown(pop_layout, 0, 0);
                        popLeft.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popLeft.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });

                    }
                }

                break;

            case R.id.tv_sp_3:
            case R.id.iv_sp_3:
             /*   if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {

                    sp03.setSelected(true);
                    sp03.performClick();

                }*/
                break;
            case R.id.ll_sp_3:
                if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {

                          /*  sp03.setSelected(true);
                            sp03.performClick();*/


                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    } else {
                        layoutLeft = getActivity().getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistLeft = (ListView) layoutLeft
                                .findViewById(R.id.menulist);
                        menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, list2));

                        menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // textCity.setText(list.get(position));
                                //   et03.setText(borrow_use.get(position).name);

                                if (position == 0) {
                                    tvSp3.setText("期限");
                                } else {
                                    //  tvSp1.setText(list0.get(position));
                                    tvSp3.setText(list2.get(position));
                                }

                                // select_id = borrowTypeList.get(position).id;borrowInterestRateList
                                qixian_id = borrowDurationList.get(position).id;

                                // popupWindow.dismiss();
                                list.clear();
                                nowPage = 1;
                                initData(true, nowPage);
                                popLeft.dismiss();
                            }
                        });
                        popLeft = new PopupWindow(layoutLeft, pop_layout.getWidth(),
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorWhite));
//            ColorDrawable cd = new ColorDrawable(-0000);
                        //ColorDrawable cd = new ColorDrawable(0xffff0000);
                        popLeft.setBackgroundDrawable(cd);
                        popLeft.setAnimationStyle(R.style.PopupAnimation);
                        popLeft.update();
                        popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popLeft.setTouchable(true); // 设置popupwindow可点击
                        popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popLeft.setFocusable(true); // 获取焦点

                        popLeft.showAsDropDown(pop_layout, 0, 0);
                        popLeft.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popLeft.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });

                    }


                }
                break;

            case R.id.tv_sp_4:
            case R.id.iv_sp_4:
              /*  if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {
                    sp04.setSelected(true);
                    sp04.performClick();

                }*/

                break;

            case R.id.ll_sp_4:
                if (list0.isEmpty()) {
                    getTypeAccessToken();
                } else {

              /*      sp04.setSelected(true);
                    sp04.performClick();*/

                    if (popLeft != null && popLeft.isShowing()) {
                        popLeft.dismiss();
                    } else {
                        layoutLeft = getActivity().getLayoutInflater().inflate(
                                R.layout.pop_menulist, null);
                        menulistLeft = (ListView) layoutLeft
                                .findViewById(R.id.menulist);
                        menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, list3));

                        menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // textCity.setText(list.get(position));
                                //   et03.setText(borrow_use.get(position).name);
//                                tvSp3.setText(list2.get(position));
                                // select_id = borrowTypeList.get(position).id;borrowInterestRateList
                                if (position == 0) {
                                    tvSp4.setText("状态");
                                } else {
                                    //  tvSp1.setText(list0.get(position));
                                    tvSp4.setText(list3.get(position));
                                }

                                toubiao_id = borrowStatusList.get(position).id;
                                // popupWindow.dismiss();
                                list.clear();
                                nowPage = 1;
                                initData(true, nowPage);
                                popLeft.dismiss();
                            }
                        });
                        popLeft = new PopupWindow(layoutLeft, pop_layout.getWidth(),
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.colorWhite));
//            ColorDrawable cd = new ColorDrawable(-0000);
                        //ColorDrawable cd = new ColorDrawable(0xffff0000);
                        popLeft.setBackgroundDrawable(cd);
                        popLeft.setAnimationStyle(R.style.PopupAnimation);
                        popLeft.update();
                        popLeft.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popLeft.setTouchable(true); // 设置popupwindow可点击
                        popLeft.setOutsideTouchable(true); // 设置popupwindow外部可点击
                        popLeft.setFocusable(true); // 获取焦点

                        popLeft.showAsDropDown(pop_layout, 0, 0);
                        popLeft.setTouchInterceptor(new View.OnTouchListener() {

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // 如果点击了popupwindow的外部，popupwindow也会消失
                                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                    popLeft.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                }


                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
         //   case R.id.sp_01:
            *//*    if (!sp01.isSelected()) {
                    return;
                }
                if (position == 0) {
                    tvSp1.setText("类型");

                } else {
                    tvSp1.setText(list0.get(position));

                }*//*
               *//* if (!sp01.isSelected()) {
                    return;*//*
               *//* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        return;
                    }
                },25000);*//*
                }
              *//*  if (position == 0) {
                    tvSp1.setText("类型");
                } else {
                    tvSp1.setText(list0.get(position));
                }

                break;
            case R.id.sp_02:*//*
             *//*   if (!sp02.isSelected()) {
                    return;
                }
                if (position == 0) {
                    tvSp2.setText("收益");

                } else {
                    tvSp2.setText(list1.get(position));
                }*//*
               *//* if (!sp02.isSelected()) {
                    return;
                }
                if (position == 0) {
                    tvSp2.setText("收益");
                } else {
                    tvSp2.setText(list1.get(position));
                }
                break;*//*
         //   case R.id.sp_03:
              *//*  if (!sp03.isSelected()) {
                    return;
                }
                if (position == 0) {
                    tvSp3.setText("期限");

                } else {
                    tvSp3.setText(list2.get(position));

                }*//*
              *//*  if (!sp03.isSelected()) {
                    return;
                }
                if (position == 0) {
                    tvSp3.setText("期限");
                } else {
                    tvSp3.setText(list2.get(position));
                }
                break;*//*
          //  case R.id.sp_04:

              *//*  if (!sp04.isSelected()) {
                    return;
                }
                if (position == 0) {
                    tvSp4.setText("状态");
                } else {
//                    tvSp4.setText(list4.get(position));
                    tvSp4.setText(list3.get(position));
                }
                break;*//*
        }
   *//*     list.clear();
        nowPage = 1;
        initData(true, nowPage);*//*

    }*/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initRefreshListener() {
        rv01.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                if (dy > 0 && lastVisibleItemPosition + 1 == mAdapter.getItemCount() && canLoadMore) {
                    canLoadMore = false;
                    if (nowPage < totalPages) {
                        initData(true, nowPage + 1);
                    } else {
//                        Toast.show(getActivity(), "当前页已是最后一页");
                        Toast.makeText(getActivity(), "没有更多了~", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        store_house_ptr_frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                int firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition();
                return (firstVisibleItemPosition == 0 || list.size() == 0) && list0.size() != 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
                nowPage = 1;
                initData(false, nowPage);
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

    private void initData(boolean b, final int page) {
        if (llTabLeft.isSelected()) {
            getInvestDataAccessToken(b, page);
        } else {
            getProductDataAccessToken(b, page);
        }
    }


    private void initInvestData(String access_token, final boolean isShow, final int page) {
        Log.d(select_id, "7777777777777777777777777777777777");
        // new JsonInvest(access_token, show_type, String.valueOf(page), borrowTypeList.get(sp01.getSelectedItemPosition()).id, borrowInterestRateList.get(sp02.getSelectedItemPosition()).id, borrowDurationList.get(sp03.getSelectedItemPosition()).id, borrowStatusList.get(sp04.getSelectedItemPosition()).id, new AsyCallBack<JsonInvest.Info>() {
        new JsonInvest(access_token, show_type, String.valueOf(page), select_id, shouyi_id, qixian_id, toubiao_id, new AsyCallBack<JsonInvest.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonInvest.Info info) throws Exception {
                if (info != null) {
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
//                    if (list.isEmpty()) {
//                        UtilToast.show(getActivity(), "无相关记录！");
//                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(getActivity(), JsonInvest.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                canLoadMore = true;
                mAdapter.notifyDataSetChanged();
            }

        }).execute(getActivity(), isShow);
    }

    private void initProductData(String access_token, final boolean isShow, final int page) {
//        new JsonProduct(access_token, String.valueOf(page), borrowTypeList.get(sp01.getSelectedItemPosition()).id, borrowInterestRateList.get(sp02.getSelectedItemPosition()).id, borrowDurationList.get(sp03.getSelectedItemPosition()).id, borrowStatusList.get(sp04.getSelectedItemPosition()).id, new AsyCallBack<JsonProduct.Info>() {
        new JsonProduct(access_token, String.valueOf(page), select_id, shouyi_id, qixian_id, toubiao_id, new AsyCallBack<JsonProduct.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonProduct.Info info) throws Exception {
                if (info != null) {
                    if (nowPage == info.page.nowPage) {
                        list.clear();
                    }
                    list.addAll(info.list);
                    nowPage = info.page.nowPage;
                    totalPages = info.page.totalPages;
                    pageSize = info.page.pageSize;
                    totalRows = info.page.totalRows;
//                    if (list.isEmpty()) {
//                        UtilToast.show(getActivity(), "无相关记录！");
//                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(getActivity(), JsonProduct.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (!isShow) {
                    store_house_ptr_frame.refreshComplete();
                }
                mAdapter.notifyDataSetChanged();
                canLoadMore = true;
            }

        }).execute(getActivity(), isShow);
    }


}
