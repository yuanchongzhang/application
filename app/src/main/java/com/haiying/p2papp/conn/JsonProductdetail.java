package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/15/2016.
 */
@HttpInlet("index/product_detail")
public class JsonProductdetail extends HYAsyGet<JsonProductdetail.Info> {
    public String access_token,id;
    public String show_type;


    public JsonProductdetail(String access_token,String id, String show_type, AsyCallBack<Info> asyCallBack) {
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
            borrowinfo.borrow_money = jsonObject.optString("borrow_money");
            borrowinfo.borrow_interest_rate = jsonObject.optString("borrow_interest_rate");
            borrowinfo.borrow_duration = jsonObject.optString("borrow_duration");
            borrowinfo.has_borrow = jsonObject.optString("has_borrow");
            borrowinfo.borrow_status = jsonObject.optString("borrow_status");
            borrowinfo.repayment_type_cn = jsonObject.optString("repayment_type_cn");
            borrowinfo.borrow_duration_cn = jsonObject.optString("borrow_duration_cn");
            borrowinfo.borrow_status_cn = jsonObject.optString("borrow_status_cn");
            borrowinfo.progress = jsonObject.optString("progress");
            borrowinfo.deadline = jsonObject.optString("deadline");
            borrowinfo.interest_type = jsonObject.optString("interest_type");
            info.borrowinfo = borrowinfo;
            JSONArray array = object.optJSONArray("invest_list");
            List<JsonInvestdetail.Info.InvestListContent> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                JsonInvestdetail.Info.InvestListContent listContent = new JsonInvestdetail.Info.InvestListContent();
                listContent.investor_capital = object1.optString("investor_capital");
                listContent.add_time = object1.optString("add_time");
                listContent.user_name = object1.optString("user_name");
                listContent.is_auto = object1.optString("is_auto");
                listContent.investor_interest = object1.optString("investor_interest");
                list.add(listContent);
            }
            info.list = list;

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public List<JsonInvestdetail.Info.InvestListContent> list;

        public Borrowinfo borrowinfo;

        public static class Borrowinfo {

            /**
             * id : 3
             * borrow_name : 海赢慧宝定期理财二
             * borrow_money : 80000.00
             * borrow_interest_rate : 8.00
             * borrow_duration : 1
             * has_borrow : 53650.00
             * borrow_status : 2
             * repayment_type_cn : 一次付清
             * borrow_duration_cn : 个月
             * borrow_status_cn : 立即投资
             * progress : 67.06
             * deadline : 1个月后
             * interest_type : 即投即起息
             */

            public String id;
            public String borrow_name;
            public String borrow_money;
            public String borrow_interest_rate;
            public String borrow_duration;
            public String has_borrow;
            public String borrow_status;
            public String repayment_type_cn;
            public String borrow_duration_cn;
            public String borrow_status_cn;
            public String progress;
            public String deadline;
            public String interest_type;
        }

    }
}
