package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/13/2016.
 */
@HttpInlet("user/productout")
public class JsonProductout extends HYAsyGet<JsonProductout.Info> {
    public String access_token,uid, p,show_type, start_time, end_time;

    public JsonProductout(String access_token,String uid, String p, String show_type, String start_time, String end_time, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.p = p;
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
                listContent.id = object1.optString("id");
                listContent.borrow_id = object1.optString("borrow_id");
                listContent.invest_time_cn = object1.optString("invest_time_cn");
                listContent.borrow_user = object1.optString("borrow_user");
                listContent.borrow_duration_cn = object1.optString("borrow_duration_cn");
                listContent.borrow_interest_rate = object1.optString("borrow_interest_rate");
                listContent.experience_money = object1.optString("experience_money");
                listContent.back = object1.optString("back");
                listContent.total = object1.optString("total");
                listContent.receive_money = object1.optString("receive_money");
                listContent.repayment_time_cn = object1.optString("repayment_time_cn");
                listContent.investor_capital = object1.optString("investor_capital");
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

        public String total_money;
        public String total_num;


        public List<ListContent> list;

        public Page page;


        public static class Page {
            public int nowPage;
            public int totalPages;
            public int pageSize;
            public int totalRows;
        }

        public static class ListContent {
            public String id;
            public String borrow_id;
            public String invest_time_cn;
            public String borrow_user;
            public String borrow_duration_cn;
            public String borrow_interest_rate;
            public String experience_money;
            public String back;
            public String total;
            public String receive_money;
            public String repayment_time_cn;
            public String investor_capital;
            public String investor_interest;
        }

    }
}
