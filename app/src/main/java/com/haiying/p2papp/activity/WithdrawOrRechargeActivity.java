package com.haiying.p2papp.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.BankListAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBanklist;
import com.haiying.p2papp.conn.JsonWithdraw;
import com.haiying.p2papp.conn.JsonWithdrawvalidate;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;

public class WithdrawOrRechargeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private TextView tvtitleright;
    private RecyclerView rv01;
    private LinearLayout lladd;
    private TextView tv01;
    private TextView tv02;
    private ImageView iv01;
    private ImageView iv02;
    private TextView tv03;
    private EditText et01;
    private EditText et02;
    private LinearLayout ll01;
    private Button bt01;

    private boolean Mode = true;//默认提现模式
    private TextView tvtitle;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<JsonBanklist.Info.ListContent> list = new ArrayList<>();
    private SimpleDraweeView ivlogo;
    private TextView tvname;
    private TextView tvid;
    private ImageView ivsettings01;
    private LinearLayout llbank;
    private String bankId, msg;
    private PopupWindow popWindow;
    private WebView wv01;

    private View view_chongzhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_or_recharge);

        initView();
        initMode();
        initListener();
        getBankListAccessToken();
    }

    private void getBankListAccessToken() {
        new JsonAccessToken("user/banklist", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initBankListData(info.access_token);
                    } else {
                        UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void initBankListData(String access_token) {
        new JsonBanklist(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonBanklist.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonBanklist.Info info) throws Exception {
                if (info != null) {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    list.addAll(info.list);
                    if (list.isEmpty()) {
                        llbank.setSelected(false);
                        if (!Mode) {
                            rv01.setVisibility(View.GONE);
                        }
                        UtilToast.show(WithdrawOrRechargeActivity.this, "您尚未绑定银行卡！");
                    } else {
                        if (Mode) {
                            llbank.setVisibility(View.VISIBLE);
                            tvname.setText(list.get(0).bank_name);
                            String string = list.get(0).bank_num;

                            String str2 = string.substring(string.length() - 4, string.length());
//            tvid.setText(data.getStringExtra("num"));
                            // tvid.setText("("+" 尾号 "+ str2+")");

                            tvid.setText("(" + " 尾号 " + str2 + ")");
                            //                            tvid.setText(list.get(0).bank_num);

                            ivlogo.setImageURI(Uri.parse(list.get(0).bank_ico));
                            bankId = list.get(0).id;
                            llbank.setSelected(true);
                        }

                    }

                } else {
                    UtilToast.show(WithdrawOrRechargeActivity.this, JsonBanklist.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                if (!Mode) {
                    mAdapter.notifyDataSetChanged();
                }

            }

        }).execute(WithdrawOrRechargeActivity.this, true);
    }

    private void initMode() {
        Mode = getIntent().getBooleanExtra("mode", true);
        if (!Mode) {
            rv01.setVisibility(View.GONE);
            tvtitle.setText(getResources().getText(R.string.wor_title_02));
            tvtitleright.setText(getResources().getText(R.string.wor_title_right_02));
            tv01.setText(getResources().getText(R.string.wor_01));
            tv03.setText(getResources().getText(R.string.wor_02));
            bt01.setText(getResources().getText(R.string.wor_05));
            et01.setHint(getResources().getText(R.string.wor_02_hint));
            ll01.setVisibility(View.GONE);
            iv01.setVisibility(View.GONE);
            iv02.setVisibility(View.GONE);
            view_chongzhi.setVisibility(View.VISIBLE);
        } else {
            tv03.setText("提现金额");
            view_chongzhi.setVisibility(View.GONE);
        }
    }

    private void initView() {
        this.wv01 = (WebView) findViewById(R.id.wv_01);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.iv02 = (ImageView) findViewById(R.id.iv_02);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.lladd = (LinearLayout) findViewById(R.id.ll_add);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.llbank = (LinearLayout) findViewById(R.id.ll_bank);
        this.ivsettings01 = (ImageView) findViewById(R.id.iv_settings_01);
        this.tvid = (TextView) findViewById(R.id.tv_id);
        this.tvname = (TextView) findViewById(R.id.tv_name);
        this.ivlogo = (SimpleDraweeView) findViewById(R.id.iv_logo);
        this.tvtitleright = (TextView) findViewById(R.id.tv_title_right);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);

        this.view_chongzhi = findViewById(R.id.view_chongzhi);

        wv01.getSettings().setJavaScriptEnabled(true);
        wv01.getSettings().setBuiltInZoomControls(false);
        wv01.getSettings().setUseWideViewPort(true);
        wv01.getSettings().setDisplayZoomControls(false);
        wv01.getSettings().setLoadWithOverviewMode(true);
        wv01.getSettings().setUseWideViewPort(true);
        wv01.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebChromeClient chromeClient = new WebChromeClient();
        wv01.setWebChromeClient(chromeClient);
        wv01.setWebViewClient(new WebViewClient());
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new BankListAdapter(this, list, 0);
        rv01.setAdapter(mAdapter);
        tv02.setText(MyApplication.myPreferences.readAccount_money() + "元");
    }

    private void initListener() {
        llbank.setOnClickListener(this);
        lladd.setOnClickListener(this);
        tvtitleright.setOnClickListener(this);
        bt01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_bank:

                intent.putExtra("mode", 0);
                intent.setClass(this, ChoseBankActivity.class);
                startActivityForResult(intent, 0);
             /*   intent.putExtra("mode", "1");
                intent.setClass(this, ChoseBankActivity.class);
                startActivityForResult(intent, 1);*/
                break;
            case R.id.ll_add:
                if (MyApplication.myPreferences.readId_status().equals("0")) {
                    ToastUtils.show(this, "请先通过实名认证，实名认证通过后方可绑定银行卡。");
                } else if (MyApplication.myPreferences.readId_status().equals("2")) {
                    ToastUtils.show(this, "实名认证审核中，实名认证通过后方可绑定银行卡。");
                } else if (MyApplication.myPreferences.readId_status().equals("1")) {
                    intent.setClass(this, BankcardAddActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_title_right:

                intent.putExtra("mode", Mode);//true为提现，false为充值
                intent.setClass(this, WithdrawOrRechargeRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_01:
                if (Mode) {
                    if (TextUtils.isEmpty(bankId)) {
                        ToastUtils.show(this, "请选择提现银行！");
                    } else if (TextUtils.isEmpty(et01.getText().toString())) {
                        ToastUtils.show(this, "请输入提现金额！");
                    } else if (et01.getText().toString().equals(".")) {
                        ToastUtils.show(this, "请输入提现金额！");
                    } else if ((et01.getText().toString().contains(".") && Double.parseDouble(et01.getText().toString()) < 100) || (!et01.getText().toString().contains(".") && Long.parseLong(et01.getText().toString()) < 100)) {
                        ToastUtils.show(this, "请输入大于100元的金额！");
                    } else if (TextUtils.isEmpty(et02.getText().toString())) {
                        ToastUtils.show(this, "请输入支付密码！");
                    } else {
                        getWithdrawvalidateAccessToken();
                    }
                } else {
                    if (TextUtils.isEmpty(et01.getText().toString())) {
                        ToastUtils.show(this, "请输入充值金额！");
                    } else if (et01.getText().toString().equals(".")) {
                        ToastUtils.show(this, "请输入充值金额！");
                    } else if ((et01.getText().toString().contains(".") && Double.parseDouble(et01.getText().toString()) < 100) || (!et01.getText().toString().contains(".") && Long.parseLong(et01.getText().toString()) < 100)) {
                        ToastUtils.show(this, "请输入大于100元的金额！");
                    } else {




                     //   getRechargeAccessToken(money);
                        showPopupWindow(v);
                    }
                }
                break;
        }

    }




    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                    .inflate(R.layout.dialog_03, null)));
            //LayoutParams相当于一个Layout的信息包，它封装了Layout的位置、高、宽等信息。
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initPop(view);
        }
        //设置动画效果
        popWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        //获取popwindow焦点
        popWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //实现软键盘不自动弹出,ADJUST_RESIZE属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置popwindow显示位置
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }


    public void initPop(View view) {
        TextView tv01 = (TextView) view.findViewById(R.id.dl_tv_01);//拍照
        TextView tv02 = (TextView) view.findViewById(R.id.dl_tv_02);//相册

        TextView text_moneycount = (TextView) view.findViewById(R.id.text_moneycount);
        text_moneycount.setText(et01.getText().toString());

        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              /*  MyApplication.myPreferences.clear();
                onBackPressed();
                popWindow.dismiss();*/
                String money = "";
                if (et01.getText().toString().contains(".")) {
                    money = doubleto2(Double.parseDouble(et01.getText().toString()));
                } else {
                    money = et01.getText().toString();
                }
                getRechargeAccessToken(money);
                popWindow.dismiss();
            }
        });
    }

    private void getRechargeAccessToken(final String money) {
        new JsonAccessToken("pay/charge", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        // String url = "https://www.haiying365.com/api/pay/charge?uid=" + MyApplication.myPreferences.readUid() + "&money=" + money + "&way=ecpss2" + "&access_token=" + info.access_token;
                        String url = " http://vhost119.zihaistar.com/api/pay/charge?uid=" + MyApplication.myPreferences.readUid() + "&money=" + money + "&way=ecpss2" + "&access_token=" + info.access_token;

                        wv01.setVisibility(View.VISIBLE);
                        wv01.loadUrl(url);
                    } else {
                        UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void getWithdrawvalidateAccessToken() {
        new JsonAccessToken("user/withdraw_validate", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initWithdrawvalidate(info.access_token);
                    } else {
                        UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initWithdrawvalidate(String access_token) {
        new JsonWithdrawvalidate(access_token, MyApplication.myPreferences.readUid(), et01.getText().toString(), et02.getText().toString(), bankId, new AsyCallBack<JsonWithdrawvalidate.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonWithdrawvalidate.Info info) throws Exception {
                if (info != null) {
                    msg = info.tips;
                    bt01.setSelected(true);
                    if (!TextUtils.isEmpty(msg)) {
                        showPopupWindow1(bt01);
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                bt01.setSelected(false);
                msg = JsonWithdrawvalidate.TOAST;
                if (!TextUtils.isEmpty(msg)) {
                    showPopupWindow1(bt01);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                super.onEnd(toast, type);
            }
        }).execute(this, true);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            tvname.setText(data.getStringExtra("name"));
            ivlogo.setImageURI(Uri.parse(data.getStringExtra("ico")));
            String string = data.getStringExtra("num");
            // string.subString(string.length-3,string.length);
            String str2 = string.substring(string.length() - 4, string.length());

//            tvid.setText(data.getStringExtra("num"));

            tvid.setText("(" + " 尾号 " + str2 + ")");
            bankId = data.getStringExtra("id");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!(!list.isEmpty() && Mode)) {
            getBankListAccessToken();
        }
    }

    private void showPopupWindow1(View parent) {
        View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.dialog_02, null)));
        //LayoutParams相当于一个Layout的信息包，它封装了Layout的位置、高、宽等信息。
        popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        initPop1(view);
        //设置动画效果
        popWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        //获取popwindow焦点
        popWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //实现软键盘不自动弹出,ADJUST_RESIZE属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置popwindow显示位置
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void initPop1(View view) {
        TextView tv01 = (TextView) view.findViewById(R.id.dl_tv_01);//拍照
        TextView tv02 = (TextView) view.findViewById(R.id.dl_tv_02);//相册
        TextView tvmsg = (TextView) view.findViewById(R.id.tv_msg);
        tvmsg.setText(msg);
        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (bt01.isSelected()) {
                    getWithdrawAccessToken();
                }
                popWindow.dismiss();
            }
        });
    }


    private void getWithdrawAccessToken() {
        new JsonAccessToken("user/withdraw", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initWithdraw(info.access_token);
                    } else {
                        UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(WithdrawOrRechargeActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void initWithdraw(String access_token) {

        new JsonWithdraw(access_token, MyApplication.myPreferences.readUid(), et01.getText().toString(), et02.getText().toString(), bankId, new AsyCallBack<JsonWithdraw.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonWithdraw.Info info) throws Exception {
                if (info != null) {
                    msg = info.tips;
                    tv02.setText(info.account_money + "元");
                    MyApplication.myPreferences.saveAccount_money(info.account_money);
                    bt01.setSelected(false);
                    if (!TextUtils.isEmpty(msg)) {
                        showPopupWindow1(bt01);
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                bt01.setSelected(false);
                msg = JsonWithdraw.TOAST;
                if (!TextUtils.isEmpty(msg)) {
                    showPopupWindow1(bt01);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                super.onEnd(toast, type);
            }
        }).execute(this, true);

    }

    // 保留小数点后2位
    public static String doubleto2(double d) {
        DecimalFormat df2 = new DecimalFormat("0.00");
        String s = df2.format(d);
        return s;
    }
}
