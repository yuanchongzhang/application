package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.conn.JsonAccessToken;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;

public class AdvertisingActivity extends BaseActivity {

    private TextView tvtitle;
    private ImageView ivback;
    private WebView wv01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        initView();
    }

    private void initView() {
        this.wv01 = (WebView) findViewById(R.id.wv_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        tvtitle.setText(getIntent().getStringExtra("title"));
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
        if (getIntent().getStringExtra("url").contains("index/article_content")) {
            getAccessToken();
        }
        wv01.loadUrl(getIntent().getStringExtra("url"));
        Log.d(getIntent().getStringExtra("url"),"33333333333333333333333");

    }

    private void getAccessToken() {
        new JsonAccessToken("index/article_content", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        wv01.loadUrl(getIntent().getStringExtra("url")+"&access_token="+info.access_token);
                    } else {
                        UtilToast.show(AdvertisingActivity.this, "安全验证失败！");
                    }

                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(AdvertisingActivity.this, "安全验证失败！");

            }
        }).execute(this, true);

    }
}
