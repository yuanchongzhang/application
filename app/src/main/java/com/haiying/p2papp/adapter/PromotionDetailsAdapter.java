package com.haiying.p2papp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonBonus;
import com.haiying.p2papp.conn.JsonChargelog;
import com.haiying.p2papp.conn.JsonPromotionfriend;
import com.zcx.helper.bound.BoundViewHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 3/9/2016.
 */
public class PromotionDetailsAdapter extends RecyclerView.Adapter<PromotionDetailsAdapter.ViewHolder> {


    private Context context;
    private int flag = 0;
    private List<Object> list;

    public PromotionDetailsAdapter(Context context, int flag, List<Object> list) {
        this.context = context;
        this.flag = flag;
        this.list = list;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_01)
        TextView tv01;
        @Bind(R.id.tv_02)
        TextView tv02;

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
                .inflate(R.layout.list_05_item_view, null)));
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
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
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (flag == 0) {
            holder.tvName.setText(((JsonPromotionfriend.Info.AwardMoneyLog) list.get(position)).user_name);
        //    holder.tv02.setTextColor(context.getResources().getColor(R.color.colorMainBlue));
            holder.tv02.setText("+  "+((JsonPromotionfriend.Info.AwardMoneyLog) list.get(position)).award_money);
        } else if (flag == 1) {
            holder.tvName.setText(((JsonPromotionfriend.Info.Members) list.get(position)).user_name);
            holder.tv01.setVisibility(View.GONE);
            holder.tv02.setText(((JsonPromotionfriend.Info.Members) list.get(position)).reg_time);
        } else if (flag == 2) {
            holder.tvName.setText("提现成功");
            holder.tv01.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(((JsonChargelog.Info.ListContent) list.get(position)).add_time);
            holder.tv02.setText("- "+((JsonChargelog.Info.ListContent) list.get(position)).money + "元");
        } else if (flag == 3) {
            holder.tvName.setText("充值成功");
            holder.tv01.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(((JsonChargelog.Info.ListContent) list.get(position)).add_time);
          //  holder.tv02.setTextColor(context.getResources().getColor(R.color.colorMainBlue));
            holder.tv02.setText("+ " +((JsonChargelog.Info.ListContent) list.get(position)).money + "元");
        } else if (flag == 4) {
            holder.tvName.setText(((JsonBonus.Info.ListContent) list.get(position)).status_cn);
            holder.tv01.setVisibility(View.GONE);
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.tvTime.setText(((JsonBonus.Info.ListContent) list.get(position)).end_time);
//            if (((JsonBonus.Info.ListContent) list.get(position)).money_bonus.contains("+")) {
               // holder.tv02.setTextColor(context.getResources().getColor(R.color.colorMainBlue));
//            } else {
//                holder.tv02.setTextColor(context.getResources().getColor(R.color.colorForgotPink));
//            }
            holder.tv02.setText("+ "+((JsonBonus.Info.ListContent) list.get(position)).money_bonus + "元");
        }

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