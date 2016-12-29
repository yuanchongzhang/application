package com.haiying.p2papp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.haiying.p2papp.adapter.FragPagerAdapter;
import com.haiying.p2papp.fragment.FifthFragment;
import com.haiying.p2papp.fragment.FirstFragment;
import com.haiying.p2papp.fragment.FourthFragment;
import com.haiying.p2papp.fragment.SecondFragment;
import com.haiying.p2papp.fragment.ThirdFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    /**
     * 主页viewpager的adapter
     */
    private FragPagerAdapter pagerAdapter;
    /**
     * 保存fragment的list
     */
    private ArrayList<Fragment> fragments;
    /**
     * fragment管理类
     */
    private FragmentManager fragmentManager;


    public Boolean FLAG = Boolean.TRUE;

    @Bind(R.id.iv_main_tab_01)
    ImageView ivMainTab01;
    @Bind(R.id.tv_main_tab_01)
    TextView tvMainTab01;
    @Bind(R.id.ll_main_tab_01)
    LinearLayout llMainTab01;
    @Bind(R.id.iv_main_tab_02)
    ImageView ivMainTab02;
    @Bind(R.id.tv_main_tab_02)
    TextView tvMainTab02;
    @Bind(R.id.ll_main_tab_02)
    LinearLayout llMainTab02;
    @Bind(R.id.iv_main_tab_03)
    ImageView ivMainTab03;
    @Bind(R.id.tv_main_tab_03)
    TextView tvMainTab03;
    @Bind(R.id.ll_main_tab_03)
    LinearLayout llMainTab03;
    @Bind(R.id.iv_main_tab_04)
    ImageView ivMainTab04;
    @Bind(R.id.tv_main_tab_04)
    TextView tvMainTab04;
    @Bind(R.id.ll_main_tab_04)
    LinearLayout llMainTab04;
    @Bind(R.id.iv_main_tab_05)
    ImageView ivMainTab05;
    @Bind(R.id.tv_main_tab_05)
    TextView tvMainTab05;
    @Bind(R.id.ll_main_tab_05)
    LinearLayout llMainTab05;
    @Bind(R.id.vp_main)
    ViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        ThirdFragment thirdFragment = new ThirdFragment();
        FourthFragment fourthFragment = new FourthFragment();
        FifthFragment fifthFragment = new FifthFragment();
        fragments = new ArrayList<>();
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        fragments.add(thirdFragment);
        fragments.add(fourthFragment);
        fragments.add(fifthFragment);
        fragmentManager = this.getSupportFragmentManager();
        pagerAdapter = new FragPagerAdapter(fragmentManager, fragments);
        vpMain.setAdapter(pagerAdapter);
        vpMain.setOffscreenPageLimit(0);
        ivMainTab01.setSelected(true);
        tvMainTab01.setSelected(true);
    }

    private void initListener() {
        llMainTab01.setOnClickListener(this);
        llMainTab02.setOnClickListener(this);
        llMainTab03.setOnClickListener(this);
        llMainTab04.setOnClickListener(this);
        llMainTab05.setOnClickListener(this);
        vpMain.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_tab_01:
                vpMain.setCurrentItem(0);
                break;
            case R.id.ll_main_tab_02:
                vpMain.setCurrentItem(1);
                break;
            case R.id.ll_main_tab_03:
                vpMain.setCurrentItem(2);
                break;
            case R.id.ll_main_tab_04:
                vpMain.setCurrentItem(3);
                break;
            case R.id.ll_main_tab_05:
                vpMain.setCurrentItem(4);
                break;
        }
    }

    private void initFirstView() {
        ivMainTab01.setSelected(true);
        tvMainTab01.setSelected(true);
}

    private void initSecondView() {
        ivMainTab02.setSelected(true);
        tvMainTab02.setSelected(true);
    }

    private void initThirdView() {
        ivMainTab03.setSelected(true);
        tvMainTab03.setSelected(true);
    }

    private void initFourthView() {
        ivMainTab04.setSelected(true);
        tvMainTab04.setSelected(true);
    }

    private void initFifthView() {
        ivMainTab05.setSelected(true);
        tvMainTab05.setSelected(true);
    }

    private void initTab() {
        ivMainTab01.setSelected(false);
        tvMainTab01.setSelected(false);
        ivMainTab02.setSelected(false);
        tvMainTab02.setSelected(false);
        ivMainTab03.setSelected(false);
        tvMainTab03.setSelected(false);
        ivMainTab04.setSelected(false);
        tvMainTab04.setSelected(false);
        ivMainTab05.setSelected(false);
        tvMainTab05.setSelected(false);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        initTab();
        switch (position) {
            case 0:
                initFirstView();
                break;
            case 1:
                initSecondView();
                break;
            case 2:
                initThirdView();
                break;
            case 3:
                initFourthView();
                break;
            case 4:
                initFifthView();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    public void setSelect(int position) {
        vpMain.setCurrentItem(position);
    }

    public void setFirstSelect(boolean isFirst) {
        FLAG=isFirst;
    }
    public boolean getFirstSelect() {
        return FLAG;
    }
}
