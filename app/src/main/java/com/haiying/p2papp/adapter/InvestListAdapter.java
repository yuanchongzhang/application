package com.haiying.p2papp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonInvest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/22/2016.
 */
public class InvestListAdapter extends BaseAdapter {

    private Context context;
    private List<JsonInvest.Info.ListContent> listContent = new ArrayList<>();

    public InvestListAdapter(Context context, List<JsonInvest.Info.ListContent> listContent) {
        this.context = context;
        this.listContent = listContent;
    }

    @Override
    public int getCount() {
        return listContent.size();
    }

    @Override
    public Object getItem(int position) {
        return listContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_invest_list, null);

            MyApplication.scaleScreenHelper.loadView((ViewGroup) convertView);

            holder = new ViewHolder();

            holder.borrow_name = (TextView) convertView.findViewById(R.id.borrow_name);

            holder.borrow_interest_rate = (TextView) convertView.findViewById(R.id.borrow_interest_rate);

            holder.borrow_duration = (TextView) convertView.findViewById(R.id.borrow_duration);

            holder.borrow_duration_cn = (TextView) convertView.findViewById(R.id.borrow_duration_cn);

            holder.need = (TextView) convertView.findViewById(R.id.need);

            holder.progress = (TextView) convertView.findViewById(R.id.progress);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.borrow_name.setText(listContent.get(position).borrow_name);
        holder.borrow_interest_rate.setText(listContent.get(position).borrow_interest_rate + "%");
        holder.borrow_duration.setText(listContent.get(position).borrow_duration);
        holder.borrow_duration_cn.setText(listContent.get(position).borrow_duration_cn);
        holder.need.setText(listContent.get(position).need);
        holder.progress.setText(listContent.get(position).progress + "%");

        return convertView;
    }

    private class ViewHolder {
        //项目名称，年利率，期限数值，期限单位，可投金额，进度
        public TextView borrow_name, borrow_interest_rate, borrow_duration, borrow_duration_cn, need, progress;

    }

}
