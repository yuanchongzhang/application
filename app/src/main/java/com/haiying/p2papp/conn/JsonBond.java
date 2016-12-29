package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/25/2016.
 */
@HttpInlet("index/bond")
public class JsonBond extends HYAsyGet<JsonBond.Info> {

    public String access_token, p;
    public String borrow_type;
    public String borrow_interest_rate;
    public String borrow_duration;
    // public String borrow_status;
    public String borrow_money;

    //    public JsonBond(String access_token,String p, String borrow_type, String borrow_interest_rate, String borrow_duration, String borrow_status, AsyCallBack<Info> asyCallBack) {
    public JsonBond(String access_token, String p, String borrow_type, String borrow_interest_rate, String borrow_money, String borrow_duration, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.p = p;
        this.borrow_type = borrow_type;
        this.borrow_interest_rate = borrow_interest_rate;
        this.borrow_money = borrow_money;
        this.borrow_duration = borrow_duration;
        //  this.borrow_status = borrow_status;
    }

    @Override
    protected Info parser(JSONObject object) {
        Log.d(object.toString(), "==============================");
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
                listContent.transfer_price = object1.optString("transfer_price");
                listContent.money = object1.optString("money");
                listContent.total_period = object1.optString("total_period");
                listContent.period = object1.optString("period");
                listContent.borrow_name = object1.optString("borrow_name");
                listContent.borrow_interest_rate = object1.optString("borrow_interest_rate");
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
            public String transfer_price;
            public String money;
            public String total_period;
            public String period;
            public String borrow_name;
            public String borrow_interest_rate;
        }

    }
}
