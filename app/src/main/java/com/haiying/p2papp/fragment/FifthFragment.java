package com.haiying.p2papp.fragment;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.AccountManagementActivity;
import com.haiying.p2papp.activity.ClaimsManagementActivity;
import com.haiying.p2papp.activity.FinancialActivity;
import com.haiying.p2papp.activity.IncreasedInterestActivity;
import com.haiying.p2papp.activity.InvestmentManagementActivity;
import com.haiying.p2papp.activity.LoginActivity;
import com.haiying.p2papp.activity.MoneyManagementActivity;
import com.haiying.p2papp.activity.MyAssetsActivity;
import com.haiying.p2papp.activity.MyRedpacketActivity;
import com.haiying.p2papp.activity.PersonalSettingsActivity;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.activity.SettingsActivity;
import com.haiying.p2papp.activity.ShopActivity;
import com.haiying.p2papp.activity.WithdrawOrRechargeActivity;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonUserinfo;
import com.zcx.helper.activity.AppV4Fragment;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.common.util.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FifthFragment extends AppV4Fragment implements View.OnClickListener {


    //    @Bind(R.id.iv_back)
//    ImageView ivBack;
    @Bind(R.id.iv_camera)
    SimpleDraweeView ivCamera;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
     @Bind(R.id.tv_money)
    TextView tvMoney;
    ////    @Bind(R.id.tv_unit)
//    TextView tvUnit;
    @Bind(R.id.ll_left)
    LinearLayout llLeft;
    @Bind(R.id.ll_right)
    LinearLayout llRight;
    @Bind(R.id.ll_item_01)
    LinearLayout llItem01;
    @Bind(R.id.ll_item_02)
    LinearLayout llItem02;
    @Bind(R.id.ll_item_03)
    LinearLayout llItem03;
    @Bind(R.id.ll_item_04)
    LinearLayout llItem04;
    @Bind(R.id.ll_item_05)
    LinearLayout llItem05;
    @Bind(R.id.ll_item_06)
    LinearLayout llItem06;
    @Bind(R.id.ll_item_07)
    LinearLayout llItem07;
    @Bind(R.id.ll_item_08)
    LinearLayout llItem08;
    //    @Bind(R.id.ll_item_09)
//    LinearLayout llItem09;
//    @Bind(R.id.bt_login)
//    Button btLogin;
    @Bind(R.id.ll_login)
    LinearLayout llLogin;

    //请登录
    @Bind(R.id.tv_login)
    TextView tv_login;

    @Bind(R.id.iv_setting)
    ImageView iv_setting;

    private String nick_name;

    private TextView recommend_code;


    public FifthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) inflater.inflate(R.layout.fragment_fifth, null)));
        ButterKnife.bind(this, view);
        recommend_code = (TextView) view.findViewById(R.id.recommend_code);
if (MyApplication.myPreferences.readUid().isEmpty()){

}else {
    tvMoney.setText(MyApplication.myPreferences.readAll_money());
}
      /*  recommend_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
               *//* ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//将文本数据复制到剪贴板
                cm.setText(getActivity().toString().trim());
                cm.getText();*//*

                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "内容"));
                if (clipboardManager.hasPrimaryClip()) {
                    clipboardManager.getPrimaryClip().getItemAt(0).getText();
                }
                return true;
            }
        });*/


        initLisener();

     /*   recommend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             *//*   TextView tv = new TextView(getActivity());
                tv.setTextIsSelectable(true);*//*
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(getActivity().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
                cmb.getText();//获取粘贴信息
            }
        });*/
        return view;
    }


  /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
            llLogin.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);

        } else {
            llLogin.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
            tvMoney.setText(MyApplication.myPreferences.readAll_money());
            String phone = MyApplication.myPreferences.readUser_phone();

            if (MyApplication.myPreferences.readNickName().isEmpty()) {

                nick_name = MyApplication.myPreferences.readUser_phone();
                tvPhone.setText(nick_name);
            } else {
                nick_name = MyApplication.myPreferences.readNickName();
                tvPhone.setText(nick_name);
            }

            Log.d(MyApplication.myPreferences.readNickName(), "-------------------------------------");

            getAccessToken();
        }
    }*/

    private void initLisener() {
        llLeft.setOnClickListener(this);
//        btLogin.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        llRight.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        llItem01.setOnClickListener(this);
        llItem02.setOnClickListener(this);
        llItem03.setOnClickListener(this);
        llItem04.setOnClickListener(this);
        llItem05.setOnClickListener(this);
        llItem06.setOnClickListener(this);
        llItem07.setOnClickListener(this);
        llItem08.setOnClickListener(this);
//        llItem09.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        recommend_code.setOnClickListener(this);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_login:

                intent.setClass(getActivity(), LoginActivity.class);
                break;
            case R.id.tv_phone:
                //  startVerifyActivity(PersonalSettingsActivity.class);
                // startActivity(new Intent(getActivity(), PersonalSettingsActivity.class));
                intent.putExtra("nicheng", nick_name);
                intent.setClass(getActivity(), PersonalSettingsActivity.class);
                break;
//            case R.id.bt_login:
//                intent.setClass(getActivity(), LoginActivity.class);
//                break;
            case R.id.iv_camera:
                intent.setClass(getActivity(), AccountManagementActivity.class);
                break;
            case R.id.ll_item_01:
                intent.setClass(getActivity(), InvestmentManagementActivity.class);
                break;
            case R.id.ll_item_02:
                intent.setClass(getActivity(), ClaimsManagementActivity.class);
                break;
            case R.id.ll_item_03:
                intent.setClass(getActivity(), MoneyManagementActivity.class);
                break;
            case R.id.ll_item_04:
                intent.setClass(getActivity(), MyAssetsActivity.class);
                break;
            case R.id.ll_item_05:
                intent.setClass(getActivity(), ShopActivity.class);
                break;
            case R.id.ll_item_06:
                intent.setClass(getActivity(), FinancialActivity.class);
                break;
            case R.id.ll_item_07:
                intent.setClass(getActivity(), MyRedpacketActivity.class);
                break;
            case R.id.ll_item_08:
                intent.setClass(getActivity(), IncreasedInterestActivity.class);
                break;
            case R.id.ll_left:
                intent.putExtra("mode", false);//false为充值
                intent.setClass(getActivity(), WithdrawOrRechargeActivity.class);
                break;
            case R.id.ll_right:
                intent.putExtra("mode", true);//true为提现
                intent.setClass(getActivity(), WithdrawOrRechargeActivity.class);
                break;
            case R.id.iv_setting:
                intent.setClass(getActivity(), SettingsActivity.class);
                break;


        }
        if (v.getId() != R.id.tv_login && TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
            ToastUtils.show(getActivity(), "请登录后再操作！");
            return;
        }else if (v.getId()==R.id.recommend_code){
            ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(getActivity().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
            cmb.getText();//获取粘贴信息
        }else {
            startActivity(intent);
        }




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(MyApplication.myPreferences.readUid())) {
            llLogin.setVisibility(View.GONE);
            tv_login.setVisibility(View.VISIBLE);

        } else {
            llLogin.setVisibility(View.VISIBLE);
            tv_login.setVisibility(View.GONE);
            tvMoney.setText(MyApplication.myPreferences.readAll_money());
            String phone = MyApplication.myPreferences.readUser_phone();
     /*    if (!TextUtils.isEmpty(phone) && UtilMatches.checkMobile(phone)) {
                tvPhone.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
            }*/
            if (MyApplication.myPreferences.readNickName().isEmpty()) {
//                tvPhone.setText(MyApplication.myPreferences.readUser_phone());
                nick_name = MyApplication.myPreferences.readUser_phone();
                tvPhone.setText(nick_name);
            } else {
                nick_name = MyApplication.myPreferences.readNickName();
                tvPhone.setText(nick_name);
            }

            //  tvPhone.setText(MyApplication.myPreferences.readNickName());
            Log.d(MyApplication.myPreferences.readNickName(), "-------------------------------------");

            getAccessToken();
        }
    }

    private void getAccessToken() {
        new JsonAccessToken("user/userinfo", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        onGetUserInfo(info.access_token);
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

    private void onGetUserInfo(String access_token) {
        new JsonUserinfo(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonUserinfo.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonUserinfo.Info info) throws Exception {
                if (info != null) {

                    MyApplication.myPreferences.saveUid(info.id);

                    MyApplication.myPreferences.saveUserName(info.user_name);

                    MyApplication.myPreferences.saveUser_email(info.user_email);

                    MyApplication.myPreferences.saveUser_phone(info.user_phone);

                    MyApplication.myPreferences.saveSex(info.sex);

                    MyApplication.myPreferences.saveReal_name(info.real_name);

                    MyApplication.myPreferences.saveIdcard(info.idcard);

                    MyApplication.myPreferences.saveMoney_freeze(info.money_freeze);

                    MyApplication.myPreferences.saveMoney_collect(info.money_collect);

                    MyApplication.myPreferences.saveAccount_money(info.account_money);

                    MyApplication.myPreferences.saveMoney_experience(info.money_experience);

                    MyApplication.myPreferences.saveAll_money(info.all_money);

                    MyApplication.myPreferences.saveUserpic(info.userpic);

                    MyApplication.myPreferences.saveId_status(info.id_status);

                    MyApplication.myPreferences.saveVip_status(info.vip_status);

                    MyApplication.myPreferences.savePhone_status(info.phone_status);

                    MyApplication.myPreferences.saveEmail_status(info.email_status);

                    MyApplication.myPreferences.savePin_pass(info.pin_pass);
                    MyApplication.myPreferences.saveNickName(info.nick_name);
                    Log.d(info.nick_name, "+++++++++++++++++++++++++++++++");
                    recommend_code.setText("我的推荐码： " + info.incode);
//                    tvMoney.setText(MyApplication.myPreferences.readAll_money());

                    ivCamera.setImageURI(Uri.parse(MyApplication.myPreferences.readUserpic()));


                    String phone = MyApplication.myPreferences.readUser_phone();
//                    if (!TextUtils.isEmpty(phone)&&UtilMatches.checkMobile(phone)) {
//                        tvPhone.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
//                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                UtilToast.show(getActivity(), JsonUserinfo.TOAST);
            }
        }).execute(getActivity(), false);

    }
}
