package com.haiying.p2papp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.BondCancelActivity;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonUserbond;
import com.zcx.helper.bound.BoundViewHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 3/15/2016.
 */
public class ClaimsManage01Adapter extends RecyclerView.Adapter<ClaimsManage01Adapter.ViewHolder> {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private Context context;
    private List<JsonUserbond.Info.ListContent> list;

    public ClaimsManage01Adapter(Context context, List<JsonUserbond.Info.ListContent> list) {
        this.context = context;
        this.list = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_05)
        TextView tv05;
        @Bind(R.id.tv_06)
        TextView tv06;
        @Bind(R.id.tv_07)
        TextView tv07;
        @Bind(R.id.bt_01)
        Button bt01;
        @Bind(R.id.tv_01)
        TextView tv01;
        @Bind(R.id.tv_02)
        TextView tv02;
        @Bind(R.id.tv_03)
        TextView tv03;
        @Bind(R.id.tv_04)
        TextView tv04;
        @Bind(R.id.tv_10)
        TextView tv_10;
        @Bind(R.id.tv_11)
        TextView tv_11;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_06_item_view, null)));
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link  android.widget.ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder(ViewHolder, int)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv01.setText(list.get(position).borrow_name);
        holder.tv02.setText("借款者：" + list.get(position).user_name);
        holder.tv03.setText("投资时间：" + list.get(position).add_time);
        holder.tv04.setText("到期时间：" + list.get(position).deadline);
        //+ "期/" + list.get(position).total_period + "期"
        holder.tv05.setText(list.get(position).period);
        holder.tv_10.setText("/" + list.get(position).total_period);
     /*   String str = list.get(position).period + " 期/" + list.get(position).total_period + " 期";
        SpannableString spanText = new SpannableString(str);
        int a = list.get(position).period.length()+1;
        int b = a + 2 + list.get(position).total_period.length()+1;

        spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.zhaiquanguanli)), a, a + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new AbsoluteSizeSpan(12, true), a, a + 1,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.zhaiquanguanli)), b, b + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText.setSpan(new AbsoluteSizeSpan(12, true), b, b + 1,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);


        holder.tv05.append(spanText);*/
     /*   int bstart=str.indexOf("千");
        int bend=bstart+"分之2".length()+1;
        SpannableStringBuilder style=new SpannableStringBuilder(str);
        //  style.setSpan(new BackgroundColorSpan(Color.RED),bstart,bend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FB1010")), bstart,bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(40), bstart, bend, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_04.setText(style);*/



      /*  String str = list.get(position).period + "期/" + list.get(position).total_period + "期";
        Log.d(str,"asdklffffffffffffffff");
        SpannableString spanText = new SpannableString(str);
      *//*  spanText.setSpan(new BackgroundColorSpan(Color.GREEN), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*//*
        spanText.setSpan(new ForegroundColorSpan(Color.BLUE), 1, spanText.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
 spanText.setSpan(new AbsoluteSizeSpan(20, true), 0, spanText.length(),
        holder.tv05.append(spanText);*/
//        + "元/" + list.get(position).investor_interest + "元"
        holder.tv06.setText(list.get(position).investor_capital);
        holder.tv_11.setText("/" + list.get(position).investor_interest);
      /*  String str2 = list.get(position).investor_capital + "元 /" + list.get(position).investor_interest + "元";
        SpannableString spanText2 = new SpannableString(str2);
        int c = list.get(position).investor_capital.length();
        int d = c + 3+ list.get(position).investor_interest.length();

        spanText2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.zhaiquanguanli)), c, c + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText2.setSpan(new AbsoluteSizeSpan(12, true), c, c + 1,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.zhaiquanguanli)), d, d + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanText2.setSpan(new AbsoluteSizeSpan(12, true), d, d + 1,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);


        holder.tv06.append(spanText2);*/


        holder.tv07.setText(list.get(position).borrow_interest_rate + "%");
        holder.bt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", list.get(position).id);
                intent.putExtra("flag", 1);

                //转让的股权
                intent.putExtra("zhuanrangdguquan", list.get(position).borrow_name);
                Log.d(list.get(position).borrow_name, "ad;lksfffff");


                intent.setClass(context, BondCancelActivity.class);
                context.startActivity(intent);
            }
        });

    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }
}
