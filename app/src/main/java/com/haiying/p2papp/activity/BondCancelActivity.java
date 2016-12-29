package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBondPub;
import com.haiying.p2papp.conn.JsonBondcancel;
import com.haiying.p2papp.conn.JsonBondpaydo;
import com.haiying.p2papp.conn.JsonBondsell;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import cn.trinea.android.common.util.ToastUtils;

public class BondCancelActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivback;
    private EditText et01;
    private Button bt01;
    private TextView title;
    private EditText et02;
    private int flag = 0;//默认撤销
    private LinearLayout llet01;

    private TextView et_zhuanrangguquan;

    private TextView et_zuigaozhuanrangjiage;

    private String id;


    private LinearLayout zhuanrang_guquan_layout;

    private String zhuanrangguquan;

    private String zhuanrang_pay;

    private String zuigao_pay;

    private int rate;

    TextView et_zuigaozhuanrangjiage_shouxufei;
    private String string;
    private String bond_free_rate1;

    public String str;

    private LinearLayout zhuanrang_layout;

    private EditText et_03;

    private LinearLayout zhaiquan_chexiao_layout;

    private LinearLayout password_layout;


    private String zuigaopay;

    private String bond_fee_rate;
    int input_transele;
    int bond_fee_rate2;

    private String input_num;
    private int shouxufeilv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bond_cancel);
        this.llet01 = (LinearLayout) findViewById(R.id.ll_et_01);
        this.et_zhuanrangguquan = (TextView) findViewById(R.id.et_zhuanrangguquan);
        this.et_zuigaozhuanrangjiage = (TextView) findViewById(R.id.et_zuigaozhuanrangjiage);
        this.zhuanrang_guquan_layout = (LinearLayout) findViewById(R.id.zhuanrang_guquan_layout);
        et_zuigaozhuanrangjiage_shouxufei = (TextView) findViewById(R.id.et_zuigaozhuanrangjiage_shouxufei);


        //债权转让
        et_03 = (EditText) findViewById(R.id.et_03);
        zhaiquan_chexiao_layout = (LinearLayout) findViewById(R.id.zhaiquan_chexiao_layout);


        flag = getIntent().getIntExtra("flag", 0);

        id = getIntent().getStringExtra("id");

        zhuanrangguquan = getIntent().getStringExtra("zhuanrangdguquan");
        //  et_zhuanrangguquan.setText(zhuanrangguquan);

        zhuanrang_layout = (LinearLayout) findViewById(R.id.zhuanrang_layout);
        password_layout = (LinearLayout) findViewById(R.id.password_layout);


        this.et02 = (EditText) findViewById(R.id.et_02);
        this.title = (TextView) findViewById(R.id.title);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        bt01.setOnClickListener(this);
        switch (flag) {
            case 0:
                title.setText("债权撤销操作");
                zhuanrang_guquan_layout.setVisibility(View.GONE);
                llet01.setVisibility(View.GONE);

                //     zhaiquan_chexiao_layout.setVisibility(View.VISIBLE);

                password_layout.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) password_layout.getLayoutParams();
                // 取控件aaa当前的布局参数
                linearParams.height = 140;        // 当控件的高强制设成365象素
                password_layout.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
                break;
            case 1:
                llet01.setVisibility(View.VISIBLE);
                title.setText("债权转让操作");
                zhuanrang_guquan_layout.setVisibility(View.VISIBLE);

                // getData();
                new JsonBondPub(MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"), new AsyCallBack<JsonBondPub.Info>() {

                    @Override
                    public void onSuccess(String toast, int type, JsonBondPub.Info info) throws Exception {
                        super.onSuccess(toast, type, info);
                        // Toast.makeText(BondCancelActivity.this, "请求成功", Toast.LENGTH_SHORT).show();

                        et_zhuanrangguquan.setText(info.borrow_name);

                        zuigaopay = info.bond_max;

                        et_zuigaozhuanrangjiage.setText(info.bond_max);

                        Double double_bond_max = Double.valueOf(info.bond_max);


                        bond_fee_rate = info.bond_fee_rate;

                        String shouxufei = et01.getText().toString();


                        et01.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                textview.setText(et01.getText());
                                input_num = et01.getText().toString();

                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                // String str = et01.getText().toString();


                                try {
                                    input_transele = Integer.parseInt(input_num);
                                    bond_fee_rate2 = Integer.parseInt(bond_fee_rate);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            /*    int input_transele=Integer.parseInt(str);
                                int bond_fee_rate2=Integer.parseInt(bond_fee_rate);*/

                                shouxufeilv = input_transele * bond_fee_rate2 / 100;


                                Log.d(shouxufeilv + "", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");


                                et_zuigaozhuanrangjiage_shouxufei.setText(shouxufeilv + "");

                            }
                        });


                        string = info.bond_max;

                        Log.d(info.bond_fee_rate, "99999999999999999999999");


                    }


                }).execute(this, true);

                Log.d(string + bond_free_rate1, "adsfklfklfklfklfklfklfklfklfklfklfkl");

/*
                int a=Integer.parseInt(string)*Integer.parseInt(bond_free_rate)/100;


                et_zuigaozhuanrangjiage_shouxufei.setText(String.valueOf(a));
            */
                break;
            case 2:
                title.setText("债权转让操作");
               // zhuanrang_guquan_layout.setVisibility(View.VISIBLE);
                zhuanrang_guquan_layout.setVisibility(View.GONE);
                llet01.setVisibility(View.GONE);
            LinearLayout.LayoutParams linearParams2 = (LinearLayout.LayoutParams) password_layout.getLayoutParams();
                  // 取控件aaa当前的布局参数
                 linearParams2.height = 140;        // 当控件的高强制设成365象素
               password_layout.setLayoutParams(linearParams2); // 使设置好的布局参数应用到控件aaa

                // getData();
                new JsonBondPub(MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"), new AsyCallBack<JsonBondPub.Info>() {

                    @Override
                    public void onSuccess(String toast, int type, JsonBondPub.Info info) throws Exception {
                        super.onSuccess(toast, type, info);
                        // Toast.makeText(BondCancelActivity.this, "请求成功", Toast.LENGTH_SHORT).show();

                        et_zhuanrangguquan.setText(info.borrow_name);

                        zuigaopay = info.bond_max;

                        et_zuigaozhuanrangjiage.setText(info.bond_max);

                        Double double_bond_max = Double.valueOf(info.bond_max);


                        bond_fee_rate = info.bond_fee_rate;

                        String shouxufei = et01.getText().toString();


                        et01.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                                textview.setText(et01.getText());
                                input_num = et01.getText().toString();

                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                // String str = et01.getText().toString();


                                try {
                                    input_transele = Integer.parseInt(input_num);
                                    bond_fee_rate2 = Integer.parseInt(bond_fee_rate);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            /*    int input_transele=Integer.parseInt(str);
                                int bond_fee_rate2=Integer.parseInt(bond_fee_rate);*/

                                shouxufeilv = input_transele * bond_fee_rate2 / 100;


                                Log.d(shouxufeilv + "", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");


                                et_zuigaozhuanrangjiage_shouxufei.setText(shouxufeilv + "");

                            }
                        });


                        string = info.bond_max;

                        Log.d(info.bond_fee_rate, "99999999999999999999999");


                    }


                }).execute(this, true);

                Log.d(string + bond_free_rate1, "adsfklfklfklfklfklfklfklfklfklfklfkl");

                break;
        }


    }


    @Override
    public void onClick(View v) {
        if (flag == 1 && TextUtils.isEmpty(et01.getText().toString())) {
            ToastUtils.show(this, et01.getHint());
        } else if (TextUtils.isEmpty(et02.getText().toString())) {
            ToastUtils.show(this, et02.getHint());
        } else if (et02.getText().toString().length() < 6 || et02.getText().toString().length() > 20) {
            ToastUtils.show(this, "请输入6-20位支付密码！");
        } else {
            initData();
        }

    }

    private void initData() {
        switch (flag) {
            case 0:
                getCancelAccessToken();
                break;
            case 1:


                getSellAccessToken();


                break;
            case 2:
                getPayAccessToken();
                break;
        }

    }

    private void getCancelAccessToken() {
        new JsonAccessToken("user/bond_cancel", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initCancel(info.access_token);
                    } else {
                        UtilToast.show(BondCancelActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BondCancelActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    private void getSellAccessToken() {
        new JsonAccessToken("user/bond_sell", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initSell(info.access_token);


                    } else {
                        UtilToast.show(BondCancelActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BondCancelActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    private void getPayAccessToken() {
//        new JsonAccessToken("index/bond_pay_do", new AsyCallBack<JsonAccessToken.Info>() {

        new JsonAccessToken("index/bond_sell", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initPay(info.access_token);
                    } else {
                        UtilToast.show(BondCancelActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(BondCancelActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    private void initPay(String access_token) {
        Log.d("access_token:"+access_token +"uid:"+ MyApplication.myPreferences.readUid() + "id:"+getIntent().getStringExtra("id") + "et2"+et02.getText().toString() + shouxufeilv + "", "222222222222222222222222222222222");
        new JsonBondpaydo(access_token,getIntent().getStringExtra("id"), MyApplication.myPreferences.readUid(),  et02.getText().toString(), new AsyCallBack() {


            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {

                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(BondCancelActivity.this, JsonBondpaydo.TOAST);
            }

        }).execute(this, true);
    }

    private void initSell(String access_token) {
        Log.d(access_token + MyApplication.myPreferences.readUid() + getIntent().getStringExtra("id") + et_zhuanrangguquan.getText().toString() + et_zuigaozhuanrangjiage.getText().toString() + et02.getText().toString(), shouxufeilv + "");
    /*    // String access_token,String uid, String id, String borrow_name, String bond_max,String bond_fee_rate,String paypass,
        String str_bondmax = string;
        String str_zuigaostr = et01.getText().toString();
//        String str_zhuanrangshouxu=str_zuigaostr*string/100;
        int bond_max = Integer.parseInt(string);
        int user_input_count = Integer.parseInt(str_zuigaostr);

        int c = bond_max * user_input_count / 100;
        //String str = String.valueOf(c);
        str = String.valueOf(c);
        // et_zuigaozhuanrangjiage_shouxufei.setText(str);

        final View rootView = ((Activity) context).getWindow().getDecorView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height() + r.top;
                if (visibleHeight < rootView.getHeight()) {
                    // 软键盘显示ing
                } else {
                    // 软键盘收起,点输入法的收起后会走到这边


                }
            }
        });


        Log.d(c + "", "+++++++++++++++++++++++++++++++++++++");*/

//        new JsonBondsell(access_token, MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"), et_zhuanrangguquan.getText().toString(), et_zuigaozhuanrangjiage.getText().toString(), shouxufeilv + "", et02.getText().toString(),

       // public String access_token, uid, money, id, paypass;
        new JsonBondsell(access_token, MyApplication.myPreferences.readUid(), et01.getText().toString(),getIntent().getStringExtra("id"), et02.getText().toString(),
                new AsyCallBack() {
                    @Override
                    public void onSuccess(String toast, int type, Object o) throws Exception {
                        if (o.equals("")) {


                            finish();
                        }
                    }

                    @Override
                    public void onEnd(String toast, int type) throws Exception {
                        UtilToast.show(BondCancelActivity.this, JsonBondsell.TOAST);
                    }

                }).execute(this, true);
    }

    private void initCancel(String access_token) {
        new JsonBondcancel(access_token, MyApplication.myPreferences.readUid(), getIntent().getStringExtra("id"), et02.getText().toString(), new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(BondCancelActivity.this, JsonBondcancel.TOAST);
            }

        }).execute(this, true);
    }
}
