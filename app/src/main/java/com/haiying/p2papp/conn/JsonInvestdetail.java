package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/15/2016.
 */
@HttpInlet("index/invest_detail")
public class JsonInvestdetail extends HYAsyGet<JsonInvestdetail.Info> {
    public String access_token,id;
    public String show_type;


    public JsonInvestdetail(String access_token,String id, String show_type, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.id = id;
        this.show_type = show_type;
    }

    @Override
    protected Info parser(JSONObject object) {
        Log.d(object.toString(),"----------------------------------");
        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            Info.Borrowinfo borrowinfo = new Info.Borrowinfo();
            JSONObject jsonObject = object.optJSONObject("borrowinfo");
            borrowinfo.id = jsonObject.optString("id");
            borrowinfo.borrow_name = jsonObject.optString("borrow_name");
            borrowinfo.borrow_money = jsonObject.optString("borrow_money");
            borrowinfo.borrow_interest_rate = jsonObject.optString("borrow_interest_rate");
            borrowinfo.borrow_duration = jsonObject.optString("borrow_duration");
            borrowinfo.repayment_type = jsonObject.optString("repayment_type");
            borrowinfo.borrow_max = jsonObject.optString("borrow_max");
            borrowinfo.borrow_min = jsonObject.optString("borrow_min");
            borrowinfo.borrow_uid = jsonObject.optString("borrow_uid");
            borrowinfo.borrow_status = jsonObject.optString("borrow_status");
            borrowinfo.repayment_type_cn = jsonObject.optString("repayment_type_cn");
            borrowinfo.borrow_duration_cn = jsonObject.optString("borrow_duration_cn");
            borrowinfo.borrow_status_cn = jsonObject.optString("borrow_status_cn");
            borrowinfo.need = jsonObject.optString("need");
            borrowinfo.lefttime = jsonObject.optString("lefttime");
            borrowinfo.progress = jsonObject.optString("progress");
            borrowinfo.reward_type_cn = jsonObject.optString("reward_type_cn");
            info.borrowinfo = borrowinfo;
            JSONArray array = object.optJSONArray("invest_list");
            List<Info.InvestListContent> investList = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.InvestListContent listContent = new Info.InvestListContent();
                listContent.investor_capital = object1.optString("investor_capital");
                listContent.add_time = object1.optString("add_time");
                listContent.user_name = object1.optString("user_name");
                listContent.is_auto = object1.optString("is_auto");
                listContent.investor_interest = object1.optString("investor_interest");
                investList.add(listContent);
            }
            info.investList = investList;
            JSONArray array1 = object.optJSONArray("paying_list");
            List<Info.PayingListContent> payingList = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.PayingListContent listContent = new Info.PayingListContent();
                listContent.borrow_name = object1.optString("borrow_name");
                listContent.total = object1.optString("total");
                listContent.borrow_id = object1.optString("borrow_id");
                listContent.sort_order = object1.optString("sort_order");
                listContent.capital = object1.optString("capital");
                listContent.interest = object1.optString("interest");
                listContent.status = object1.optString("status");
                listContent.paid = object1.optString("paid");
                listContent.deadline = object1.optString("deadline");
                listContent.deadline_cn = object1.optString("deadline_cn");
                payingList.add(listContent);
            }
            info.payingList = payingList;

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

//        public List<ListContent> list;

        public Borrowinfo borrowinfo;
        public List<InvestListContent> investList;
        public List<PayingListContent> payingList;

        public static class Borrowinfo {


            /**
             * id : 30
             * borrow_name : 海赢抵押宝三
             * borrow_money : 30000.00
             * borrow_interest_rate : 11.00
             * borrow_duration : 3
             * repayment_type : 4
             * borrow_max : 0
             * borrow_min : 100
             * borrow_uid : 1403
             * borrow_status : 2
             * repayment_type_cn : 每月还息到期还本
             * borrow_duration_cn : 个月
             * borrow_status_cn : 立即投资
             * need : 25000.00
             * lefttime : 78237
             * progress : 16.66
             * reward_type_cn : 无
             */

            public String id;
            public String borrow_name;
            public String borrow_money;
            public String borrow_interest_rate;
            public String borrow_duration;
            public String repayment_type;
            public String borrow_max;
            public String borrow_min;
            public String borrow_uid;
            public String borrow_status;
            public String repayment_type_cn;
            public String borrow_duration_cn;
            public String borrow_status_cn;
            public String need;
            public String lefttime;
            public String progress;
            public String reward_type_cn;
        }

        public static class InvestListContent {
            public String investor_capital;
            public String add_time;
            public String user_name;
            public String is_auto;
            public String investor_interest;
        }

        public static class PayingListContent {
            public String borrow_name;
            public String total;
            public String borrow_id;
            public String sort_order;
            public String capital;
            public String interest;
            public String status;
            public String paid;
            public String deadline;
            public String deadline_cn;
        }

    }
}