package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/25/2016.
 */
@HttpInlet("index/bond_detail")
public class JsonBonddetail extends HYAsyGet<JsonBonddetail.Info> {
    public String access_token,id;
    public String show_type;



    public JsonBonddetail(String access_token,String id, String show_type, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.id = id;
        this.show_type = show_type;
    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            Info.Borrowinfo borrowinfo = new Info.Borrowinfo();
            JSONObject jsonObject = object.optJSONObject("borrowinfo");
            borrowinfo.id = jsonObject.optString("id");
            borrowinfo.borrow_name = jsonObject.optString("borrow_name");
            borrowinfo.borrow_uid = jsonObject.optString("borrow_uid");
            borrowinfo.borrow_duration = jsonObject.optString("borrow_duration");
            borrowinfo.borrow_interest_rate = jsonObject.optString("borrow_interest_rate");
            borrowinfo.borrow_max = jsonObject.optString("borrow_max");
            borrowinfo.repayment_type_cn = jsonObject.optString("repayment_type_cn");
            borrowinfo.borrow_duration_cn = jsonObject.optString("borrow_duration_cn");
            borrowinfo.transfer_price = jsonObject.optString("transfer_price");
            borrowinfo.period = jsonObject.optString("period");
            borrowinfo.status = jsonObject.optString("status");
            borrowinfo.investor_capital = jsonObject.optString("investor_capital");
            borrowinfo.investor_interest = jsonObject.optString("investor_interest");
            borrowinfo.status_cn = jsonObject.optString("status_cn");
            borrowinfo.investor_all = jsonObject.optString("investor_all");
            info.borrowinfo = borrowinfo;
            JSONArray array = object.optJSONArray("invest_list");
            List<JsonInvestdetail.Info.InvestListContent> investList = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                JsonInvestdetail.Info.InvestListContent listContent = new JsonInvestdetail.Info.InvestListContent();
                listContent.investor_capital = object1.optString("investor_capital");
                listContent.add_time = object1.optString("add_time");
                listContent.user_name = object1.optString("user_name");
                listContent.is_auto = object1.optString("is_auto");
                listContent.investor_interest = object1.optString("investor_interest");
                investList.add(listContent);
            }
            info.investList = investList;
            JSONArray array1 = object.optJSONArray("paying_list");
            List<JsonInvestdetail.Info.PayingListContent> payingList = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                JsonInvestdetail.Info.PayingListContent listContent = new JsonInvestdetail.Info.PayingListContent();
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
        public List<JsonInvestdetail.Info.InvestListContent> investList;
        public List<JsonInvestdetail.Info.PayingListContent> payingList;

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
            public String borrow_uid;
            public String borrow_duration;
            public String borrow_interest_rate;
            public String borrow_max;
            public String repayment_type_cn;
            public String borrow_duration_cn;
            public String transfer_price;
            public String period;
            public String status;
            public String investor_capital;
            public String investor_interest;
            public String status_cn;
            public String investor_all;
        }

//        public static class InvestListContent {
//            public String investor_capital;
//            public String add_time;
//            public String user_name;
//            public String is_auto;
//            public String investor_interest;
//        }
//
//        public static class PayingListContent {
//            public String borrow_name;
//            public String total;
//            public String borrow_id;
//            public String sort_order;
//            public String capital;
//            public String interest;
//            public String status;
//            public String paid;
//            public String deadline;
//            public String deadline_cn;
//        }
       /**
        *{
        *    "borrowinfo": {
        *            "id": "1471",
        *            "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "borrow_uid": "1442",
        *            "borrow_duration": "6",
        *            "borrow_interest_rate": "10.00",
        *            "borrow_max": "不限",
        *            "repayment_type_cn": "按月等额本息",
        *            "borrow_duration_cn": "个月",
        *            "transfer_price": "1000.00",
        *            "period": "6",
        *            "status": "3",
        *            "investor_capital": "1111.00",
        *            "investor_interest": "32.60",
        *            "status_cn": "3",
        *            "investor_all": "143.60"
        *},
        *    "invest_list": [
        *    {
        *            "investor_capital": "377889.00",
        *            "add_time": "2016-04-19",
        *            "user_name": "testuser",
        *            "is_auto": "手动",
        *            "investor_interest": "11097.96"
        *    },
        *    {
        *        "investor_capital": "1000000.00",
        *            "add_time": "2016-04-19",
        *            "user_name": "testuser",
        *            "is_auto": "手动",
        *            "investor_interest": "29368.34"
        *    },
        *    {
        *        "investor_capital": "600000.00",
        *            "add_time": "2016-04-19",
        *            "user_name": "testuser",
        *            "is_auto": "手动",
        *            "investor_interest": "17621.04"
        *    },
        *    {
        *        "investor_capital": "20000.00",
        *            "add_time": "2016-04-19",
        *            "user_name": "testuser",
        *            "is_auto": "手动",
        *            "investor_interest": "587.38"
        *    },
        *    {
        *        "investor_capital": "1111.00",
        *            "add_time": "2016-04-19",
        *            "user_name": "testuser",
        *            "is_auto": "手动",
        *            "investor_interest": "32.60"
        *    },
        *    {
        *        "investor_capital": "1000.00",
        *            "add_time": "2016-04-19",
        *            "user_name": "testuser",
        *            "is_auto": "手动",
        *            "investor_interest": "29.36"
        *    }
        *    ],
        *    "paying_list": [
        *    {
        *            "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "total": "6",
        *            "borrow_id": "33",
        *            "sort_order": "1",
        *            "capital": "326456.11",
        *            "interest": "16666.64",
        *            "status": "还未还",
        *            "paid": "0.00",
        *            "deadline": "1463673599",
        *            "deadline_cn": "2016-05-19"
        *    },
        *    {
        *        "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "total": "6",
        *            "borrow_id": "33",
        *            "sort_order": "2",
        *            "capital": "329176.58",
        *            "interest": "13946.18",
        *            "status": "还未还",
        *            "paid": "0.00",
        *            "deadline": "1466351999",
        *            "deadline_cn": "2016-06-19"
        *    },
        *    {
        *        "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "total": "6",
        *            "borrow_id": "33",
        *            "sort_order": "3",
        *            "capital": "331919.72",
        *            "interest": "11203.05",
        *            "status": "还未还",
        *            "paid": "0.00",
        *            "deadline": "1468943999",
        *            "deadline_cn": "2016-07-19"
        *    },
        *    {
        *        "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "total": "6",
        *            "borrow_id": "33",
        *            "sort_order": "4",
        *            "capital": "334685.71",
        *            "interest": "8437.05",
        *            "status": "还未还",
        *            "paid": "0.00",
        *            "deadline": "1471622399",
        *            "deadline_cn": "2016-08-19"
        *    },
        *    {
        *        "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "total": "6",
        *            "borrow_id": "33",
        *            "sort_order": "5",
        *            "capital": "337474.77",
        *            "": "还未还",
        *            "painterest": "5648.01",
        *            "statusid": "0.00",
        *            "deadine": "1474300799",
        *            "deadlinle_cn": "2016-09-19"
        *    },
        *    {
        *        "borrow_name": "大型IT专业自营连锁零售企业应付账款转让项目（二期）",
        *            "total": "6",
        *            "borrow_id": "33",
        *            "sort_order": "6",
        *            "capital": "340287.11",
        *            "interest": "2835.75",
        *            "status": "还未还",
        *            "paid": "0.00",
        *            "deadline": "1476892799",
        *            "deadline_cn": "2016-10-19"
        *    }
        *    ],
        *    "status": "1"
        *}
        */
    }
}