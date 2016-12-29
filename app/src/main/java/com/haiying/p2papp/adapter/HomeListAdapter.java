package com.haiying.p2papp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonIndex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HomeListAdapter extends BaseAdapter {

    private Context context;
    private List<JsonIndex.Info.MenuListContent> menuListContent = new ArrayList<>();

    public HomeListAdapter(Context context, List<JsonIndex.Info.MenuListContent> menuListContent) {
        this.context = context;
        this.menuListContent = menuListContent;
    }

    @Override
    public int getCount() {
        return menuListContent.size();
    }

    @Override
    public Object getItem(int position) {
        return menuListContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_list, null);

            MyApplication.scaleScreenHelper.loadView((ViewGroup) convertView);

            viewHolder = new ViewHolder();

            viewHolder.tv_home_list_title = (TextView) convertView.findViewById(R.id.tv_home_list_title);

            viewHolder.tv_home_list_desc = (TextView) convertView.findViewById(R.id.tv_home_list_desc);

            viewHolder.img_home_list = (ImageView) convertView.findViewById(R.id.img_home_list);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        switch (position) {

            case 0:

                viewHolder.img_home_list.setImageResource(R.mipmap.home_list01);

                break;

            case 1:

                viewHolder.img_home_list.setImageResource(R.mipmap.home_list02);

                break;

            case 2:

                viewHolder.img_home_list.setImageResource(R.mipmap.home_list03);

                break;

        }

        viewHolder.tv_home_list_title.setText(menuListContent.get(position).title);

        viewHolder.tv_home_list_desc.setText(menuListContent.get(position).desc);

        return convertView;

    }

    private class ViewHolder {

        public TextView tv_home_list_title, tv_home_list_desc;

        public ImageView img_home_list;

    }

}
