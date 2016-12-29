package com.haiying.p2papp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.InvestmentActivity;
import com.haiying.p2papp.activity.ManageFinancesActivity;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.conn.JsonInvest;
import com.haiying.p2papp.view.ProgressBar;
import com.zcx.helper.bound.BoundViewHelper;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2/22/2016.
 */
public class MyInvestlistAdapter extends RecyclerView.Adapter<MyInvestlistAdapter.ViewHolder> {

    private Context context;
    public final static int INVESTMODE = 1;//投资
    public final static int MANAGEFINANCESMODE = 2;//理财
    public final static int BONDMODE = 3;//债卷
    private int isInvest = 0;
    private List<JsonInvest.Info.ListContent> list;


    public MyInvestlistAdapter(Context context, List<JsonInvest.Info.ListContent> list, int isInvest) {
        this.context = context;
        this.list = list;
        this.isInvest = isInvest;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.tv_rate)
        TextView tvRate;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_danwei)
        TextView tvDanwei;
        /*@Bind(R.id.crpv)
        ColorfulRingProgressView crpv;*/
        @Bind(R.id.tv_progress)
        TextView tvProgress;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_sumMoney)
        TextView tvSumMoney;

        @Bind(R.id.progress_horizontal)
        ProgressBar horizontalNumProgressBar;
        @Bind(R.id.text_danwei)
        TextView text_danwei;
        public String str;
        public int a;

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
                .inflate(R.layout.list_01_item_view, null)));
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
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
        holder.tvName.setText(list.get(position).borrow_name);
        NumberFormat nfNumber = NumberFormat.getNumberInstance();
//        holder.tvSumMoney.setText(nfNumber.format(Long.parseLong(list.get(position).need)) + "元");
        /*String str1 = list.get(position).need;
        int b = Integer.parseInt(str1);
        int c = b / 10000;
        String str2=String.valueOf(c);*/
        String str1 = list.get(position).need;


        if (str1.length() < 5) {
            holder.tvSumMoney.setText(list.get(position).need);
            holder.text_danwei.setText("元");
        }else {
         //   holder.tvSumMoney.setText(str2);
            holder.tvSumMoney.setText(list.get(position).need);
        }







      //  holder.tvSumMoney.setText(list.get(position).need);
        holder.tvRate.setText(list.get(position).borrow_interest_rate + "%");
        holder.tvTime.setText(list.get(position).borrow_duration);
        // holder.tvDanwei.setText(list.get(position).borrow_duration_cn);
        holder.tvProgress.setText(list.get(position).progress + "%");

    /*    String string = list.get(position).progress;
       String fenge=string.split(".")[0];
        int mProgress = Integer.parseInt(fenge);*/
        String string = list.get(position).progress;
        // holder.horizontalNumProgressBar.setProgress2(2);
        //int b = Integer.parseInt(string);
        if (isInvest == INVESTMODE) {
            if (string.equals("0.00")) {
                holder.horizontalNumProgressBar.setProgress(0);
            } else if (Double.valueOf(string) > 0 && Double.valueOf(string) < 1) {
                holder.horizontalNumProgressBar.setProgress(0.5);

            } else {
                holder.horizontalNumProgressBar.setProgress(Double.valueOf(string));
            }

        } else if (isInvest == MANAGEFINANCESMODE) {
            int a;
            try {
                a = Integer.parseInt(string);
                Log.d(a + "", "adsfkjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjl");
                if (a < 100) {
                    holder.horizontalNumProgressBar.setProgress2(Integer.parseInt(string) + 1);
                } else {
                    holder.horizontalNumProgressBar.setProgress2(Integer.parseInt(string));
                }

            } catch (Exception e) {
                e.getMessage();
            }




          /*  if (a==1){
                holder.horizontalNumProgressBar.setProgress2(2);
            }else if (a==2){
                holder.horizontalNumProgressBar.setProgress2(3);
            }*/


        }




      /*  String string = list.get(position).progress;
        String fenge = string.split(".")[0];
        int a = Integer.parseInt(fenge);
        if (string.equals("0")) {
            holder.horizontalNumProgressBar.setProgress(0);
        }else if (a==0)
        {
            holder.horizontalNumProgressBar.setProgress(0);
        }

        else {
              fenge = string.split(".")[0];


              a = Integer.parseInt(fenge);


            holder.horizontalNumProgressBar.setProgress(a);
        }*/


    /*    try {
            int mProgress = Integer.parseInt(string);

            holder.horizontalNumProgressBar.setProgress(mProgress);
        } catch (Exception e) {
            e.getMessage();
        }*/


        //  holder.horizontalNumProgressBar.setProgress(80);



      /*  holder.crpv.setStartAngle(getStartAngle(Float.parseFloat(list.get(position).progress)));
        holder.crpv.setPercent(Float.parseFloat(list.get(position).progress));*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInvest == INVESTMODE) {
                    Intent intent = new Intent();
                    intent.putExtra("id", list.get(position).id);
                    intent.setClass(context, InvestmentActivity.class);
                    context.startActivity(intent);
                } else if (isInvest == 2) {
                    Intent intent = new Intent();
                    intent.putExtra("id", list.get(position).id);
                    intent.setClass(context, ManageFinancesActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    private float getStartAngle(float i) {
        if (i > 95) {
            return (1 - ((i + 5 - 100) / 100)) * 360;
        } else {
            return (1 - ((i + 5) / 100)) * 360;
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
