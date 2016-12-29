package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.adapter.GuidePagerAdapter;
import com.zcx.helper.bound.BoundViewHelper;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private List<View> mListViews;
    private ViewPager vp01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        this.vp01 = (ViewPager) findViewById(R.id.vp_01);
        mListViews = new ArrayList<>();
        mListViews.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.item_guide_one, null))));
        mListViews.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.item_guide_two, null))));
        mListViews.add(BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.item_guide_three, null))));
        GuidePagerAdapter guidePagerAdapter = new GuidePagerAdapter(GuideActivity.this, mListViews);
        vp01.setAdapter(guidePagerAdapter);
        vp01.setCurrentItem(0);
    }
}
