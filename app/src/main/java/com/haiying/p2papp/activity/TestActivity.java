package com.haiying.p2papp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initListener();
    }

    private void initView() {
        this.ivback = (ImageView) findViewById(R.id.iv_back);
    }

    private void initListener() {
        ivback.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.iv_back:
                intent.setClass(this, PersonalSettingsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
