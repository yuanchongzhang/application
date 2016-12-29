package com.haiying.p2papp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.MainActivity;
import com.haiying.p2papp.activity.R;

import java.util.List;

/**
 * Created by Administrator on 4/20/2016.
 */
public class GuidePagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> mListViews;

    public GuidePagerAdapter(Context context, List<View> mListViews) {
        this.context = context;
        this.mListViews = mListViews;
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

                break;
            case 1:

                break;
            case 2:
                Button  button = (Button) mListViews.get(position).findViewById(R.id.bt_01);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.guidePreferences.saveIsGuide(true);
                        Intent intent = new Intent();
                        intent.setClass(context, MainActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                });
                break;
            //155 0357 5886
        }
        //这个方法用来实例化页卡
        ((ViewPager) container).addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

}