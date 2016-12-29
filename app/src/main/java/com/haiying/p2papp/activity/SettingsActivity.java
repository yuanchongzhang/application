package com.haiying.p2papp.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.ArticleAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonArticle;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvtitle;
    private ImageView ivback;
    private RecyclerView rv01;
    private Button bt01;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<JsonArticle.Info.ListContent> list = new ArrayList<>();
    private PopupWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initView();
        getAccessToken();
        bt01.setOnClickListener(this);
    }


    private void getAccessToken() {
        new JsonAccessToken("index/article", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show( SettingsActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show( SettingsActivity.this, "安全验证失败！");
            }
        }).execute(this,true);

    }



    private void initData(String access_token) {
        new JsonArticle(access_token,new AsyCallBack<JsonArticle.Info>() {

            @Override
            public void onStart(int type) throws Exception {
                super.onStart(type);
            }

            @Override
            public void onSuccess(String toast, int type, JsonArticle.Info info) throws Exception {
                if (info != null) {
                    list.addAll(info.list);
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(SettingsActivity.this, "获取失败");
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                mAdapter.notifyDataSetChanged();
            }

        }).execute(SettingsActivity.this, true);
    }

    private void initView() {
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.rv01 = (RecyclerView) findViewById(R.id.rv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        mLayoutManager = new LinearLayoutManager(this);
        rv01.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new ArticleAdapter(this, list);
        rv01.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View view) {
        showPopupWindow(view);
    }

    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                    .inflate(R.layout.dialog_01, null)));
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

        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MyApplication.myPreferences.clear();
                onBackPressed();
                popWindow.dismiss();
            }
        });
    }
}
