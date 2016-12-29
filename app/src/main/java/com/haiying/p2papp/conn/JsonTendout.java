package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/26/2016.
 */
@HttpInlet("user/tendout")
public class JsonTendout extends HYAsyGet<JsonTendout.Info> {
    public String access_token,p;
    public String uid;
    public String show_type;
    public String start_time;
    public String end_time;


    public JsonTendout(String access_token,String p, String uid, String show_type,String start_time, String end_time, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.p = p;
        this.uid = uid;
        this.show_type = show_type;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            info.total_money = object.optString("total_money");
            info.total_num = object.optString("total_num");

            Info.Page page = new Info.Page();
            JSONObject jsonObject = object.optJSONObject("page");
            page.nowPage = Integer.parseInt(jsonObject.optString("nowPage"));
            page.totalPages = Integer.parseInt(jsonObject.optString("totalPages"));
            page.pageSize = Integer.parseInt(jsonObject.optString("pageSize"));
            page.totalRows = Integer.parseInt(jsonObject.optString("totalRows"));
            info.page = page;
            JSONArray array = object.optJSONArray("list");
            List<Info.ListContent> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.investor_all = object1.optString("investor_all");
                listContent.id = object1.optString("id");
                listContent.borrow_id = object1.optString("borrow_id");
                listContent.investor_capital = object1.optString("investor_capital");
                listContent.investor_interest = object1.optString("investor_interest");
                listContent.receive_capital = object1.optString("receive_capital");
                listContent.receive_interest = object1.optString("receive_interest");
                listContent.invest_time = object1.optString("invest_time");
                listContent.borrow_name = object1.optString("borrow_name");
                listContent.borrow_money = object1.optString("borrow_money");
                listContent.borrow_interest_rate = object1.optString("borrow_interest_rate");
                listContent.borrow_duration = object1.optString("borrow_duration");
                listContent.repayment_type = object1.optString("repayment_type");
                listContent.borrow_status = object1.optString("borrow_status");
                listContent.has_pay = object1.optString("has_pay");
                listContent.borrow_user = object1.optString("borrow_user");
                listContent.total = object1.optString("total");
                listContent.back = object1.optString("back");
                listContent.receive_money = object1.optString("receive_money");
                listContent.unreceive_money = object1.optString("unreceive_money");
                listContent.unreceive_interest = object1.optString("unreceive_interest");
                listContent.repayment_time_cn = object1.optString("repayment_time_cn");
                listContent.repayment_time = object1.optString("repayment_time");
                listContent.borrow_duration_cn = object1.optString("borrow_duration_cn");
                listContent.invest_time_cn = object1.optString("invest_time_cn");
                listContent.breakday = object1.optString("breakday");
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


        public String total_money;
        public String total_num;

        public Page page;


        public static class Page {
            public int nowPage;
            public int totalPages;
            public int pageSize;
            public int totalRows;
        }

        public List<ListContent> list;


        public static class ListContent {
            public String investor_all;
            public String id;
            public String borrow_id;
            public String investor_capital;
            public String investor_interest;
            public String receive_capital;
            public String receive_interest;
            public String invest_time;
            public String borrow_name;
            public String borrow_money;
            public String borrow_interest_rate;
            public String borrow_duration;
            public String repayment_type;
            public String borrow_status;
            public String has_pay;
            public String borrow_user;
            public String total;
            public String back;
            public String receive_money;
            public String unreceive_money;
            public String unreceive_interest;
            public String repayment_time_cn;
            public String repayment_time;
            public String borrow_duration_cn;
            public String invest_time_cn;
            public String breakday;
        }
    }
}