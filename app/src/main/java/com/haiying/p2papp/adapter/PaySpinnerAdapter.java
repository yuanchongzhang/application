package com.haiying.p2papp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonInvestpay;
import com.zcx.helper.bound.BoundViewHelper;

import java.util.List;

/**
 * Created by Administrator on 4/26/2016.
 */
public class PaySpinnerAdapter extends BaseAdapter {
    private List<JsonInvestpay.Info.BonusListContent> list;

    public PaySpinnerAdapter(List<JsonInvestpay.Info.BonusListContent> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || !convertView.getTag().toString().equals("DROPDOWN")) {
            convertView = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spinner_item_dropdown_02, null)));
            convertView.setTag("DROPDOWN");
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        TextView textView1 = (TextView) convertView.findViewById(android.R.id.text2);
        textView.setText(list.get(position).interest_rate);
        textView1.setText(list.get(position).end_time_cn);
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || !convertView.getTag().toString().equals("NON_DROPDOWN")) {
            convertView = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.spinner_item_01, null)));
            convertView.setTag("NON_DROPDOWN");
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(list.get(position).interest_rate);
        return convertView;
    }
}