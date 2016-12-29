package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/10/2016.
 */
@HttpInlet("user/capital")
public class JsonCapital extends HYAsyGet<JsonCapital.Info>{
    public String access_token,uid, p, log_type,start_time,end_time;

    public JsonCapital(String access_token,String uid, String p, String log_type, String start_time, String end_time,AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.p = p;
        this.log_type = log_type;
        this.start_time = start_time;
        this.end_time = end_time;
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
            JSONArray array = object.optJSONArray("loglist");
            List<Info.ListContent> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.type = object1.optString("type");
                listContent.info = object1.optString("info");
                listContent.add_time = object1.optString("add_time");
                listContent.freeze_money = object1.optString("freeze_money");
                listContent.affect_money = object1.optString("affect_money");
                listContent.account_money = object1.optString("account_money");
                listContent.collect_money = object1.optString("collect_money");
                listContent.experience_money = object1.optString("experience_money");
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
            public String type;
            public String info;
            public String add_time;
            public String freeze_money;
            public String affect_money;
            public String account_money;
            public String collect_money;
            public String experience_money;
        }

    }
}
