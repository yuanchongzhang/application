package com.haiying.p2papp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonInvestdetail;

import java.util.List;

/**
 * Created by Administrator on 4/19/2016.
 */
public class MyViewPager02Adapter extends PagerAdapter {
    private Context context;
    private List<View> mListViews;
    private String webUrl01;
    private List<JsonInvestdetail.Info.InvestListContent> list1;

    public MyViewPager02Adapter(Context context, List<View> mListViews, String webUrl01, List<JsonInvestdetail.Info.InvestListContent> list1) {
        this.context = context;
        this.mListViews = mListViews;
        this.webUrl01 = webUrl01;
        this.list1 = list1;
    }


    @Override

    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mListViews.get(position));//删除页卡
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        switch (position) {
            case 0:
                WebView webView = (WebView) mListViews.get(position).findViewById(R.id.wv_01);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setBuiltInZoomControls(false);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setDisplayZoomControls(false);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                WebChromeClient webchromeClient = new WebChromeClient();
                webView.setWebChromeClient(webchromeClient);
                webView.loadUrl(webUrl01);
                break;
            case 1:
                RecyclerView recyclerView = (RecyclerView) mListViews.get(position).findViewById(R.id.rv_01);
                RecyclerView.Adapter mAdapter = new ViewPagerListAdapter(context, (List) list1, 0);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);
                break;
        }
        //这个方法用来实例化页卡
        ((ViewPager) container).addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

}
