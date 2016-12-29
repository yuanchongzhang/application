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
 * Created by Administrator on 4/18/2016.
 */
public class MyViewPager01Adapter extends PagerAdapter {
    private Context context;
    private List<View> mListViews;
    private String webUrl01;
    private String webUrl02;
    private List<JsonInvestdetail.Info.InvestListContent> list1;
    private List<JsonInvestdetail.Info.PayingListContent> list2;

    public MyViewPager01Adapter(Context context, List<View> mListViews, String webUrl01, String webUrl02, List<JsonInvestdetail.Info.InvestListContent> list1, List<JsonInvestdetail.Info.PayingListContent> list2) {
        this.context = context;
        this.mListViews = mListViews;
        this.webUrl01 = webUrl01;
        this.webUrl02 = webUrl02;
        this.list1 = list1;
        this.list2 = list2;
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
                WebView webView1 = (WebView) mListViews.get(position).findViewById(R.id.wv_01);
                webView1.getSettings().setJavaScriptEnabled(true);
                webView1.getSettings().setBuiltInZoomControls(false);
                webView1.getSettings().setUseWideViewPort(true);
                webView1.getSettings().setDisplayZoomControls(false);
                webView1.getSettings().setLoadWithOverviewMode(true);
                webView1.getSettings().setUseWideViewPort(true);
                webView1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                WebChromeClient chromeClient = new WebChromeClient();
                webView1.setWebChromeClient(chromeClient);
                webView1.loadUrl(webUrl02);
                break;
            case 2:
                RecyclerView recyclerView = (RecyclerView) mListViews.get(position).findViewById(R.id.rv_01);
                RecyclerView.Adapter mAdapter = new ViewPagerListAdapter(context, (List) list1, 0);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);
                break;
            case 3:
                RecyclerView recyclerView1 = (RecyclerView) mListViews.get(position).findViewById(R.id.rv_01);
                RecyclerView.Adapter adapter = new ViewPagerListAdapter(context, (List) list2, 1);
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
                recyclerView1.setLayoutManager(layoutManager1);
                recyclerView1.setAdapter(adapter);
                break;
        }
        //这个方法用来实例化页卡
        ((ViewPager) container).addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

}
