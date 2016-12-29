package com.haiying.p2papp.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.IoanActivity;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBorrowindex;
import com.zcx.helper.activity.AppV4Fragment;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.common.util.ToastUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourthFragment extends AppV4Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    @Bind(R.id.iv_cb_01)
    ImageView ivCb01;
    /* @Bind(R.id.iv_cb_02)
     ImageView ivCb02;*/
    @Bind(R.id.bt_next)
    Button btNext;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_01)
    TextView et01;
    /*@Bind(R.id.et_02)
    EditText et02;*/
    @Bind(R.id.et_03)
    TextView et03;
    /*   @Bind(R.id.sp_01)
       Spinner sp01;*/
    /*@Bind(R.id.iv_01)
    ImageView iv01;*/
    @Bind(R.id.et_04)
    TextView et04;
    /*  @Bind(R.id.sp_02)
      Spinner sp02;
    *//*  @Bind(R.id.sp_03)
    Spinner sp03;*//*
    @Bind(R.id.sp_04)
    Spinner sp04;*/
    @Bind(R.id.iv_02)
    ImageView iv02;

    @Bind(R.id.text_user)
    EditText text_user;
    @Bind(R.id.text_sex)
    TextView text_sex;

    @Bind(R.id.text_telephone)
    EditText text_telephone;

    @Bind(R.id.sex_layout)
    LinearLayout sex_layout;

    @Bind(R.id.borrow_money_layout)
    LinearLayout borrow_money_layout;

    @Bind(R.id.borrow_money_use_layout)
    LinearLayout borrow_money_use_layout;

    @Bind(R.id.qixian_layout)
    LinearLayout qixian_layout;

    private PopupWindow popLeft;
    private View layoutLeft;

    private ListView menulistLeft;
    private List<JsonBorrowindex.Info.ListContent> borrow_use = new ArrayList<>();


    private List<String> stringListBorrow_use = new ArrayList<>();


    private List<JsonBorrowindex.Info.ListContent> borrow_money = new ArrayList<>();
    private List<String> myborrow_money = new ArrayList<>();


    private List<JsonBorrowindex.Info.ListContent> sex = new ArrayList<>();
    private List<String> mysex = new ArrayList<>();

    private List<JsonBorrowindex.Info.ListContent> borrow_duration = new ArrayList<>();
    private List<String> myborrow_duration = new ArrayList<>();

    List<String> data = new ArrayList<String>();

    private String myet03;
    private String myet04;

    private String mysex_1;


    private String myet_01;

    public FourthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) inflater.inflate(R.layout.fragment_fourth, null)));
        ButterKnife.bind(this, view);
        initView();
        initListener();
        text_sex.setText("");
        et01.setText("");
        et03.setText("");
        et04.setText("");
        getTypeAccessToken();
        return view;
    }

    private void initView() {

    }

    private void initListener() {
        ivCb01.setOnClickListener(this);

        btNext.setOnClickListener(this);
//        sp01.setOnItemSelectedListener(this);
//        sp02.setOnItemSelectedListener(this);


        et03.setOnClickListener(this);
        et04.setOnClickListener(this);

        iv02.setOnClickListener(this);


        btNext.setOnClickListener(this);

        sex_layout.setOnClickListener(this);
        et01.setOnClickListener(this);

        borrow_money_layout.setOnClickListener(this);

        borrow_money_use_layout.setOnClickListener(this);
        qixian_layout.setOnClickListener(this);


    }


    @Override
    public void onStart() {
        super.onStart();
        text_user.setText(MyApplication.myPreferences.readUserName());
       /* text_user.setText(MyApplication.myPreferences.readUserName());
        if (MyApplication.myPreferences.readSex().equals("0")){
            text_sex.setText("男");
        }else{
            text_sex.setText("女");
        }*/

        //  text_sex.setText(MyApplication.myPreferences.readSex());
        text_telephone.setText(MyApplication.myPreferences.readUser_phone());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
         /*   case R.id.iv_cb_01:
                ivCb01.setSelected(!ivCb01.isSelected());
                sp02.setSelected(false);
                if (ivCb01.isSelected()) {
                    sp02.setAdapter(new CmSpinnerAdapter(myborrow_money));
                } else {
                    sp02.setAdapter(new CmSpinnerAdapter(myborrow_money));
                }
                et04.setText("");
                break;*/

            case R.id.sex_layout:
/*
                if (!sp03.isSelected() && !myborrow_money.isEmpty()) {
                    if (ivCb01.isSelected()) {
                        text_sex.setText(mysex.get(0));
                    } else {
                        text_sex.setText(mysex.get(0));
                    }
                }
                sp03.setSelected(true);
                sp03.performClick();*/

                getSexData();

                break;

            case R.id.borrow_money_layout:
                BorrowData();

                break;
            case R.id.et_01:
              /*  if (!sp04.isSelected() && !myborrow_duration.isEmpty()) {
                    if (ivCb01.isSelected()) {
                        et01.setText(myborrow_duration.get(0));
                    } else {
                        et01.setText(myborrow_duration.get(0));
                    }

                }
                sp04.setSelected(true);
                sp04.performClick();*/
                BorrowData();
                break;


            case R.id.borrow_money_use_layout:
                BorrowUseData();

                break;

            case R.id.et_03:
               /* sp01.setVisibility(View.VISIBLE);

                if (!sp01.isSelected() && !stringListBorrow_use.isEmpty()) {
                    et03.setText(stringListBorrow_use.get(0));
                }
                sp01.setSelected(true);
                sp01.performClick();
                sp01.setVisibility(View.GONE);*/
                BorrowUseData();
                break;


            case R.id.qixian_layout:
                BorrowMoneyQixianData();
                break;
            case R.id.et_04:
               /* if (!sp02.isSelected() && !myborrow_money.isEmpty()) {
                    if (ivCb01.isSelected()) {
                        et04.setText(myborrow_money.get(0));
                    } else {
                        et04.setText(myborrow_money.get(0));
                    }

                }
                sp02.setSelected(true);
                sp02.performClick();*/
                BorrowMoneyQixianData();
                break;
            case R.id.iv_01:

            case R.id.iv_02:
         /*   case R.id.et_04:
                if (!sp02.isSelected() && !myborrow_money.isEmpty()) {
                    if (ivCb01.isSelected()) {
                        et04.setText(myborrow_money.get(0));
                    } else {
                        et04.setText(myborrow_money.get(0));
                    }

                }
                sp02.setSelected(true);
                sp02.performClick();
                break;*/


            case R.id.et_05:

                break;
            case R.id.iv_04:

            case R.id.iv_05:

            case R.id.bt_next:
                if (TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
                    ToastUtils.show(getActivity(), "请先登录");
                } else if (TextUtils.isEmpty(et01.getText().toString())) {
                    ToastUtils.show(getActivity(), "请输入借款总金额");
                } else if (TextUtils.isEmpty(et03.getText().toString())) {
                    ToastUtils.show(getActivity(), "请选择借款用途");
                } else if (TextUtils.isEmpty(et04.getText().toString())) {
                    ToastUtils.show(getActivity(), "请选择贷款期限");
                } else if (text_sex.getText().toString().isEmpty()) {
                    ToastUtils.show(getActivity(), "请输入性别");
                }else if (text_user.getText().toString().isEmpty()){
                    ToastUtils.show(getActivity(), "用户名不能为空");
                }else if (text_telephone.getText().toString().isEmpty()){
                    ToastUtils.show(getActivity(), "用户联系方式不能为空");
                }

                else {
                    Intent intent = new Intent();

                    //intent.putExtra("borrow_use", borrow_use.get(sp01.getSelectedItemPosition()).id);

                    String sex_Str = text_sex.getText().toString();
                    if (sex_Str.equals("男")) {
                        sex_Str = "1";
                    } else {
                        sex_Str = "2";
                    }
                    intent.putExtra("username", text_user.getText().toString());
                    intent.putExtra("userphone",text_telephone.getText().toString());

                    intent.putExtra("sex", mysex_1);

                    intent.putExtra("borrow_money", et01.getText().toString());
                    intent.putExtra("borrow_use", myet03);

                    intent.putExtra("borrow_use_limit", myet04);
                    intent.setClass(getActivity(), IoanActivity.class);
                  //  MyApplication.myPreferences.clear();


                    startActivity(intent);
                    et01.setText("");
                    et03.setText("");
                    et04.setText("");
                }
                break;
        }
    }

    private void getTypeAccessToken() {
        new JsonAccessToken("index/feedback", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        onTypeInfo(info.access_token);
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


    private void onTypeInfo(String access_token) {


        new JsonBorrowindex(access_token, new AsyCallBack<JsonBorrowindex.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonBorrowindex.Info info) throws Exception {
                // Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT).show();

                if (info != null) {

                    borrow_use = info.borrow_use;
                    stringListBorrow_use.clear();
                    for (int i = 0; i < borrow_use.size(); i++) {
                        stringListBorrow_use.add(borrow_use.get(i).name);
                    }

                    borrow_money = info.borrow_money;

                    myborrow_money.clear();
                    for (int i = 0; i < borrow_money.size(); i++) {
                        myborrow_money.add(borrow_money.get(i).name);
                    }


                    sex = info.sex;
                    mysex.clear();

                    for (int i = 0; i < sex.size(); i++) {
                        mysex.add(sex.get(i).name);
                    }

                    borrow_duration = info.borrow_duration;
                    myborrow_duration.clear();

                    for (int i = 0; i < borrow_duration.size(); i++) {
                        myborrow_duration.add(borrow_duration.get(i).name);
                    }

                    MyApplication.myPreferences.saveEt01(et01.getText().toString());

                    MyApplication.myPreferences.saveEt03(et03.getText().toString());
                    MyApplication.myPreferences.saveEt04(et04.getText().toString());
//                    sp01.setAdapter(new CmSpinnerAdapter(stringListBorrow_use));
//                    sp02.setAdapter(new CmSpinnerAdapter(myborrow_money));
//                    // sp03.setAdapter(new CmSpinnerAdapter(mysex));
//                    sp03.setAdapter(new CmSpinnerAdapter(myborrow_duration));
                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                UtilToast.show(getActivity(), JsonBorrowindex.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(getActivity(), false);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
       /*     case R.id.sp_01:
                if (!sp01.isSelected()) {
                    return;
                }
                et03.setText(stringListBorrow_use.get(position));
                break;
            case R.id.sp_02:
                if (!sp02.isSelected()) {
                    return;
                }
                if (ivCb01.isSelected()) {
                    et04.setText(myborrow_money.get(position));
                } else {
                    et04.setText(myborrow_money.get(position));
                }

                break;
            case R.id.sp_03:
                if (!sp03.isSelected()) {
                    return;
                }
                text_sex.setText(mysex.get(position));
                break;
            case R.id.sp_04:
                if (!sp04.isSelected()) {
                    return;
                }
                if (!sp01.isSelected()) {
                    return;
                }
                et01.setText(myborrow_duration.get(position));
                break;*/

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.canClean) {
//            et01.setText("");
//            et03.setText("");
//            et04.setText("");
            //   et05.setText("");
            //et06.setText("");
            //et07.setText("");
            // et08.setText("");
            //    et09.setText("");
            //  et10.setText("");


        } else {
            MyApplication.canClean = false;
        }

    }


    public void getSexData() {
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft = getActivity().getLayoutInflater().inflate(
                    R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);
            menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, mysex));

            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // textCity.setText(list.get(position));
                    text_sex.setText(sex.get(position).name);
                    mysex_1 = sex.get(position).id;
                    // popupWindow.dismiss();
                    popLeft.dismiss();
                }
            });
            popLeft = new PopupWindow(layoutLeft, sex_layout.getWidth(),
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

            popLeft.showAsDropDown(sex_layout, 0, 0);
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

    public void BorrowData() {
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft = getActivity().getLayoutInflater().inflate(
                    R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);
            menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, myborrow_money));

            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // textCity.setText(list.get(position));
                    //et01.setText(borrow_money.get(position).name);
                    et01.setText(borrow_money.get(position).id);
                    myet_01 = borrow_money.get(position).id;
                    // popupWindow.dismiss();
                    popLeft.dismiss();
                }
            });
            popLeft = new PopupWindow(layoutLeft, borrow_money_layout.getWidth(),
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

            popLeft.showAsDropDown(borrow_money_layout, 0, 0);
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


    public void BorrowUseData() {
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft = getActivity().getLayoutInflater().inflate(
                    R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);
            menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, stringListBorrow_use));

            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // textCity.setText(list.get(position));
                    //  et03.setText(borrow_use.get(position).name);
                    et03.setText(borrow_use.get(position).name);
                    myet03 = borrow_use.get(position).id;
                    // popupWindow.dismiss();
                    popLeft.dismiss();
                }
            });
            popLeft = new PopupWindow(layoutLeft, borrow_money_layout.getWidth(),
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

            popLeft.showAsDropDown(borrow_money_use_layout, 0, 0);
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


    public void BorrowMoneyQixianData() {
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft = getActivity().getLayoutInflater().inflate(
                    R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);
            menulistLeft.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.spinner_list_item, myborrow_duration));

            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // textCity.setText(list.get(position));
                    et04.setText(borrow_duration.get(position).name);
                    myet04 = borrow_duration.get(position).id;
                    // popupWindow.dismiss();
                    popLeft.dismiss();
                }
            });
            popLeft = new PopupWindow(layoutLeft, borrow_money_layout.getWidth(),
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

            popLeft.showAsDropDown(qixian_layout, 0, 0);
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

}
