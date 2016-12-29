package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.ApplyVipAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonVip;
import com.haiying.p2papp.conn.JsonVipapply;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ToastUtils;

public class VipApplyActivity extends BaseActivity implements View.OnClickListener {

    private ApplyVipAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private ImageView ivback;
    private EditText et01;
    private RecyclerView rv01;
    private Button bt01;
    public List<JsonVip.Info.ListContent> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_apply);
        initView();
        initListener();
        getVipDataAccessToken();

    }

    private void initListener() {
        bt01.setOnClickListener(this);
    }


    private void initView() {
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.et01 = (EditText) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        gridLayoutManager = new GridLayoutManager(this,3);
        rv01.setLayoutManager(gridLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new ApplyVipAdapter(this,list);
        rv01.setAdapter(mAdapter);
    }

    private void getVipDataAccessToken() {
        new JsonAccessToken("user/vip", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        getVipData(info.access_token);
                    } else {
                        UtilToast.show( VipApplyActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( VipApplyActivity.this, "安全验证失败！");
            }
        }).execute(this,true);

    }


    private void getVipData(String access_token) {
        new JsonVip(access_token,MyApplication.myPreferences.readUid(), new AsyCallBack<JsonVip.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonVip.Info info) throws Exception {
                if (info != null) {
                    list.clear();
                    list.addAll(info.list);

                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                ToastUtils.show(VipApplyActivity.this, JsonVip.TOAST);
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                mAdapter.notifyDataSetChanged();
            }

        }).execute(VipApplyActivity.this, true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_01:
                if (TextUtils.isEmpty(et01.getText().toString())) {
                    ToastUtils.show(this,"请输入申请理由！");
                } else if (list.isEmpty()) {
                    ToastUtils.show(this,"请选择业务指导客服！");
                    getVipDataAccessToken();
                } else {
                    getAccessToken();
                }
                break;
        }
    }

    private void getAccessToken() {
        new JsonAccessToken("user/vip_apply", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show( VipApplyActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( VipApplyActivity.this, "安全验证失败！");
            }
        }).execute(this,true);

    }

    private void initData(String access_token) {
        new JsonVipapply(access_token,MyApplication.myPreferences.readUid(), list.get(mAdapter.getSelectItemPosition()).id, et01.getText().toString(),new AsyCallBack() {
            @Override
            public void onSuccess(String toast, int type, Object o) throws Exception {
                if (o.equals("")) {
                    finish();
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(VipApplyActivity.this, JsonVipapply.TOAST);
            }

        }).execute(this, true);
    }

}
