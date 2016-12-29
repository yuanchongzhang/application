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
@HttpInlet("user/bond")
public class JsonUserbond extends HYAsyGet<JsonUserbond.Info> {
    public String access_token,p;
    public String uid;
    public String show_type;


    public JsonUserbond(String access_token,String p, String uid, String show_type, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.p = p;
        this.uid = uid;
        this.show_type = show_type;
    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
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
                listContent.borrow_name = object1.optString("borrow_name");
                listContent.add_time = object1.optString("add_time");
                listContent.user_name = object1.optString("user_name");
                listContent.deadline = object1.optString("deadline");
                listContent.period = object1.optString("period");
                listContent.total_period = object1.optString("total_period");
                listContent.investor_capital = object1.optString("investor_capital");
                listContent.investor_interest = object1.optString("investor_interest");
                listContent.borrow_interest_rate = object1.optString("borrow_interest_rate");
                listContent.transfer_price = object1.optString("transfer_price");
                listContent.agreement_url = object1.optString("agreement_url");
                listContent.money = object1.optString("money");
                listContent.cancel_time = object1.optString("cancel_time");
                listContent.cancel_times = object1.optString("cancel_times");
                listContent.remark = object1.optString("remark");
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


        public Page page;

        public static class Page {
            public int nowPage;
            public int totalPages;
            public int pageSize;
            public int totalRows;
        }

        public List<ListContent> list;


        public static class ListContent {
            public String id;
            public String borrow_name;
            public String add_time;
            public String user_name;
            public String deadline;
            public String period;
            public String total_period;
            public String investor_capital;
            public String investor_interest;
            public String borrow_interest_rate;
            public String transfer_price;
            public String agreement_url;
            public String money;
            public String cancel_time;
            public String cancel_times;
            public String remark;
        }
    }
}