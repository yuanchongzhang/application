package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.PaySpinnerAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonInvestpay;
import com.haiying.p2papp.conn.JsonInvestpaydo;
import com.haiying.p2papp.conn.JsonProductpay;
import com.haiying.p2papp.conn.JsonProductpaydo;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;

public class PayActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView ivback;
    private TextView tv01;
    private TextView tv02;
    private ImageView iv01;
    private ImageView iv02;
    private EditText et01;
    private ImageView ivcb01;
    private TextView tv03;
    private ImageView ivcb02;
    private TextView tv04;
    private Button bt01;
    private Spinner sp01;
    private Spinner sp02;
    private String has_pin;
    private EditText et02;
    private List<JsonInvestpay.Info.BonusListContent> list = new ArrayList<>();
    private List<JsonInvestpay.Info.BonusListContent> list1 = new ArrayList<>();
    private boolean MODE = true;//默认投资，false为理财
    private LinearLayout llpass;
    private LinearLayout ll01;
    private LinearLayout ll02;
    private ImageView iv03;
    private ImageView iv04;
    private ImageView ivcb03;
    private TextView tv05;
    private LinearLayout ll03;
    private TextView tv08;
    private double lM = 0.00;
    private double HM = 0.00;

    View view_last;

    private LinearLayout money_layout;

    private LinearLayout daishou_layout;
    View lines1;
    View lines2;
    View lines3;
    View lines4;
    View lines5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        this.tv08 = (TextView) findViewById(R.id.tv_08);
        this.ll03 = (LinearLayout) findViewById(R.id.ll_03);
        this.tv05 = (TextView) findViewById(R.id.tv_05);
        this.ivcb03 = (ImageView) findViewById(R.id.iv_cb_03);
        view_last=findViewById(R.id.view_last);
        this.iv04 = (ImageView) findViewById(R.id.iv_04);
        this.iv03 = (ImageView) findViewById(R.id.iv_03);
        this.ll02 = (LinearLayout) findViewById(R.id.ll_02);
        this.ll01 = (LinearLayout) findViewById(R.id.ll_01);
        this.llpass = (LinearLayout) findViewById(R.id.ll_pass);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.sp02 = (Spinner) findViewById(R.id.sp_02);
        this.sp01 = (Spinner) findViewById(R.id.sp_01);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.tv04 = (TextView) findViewById(R.id.tv_04);
        this.ivcb02 = (ImageView) findViewById(R.id.iv_cb_02);
        this.tv03 = (TextView) findViewById(R.id.tv_03);
        this.ivcb01 = (ImageView) findViewById(R.id.iv_cb_01);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.iv02 = (ImageView) findViewById(R.id.iv_02);
        this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.tv02 = (TextView) findViewById(R.id.tv_02);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        ivcb01.setOnClickListener(this);
        ivcb02.setOnClickListener(this);
        ivcb03.setOnClickListener(this);
        tv03.setOnClickListener(this);
        tv04.setOnClickListener(this);
        bt01.setOnClickListener(this);
        sp01.setOnItemSelectedListener(this);
        sp02.setOnItemSelectedListener(this);
        money_layout= (LinearLayout) findViewById(R.id.money_layout);
        daishou_layout= (LinearLayout) findViewById(R.id.daishou_layout);
        lines1=findViewById(R.id.lines1);
        lines2=findViewById(R.id.lines2);
        lines3=findViewById(R.id.lines3);
        lines4=findViewById(R.id.lines4);


        MODE = getIntent().getBooleanExtra("mode", true);
        if (MODE) {
            getAccessToken1();
            llpass.setVisibility(View.GONE);
            iv03.setVisibility(View.GONE);
            iv04.setVisibility(View.GONE);

           // money_layout.setVisibility(View.GONE);
         //   daishou_layout.setVisibility(View.GONE);
//            ll01.setVisibility(View.GONE);
//            ll02.setVisibility(View.GONE);
//            ll03.setVisibility(View.GONE);
//            view_last.setVisibility(View.GONE);
//            lines1.setVisibility(View.GONE);
//            lines2.setVisibility(View.GONE);
//            lines3.setVisibility(View.GONE);
//            lines4.setVisibility(View.GONE);

        } else {
            getAccessToken2();
            ll01.setVisibility(View.GONE);
            ll02.setVisibility(View.GONE);
            ll03.setVisibility(View.GONE);
            tv08.setText("支付密码");
            et02.setHint("请输入支付密码");
            view_last.setVisibility(View.GONE);

        }
        tv01.setText(MyApplication.myPreferences.readAccount_money() + "元");
    }
    private void getAccessToken2() {
        new JsonAccessToken("index/product_pay", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData2(info.access_token);
                    } else {
                        UtilToast.show( PayActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( PayActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }


    private void initData2(String access_token) {
        new JsonProductpay(access_token,getIntent().getStringExtra("id"), MyApplication.myPreferences.readUid(), new AsyCallBack<JsonProductpay.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonProductpay.Info info) throws Exception {
                if (info != null) {
                    tv01.setText(info.account_money);
                    tv02.setText(info.invest_range);
                    if (info.invest_range.contains("无限")) {
                        String[] m = info.invest_range.replace("元", "").split("-");
                        lM = Double.parseDouble(m[0]);
                        HM = 0.00;
                    } else {
                        String[] m = info.invest_range.replace("元", "").split("-");
                        lM = Double.parseDouble(m[0]);
                        HM = Double.parseDouble(m[1]);
                    }

                    tv02.setSelected(true);
                    bt01.setSelected(true);
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                UtilToast.show(PayActivity.this, JsonProductpay.TOAST);
                bt01.setSelected(false);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(PayActivity.this, true);
    }
    private void getAccessToken1() {
//        new JsonAccessToken("index/invest_pay", new AsyCallBack<JsonAccessToken.Info>() {
        new JsonAccessToken("index/invest_pay", new AsyCallBack<JsonAccessToken.Info>() {

            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData1(info.access_token);
                    } else {
                        UtilToast.show( PayActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( PayActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }
    private void initData1(String access_token) {
        new JsonInvestpay(access_token,getIntent().getStringExtra("id"), MyApplication.myPreferences.readUid(), new AsyCallBack<JsonInvestpay.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonInvestpay.Info info) throws Exception {
                if (info != null) {
                    tv01.setText(info.account_money + "元");
                    tv05.setText(info.money_experience + "元");
                    tv02.setText(info.invest_range);
                    String[] m = info.invest_range.replace("元", "").split("-");
                  /*  lM = Double.parseDouble(m[0]);
                    HM = Double.parseDouble(m[1]);
                    money_bonus
                    */
//                    tv03.setText(info.account_money);

                    try{
                        lM = Double.parseDouble(m[0]);
                        HM = Double.parseDouble(m[1]);
                    }catch (Exception e){
                        e.getMessage();
                    }
                    tv02.setSelected(true);
                    has_pin = info.has_pin;
                    if (!info.bonusList.isEmpty()) {
                        list = info.bonusList;
                        PaySpinnerAdapter spinnerAdapter = new PaySpinnerAdapter(list);
                        sp01.setAdapter(spinnerAdapter);
                    } else {
                        tv03.setSelected(true);
                       // tv03.setText("0元");
                    }
                    if (!info.rateList.isEmpty()) {
                        list1 = info.rateList;
                        PaySpinnerAdapter spinnerAdapter1 = new PaySpinnerAdapter(list1);
                        sp02.setAdapter(spinnerAdapter1);
                    } else {
                        tv04.setSelected(true);
                     //   tv04.setText("0.00%");
                    }
                    if (has_pin.equals("1")) {
                        llpass.setVisibility(View.VISIBLE);
                        iv03.setVisibility(View.VISIBLE);
                        iv04.setVisibility(View.VISIBLE);
                    }
                    bt01.setSelected(true);
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                UtilToast.show(PayActivity.this, JsonInvestpay.TOAST);
                bt01.setSelected(false);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(PayActivity.this, true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cb_01:
                if (TextUtils.isEmpty(has_pin)) {
                    getAccessToken1();
                } else if (!tv03.isSelected()) {
                    ivcb01.setSelected(!ivcb01.isSelected());
                } else if (tv03.isSelected()) {
                    ToastUtils.show(this, "无红包可用！");
                }

                break;
            case R.id.iv_cb_02:
                if (TextUtils.isEmpty(has_pin)) {
                    getAccessToken1();
                } else if (!tv04.isSelected()) {
                    ivcb02.setSelected(!ivcb02.isSelected());
                } else if (tv04.isSelected()){
                    ToastUtils.show(this, "无优惠卷可用！");
                }
                break;
            case R.id.iv_cb_03:
                ivcb03.setSelected(!ivcb03.isSelected());
                break;
            case R.id.tv_03:
                if (ivcb01.isSelected()) {
                    sp01.performClick();
                }
                break;
            case R.id.tv_04:
                if (ivcb02.isSelected()) {
                    sp02.performClick();
                }
                break;
            case R.id.bt_01:
                if (bt01.isSelected()) {
                    if (TextUtils.isEmpty(et01.getText().toString())) {
                        ToastUtils.show(this, "请输入金额！");
                    }/* else if ((et01.getText().toString().contains(".") && Double.parseDouble(et01.getText().toString()) < lM) || (!et01.getText().toString().contains(".") && Integer.parseInt(et01.getText().toString()) < lM)||(et01.getText().toString().contains(".") && Double.parseDouble(et01.getText().toString()) > HM && HM>0.00)  || (!et01.getText().toString().contains(".") && Integer.parseInt(et01.getText().toString()) > HM && HM>0.00)) {
                        ToastUtils.show(this, "请输入支付额度范围内的金额！");
                    }*/ else {
                        if (MODE) {
                            if (has_pin.equals("1") && TextUtils.isEmpty(et02.getText().toString())) {
                                ToastUtils.show(this, "请输入投资密码！");
                            } else {
                                investPay();
                            }
                        } else {
                            if (TextUtils.isEmpty(et02.getText().toString())) {
                                ToastUtils.show(this, "请输入支付密码！");
                            } else {
                                getproductPayAccessToken();
                            }
                        }
                    }
                }

                break;
        }
    }

    private void investPay() {
        String is_experience ="";
        String member_interest_rate_id ="";
        String bonus_id ="";
        if (ivcb03.isSelected()) {
            is_experience = "1";
        } else {
            is_experience = "0";
        }
        if (ivcb01.isSelected()) {
            bonus_id = list.get(sp01.getSelectedItemPosition()).id;
        } else {
            bonus_id = "0";
        }
        if (ivcb02.isSelected()) {
            member_interest_rate_id = list1.get(sp02.getSelectedItemPosition()).id;
        } else {
            member_interest_rate_id = "0";
        }
        getInvestPayDoAccessToken(is_experience, member_interest_rate_id, bonus_id);
    }


    private void getInvestPayDoAccessToken(final String is_experience, final String member_interest_rate_id, final String bonus_id) {
        new JsonAccessToken("index/invest_pay_do", new AsyCallBack<JsonAccessToken.Info>() {


            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initInvestPayDo(info.access_token,is_experience, member_interest_rate_id, bonus_id);
                        Log.d("access_token"+info.access_token,"access_token");
                        Log.d("is_experience"+is_experience,"is_experience");
                        Log.d("member_interest_rate_id"+member_interest_rate_id,"member_interest_rate_id");
                        Log.d("bonus_id"+bonus_id,"bonus_id");

                    } else {
                        UtilToast.show( PayActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( PayActivity.this, "安全验证失败！");
            }
        }).execute(this,true);

    }

    private void initInvestPayDo(String access_token,String is_experience, String member_interest_rate_id, String bonus_id) {
        new JsonInvestpaydo(access_token,MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"),et01.getText().toString(),is_experience,member_interest_rate_id,bonus_id,et02.getText().toString(),new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    MyApplication.INSTANCE.finishActivity(InvestmentActivity.class);
                    finish();
                }
            }
            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(PayActivity.this, JsonInvestpaydo.TOAST);
            }

        }).execute(this, true);
    }

    private void getproductPayAccessToken() {
        new JsonAccessToken("index/product_pay_do", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        productPay(info.access_token);
                    } else {
                        UtilToast.show( PayActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( PayActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }

    private void productPay(String access_token) {


        new JsonProductpaydo(access_token,MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"),et02.getText().toString(),et01.getText().toString(),new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    MyApplication.INSTANCE.finishActivity(ManageFinancesActivity.class);
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(PayActivity.this, JsonProductpaydo.TOAST);
            }

        }).execute(this, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_01:
                tv03.setText(list.get(position).interest_rate + "元");
                break;
            case R.id.sp_02:
                tv04.setText(list1.get(position).interest_rate + "%");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
