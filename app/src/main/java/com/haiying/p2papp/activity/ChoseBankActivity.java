package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.BankListAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBanklist;
import com.haiying.p2papp.conn.JsonBanknamelist;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

public class ChoseBankActivity extends BaseActivity {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageView ivback;
    private RecyclerView rv01;
    private List<JsonBanklist.Info.ListContent> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_bank);
        initView();
    /*  if (getIntent().getStringExtra("mode").equals("1")){
          getBankAccessToken();
      }else {
          getBankCardAccessToken();
      }*/

        if (getIntent().getIntExtra("mode", 1) == 1) {
            getBankAccessToken();
        } else {
            getBankCardAccessToken();
        }
    }

    private void getBankAccessToken() {
        new JsonAccessToken("user/bank_name_list", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initBankListData(info.access_token);
                    } else {
                        UtilToast.show(ChoseBankActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ChoseBankActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }

    private void getBankCardAccessToken() {
        new JsonAccessToken("user/banklist", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initBankCardListData(info.access_token);
                    } else {
                        UtilToast.show(ChoseBankActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(ChoseBankActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initBankCardListData(String access_token) {
        new JsonBanklist(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonBanklist.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonBanklist.Info info) throws Exception {
                if (info != null) {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    list.addAll(info.list);

                } else {
                    UtilToast.show(ChoseBankActivity.this, JsonBanklist.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                mAdapter.notifyDataSetChanged();
            }

        }).execute(ChoseBankActivity.this, true);
    }

    private void initBankListData(String access_token) {
        new JsonBanknamelist(access_token, MyApplication.myPreferences.readUid(), new AsyCallBack<JsonBanknamelist.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonBanknamelist.Info info) throws Exception {
                if (info != null) {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    list.addAll(info.list);
                } else {
                    UtilToast.show(ChoseBankActivity.this, JsonBanknamelist.TOAST);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                mAdapter.notifyDataSetChanged();
            }

        }).execute(ChoseBankActivity.this, true);
    }

    private void initView() {
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new BankListAdapter(this, list, getIntent().getIntExtra("mode", 1));
        rv01.setAdapter(mAdapter);
    }
}
