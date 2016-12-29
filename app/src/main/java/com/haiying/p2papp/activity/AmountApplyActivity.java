package com.haiying.p2papp.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
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
import android.widget.Toast;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.CmSpinnerAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonMoneylimit;
import com.haiying.p2papp.conn.JsonMoneylimitapply;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;


public class AmountApplyActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView ivback;
    private TextView tvtitleright;
    private EditText et01;
    private EditText et02;
    private Button bt02;
//    private Spinner sp01;
    private List<JsonMoneylimit.Info.applyType> typelist = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private TextView tv01;
    private TextView tvhint;
    private PopupWindow popLeft;
    private View layoutLeft;

    private ListView menulistLeft;
    private String geteduStr;

    private LinearLayout pop_layout;


    private boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_apply);

        initView();
        initListener();
        getTypeAccessToken(false);
    }


    private void initView() {
        this.bt02 = (Button) findViewById(R.id.bt_02);
        this.tvhint = (TextView) findViewById(R.id.tv_hint);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (EditText) findViewById(R.id.et_01);
       // this.sp01 = (Spinner) findViewById(R.id.sp_01);
        this.tv01 = (TextView) findViewById(R.id.tv_01);
        this.tvtitleright = (TextView) findViewById(R.id.tv_title_right);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        pop_layout= (LinearLayout) findViewById(R.id.pop_layout);
    }

    private void initListener() {
        tvtitleright.setOnClickListener(this);
        bt02.setOnClickListener(this);
        tv01.setOnClickListener(this);
        //sp01.setOnItemSelectedListener(this);
        et02.setOnClickListener(this);
        et02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvhint.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    tvhint.setVisibility(View.VISIBLE);
                } else {
                    tvhint.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_title_right:
                Intent intent = new Intent();
                intent.setClass(this, ApplyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.et_02:
                tvhint.setVisibility(View.GONE);
                break;
            case R.id.tv_01:
               /* if (list.isEmpty()) {
                    getTypeAccessToken(true);
                } else {sp_01
                    sp01.performClick();
                }*/
                if (flag){
                    BorrowMoneyQixianData();
                }else {
                    bt02.setEnabled(false);
                }



                break;
            case R.id.bt_02:
                if (list.isEmpty()) {
                    ToastUtils.show(this,"请选择申请类型");
                } else if (TextUtils.isEmpty(et01.getText().toString())) {
                    ToastUtils.show(this,"请输入申请金额");
                } else if (TextUtils.isEmpty(et02.getText().toString())) {
                    ToastUtils.show(this,"请输入借款说明");
                } else {
                    getApplyAccessToken();
                }
                break;
        }

    }

    private void getTypeAccessToken(final Boolean isClick) {
        new JsonAccessToken("user/moneylimit", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        getType(info.access_token,isClick);
                    } else {
                        UtilToast.show(AmountApplyActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(AmountApplyActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    private void getType(String access_token,final Boolean isClick) {

        new JsonMoneylimit(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonMoneylimit.Info>() {


            @Override
            public void onSuccess(String toast, int type, JsonMoneylimit.Info info) throws Exception {
                if (info != null) {
                    if (!list.isEmpty())
                        list.clear();
                    for (int i = 0; i < info.list.size(); i++) {
                        list.add(info.list.get(i).name);
                    }
                    typelist.addAll(info.list);
                    flag=true;
                    CmSpinnerAdapter cmSpinnerAdapter = new CmSpinnerAdapter(list);
                    /*sp01.setAdapter(cmSpinnerAdapter);
                    if (isClick) {
                        sp01.performClick();
                    }*/
                }else {
                    Toast.makeText(AmountApplyActivity.this, "审核正在申请中", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                Toast.makeText(AmountApplyActivity.this, "审核正在申请中", Toast.LENGTH_SHORT).show();
                flag=false;
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(AmountApplyActivity.this, true);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tv01.setText(list.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getApplyAccessToken() {
        new JsonAccessToken("user/moneylimit_apply", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        goApply(info.access_token);
                    } else {
                        UtilToast.show(AmountApplyActivity.this, "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(AmountApplyActivity.this, "安全验证失败！");
            }
        }).execute(this, false);

    }


    private void goApply(String access_token) {
       // new JsonMoneylimitapply(access_token,MyApplication.myPreferences.readUid(),typelist.get(sp01.getSelectedItemPosition()).id,et01.getText().toString(),et02.getText().toString(),new AsyCallBack() {
        new JsonMoneylimitapply(access_token,MyApplication.myPreferences.readUid(),geteduStr,et01.getText().toString(),et02.getText().toString(),new AsyCallBack() {

            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(AmountApplyActivity.this, JsonMoneylimitapply.TOAST);
            }

        }).execute(this, true);

    }





    public void BorrowMoneyQixianData() {
        if (popLeft != null && popLeft.isShowing()) {
            popLeft.dismiss();
        } else {
            layoutLeft =getLayoutInflater().inflate(
                    R.layout.pop_menulist, null);
            menulistLeft = (ListView) layoutLeft
                    .findViewById(R.id.menulist);
            menulistLeft.setAdapter(new ArrayAdapter<String>(AmountApplyActivity.this, R.layout.spinner_list_item, list));

            menulistLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // textCity.setText(list.get(position));
                    tv01.setText(typelist.get(position).name);
                    geteduStr=typelist.get(position).id;
                   // myet04 = borrow_duration.get(position).id;
                    // popupWindow.dismiss();
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

}
