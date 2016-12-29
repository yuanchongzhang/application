package com.haiying.p2papp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonTendout;
import com.zcx.helper.bound.BoundViewHelper;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 4/26/2016.
 */
public class InvestManageDetailAdapter extends RecyclerView.Adapter<InvestManageDetailAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    private Context context;
    private List<JsonTendout.Info.ListContent> list;

    public InvestManageDetailAdapter(Context context, List<JsonTendout.Info.ListContent> list) {
        this.context = context;
        this.list = list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        /*@Bind(R.id.tv_edit_01)
        TextView tvEdit01;*/
        @Bind(R.id.tv_05)
        TextView tv05;
        @Bind(R.id.tv_06)
        TextView tv06;
        @Bind(R.id.tv_07)
        TextView tv07;
        @Bind(R.id.tv_09)
        TextView tv09;
        @Bind(R.id.tv_01)
        TextView tv01;
        @Bind(R.id.tv_02)
        TextView tv02;
        @Bind(R.id.tv_03)
        TextView tv03;
        @Bind(R.id.tv_08)
        TextView tv08;
        @Bind(R.id.tv_04)
        TextView tv04;


        public ViewHolder(View view) {
            super(view);
            // ButterKnife.bind(this, view);
            tv01 = (TextView) view.findViewById(R.id.tv_01);
            tv02 = (TextView) view.findViewById(R.id.tv_02);

            tv03 = (TextView) view.findViewById(R.id.tv_03);
            tv08 = (TextView) view.findViewById(R.id.tv_08);

             tv04 = (TextView) view.findViewById(R.id.tv_04);
            tv05 = (TextView) view.findViewById(R.id.tv_05);


            tv06 = (TextView) view.findViewById(R.id.tv_06);
            tv07 = (TextView) view.findViewById(R.id.tv_07);

            tv09 = (TextView) view.findViewById(R.id.tv_09);


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
                .inflate(R.layout.list_19_item_view, null)));
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link  ListView}, RecyclerView will not call this method
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
    /*    holder.tv01 = (TextView) holder.tv01.findViewById(R.id.tv_01);
        holder.tv02 = (TextView) holder.tv02.findViewById(R.id.tv_02);

        holder.tv03 = (TextView) holder.tv03.findViewById(R.id.tv_03);
        holder.tv08 = (TextView) holder.tv08.findViewById(R.id.tv_08);

        holder.tv04 = (TextView) holder.tv04.findViewById(R.id.tv_04);
        holder.tv05 = (TextView) holder.tv05.findViewById(R.id.tv_05);


        holder.tv06 = (TextView) holder.tv06.findViewById(R.id.tv_06);
        holder.tv07 = (TextView) holder.tv07.findViewById(R.id.tv_07);

        holder.tv09 = (TextView) holder.tv09.findViewById(R.id.tv_09);*/

        holder.tv01.setText(list.get(position).borrow_name);
        holder.tv02.setText(list.get(position).borrow_id);
        holder.tv08.setText("投标日期：" + list.get(position).invest_time_cn);
        holder.tv03.setText("借入人：" + list.get(position).borrow_user);
      holder.tv04.setText(list.get(position).borrow_interest_rate+"%");
        holder.tv05.setText(list.get(position).borrow_money + "元");
        holder.tv06.setText(list.get(position).borrow_duration_cn);
        holder.tv07.setText("我的投资金额 : "+list.get(position).investor_capital + "元");
        holder.tv09.setText(list.get(position).investor_all + "元");
//        holder.bt01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("id", list.get(position).id);
//                intent.putExtra("flag", 1);
//                intent.setClass(context, BondCancelActivity.class);
//                context.startActivity(intent);
//            }
//        });

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
