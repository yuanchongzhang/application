package com.haiying.p2papp.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2/17/2016.
 */
public class FragPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public FragPagerAdapter(FragmentManager fm, List<Fragment> Fragments) {
        super(fm);
        this.mFragments = Fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
