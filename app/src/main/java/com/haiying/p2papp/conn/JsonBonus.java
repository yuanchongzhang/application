package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/8/2016.
 */
@HttpInlet("user/bonus")
public class JsonBonus extends HYAsyGet<JsonBonus.Info> {
    public String access_token,uid, p;

    public JsonBonus(String access_token,String uid, String p, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.p = p;
    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;

        if (object.optString("status").equals("1")) {
            info = new Info();
            info.canUse = object.optString("canUse");
            info.bonus_url = object.optString("bonus_url");
            Info.Page page = new Info.Page();
            JSONObject jsonObject = object.optJSONObject("page");
            page.nowPage = Integer.parseInt(jsonObject.optString("nowPage"));
            page.totalPages = Integer.parseInt(jsonObject.optString("totalPages"));
            page.pageSize = Integer.parseInt(jsonObject.optString("pageSize"));
            page.totalRows = Integer.parseInt(jsonObject.optString("totalRows"));
            info.page = page;
            JSONArray array = object.optJSONArray("list");
            List<Info.ListContent> list = new ArrayList();
            for (int i = 0; i <array.length() ; i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.ListContent listContent=new Info.ListContent();
                listContent.money_bonus = object1.optString("money_bonus");
                listContent.bonus_invest_min = object1.optString("bonus_invest_min");
                listContent.start_time = object1.optString("start_time");
                listContent.end_time = object1.optString("end_time");
                listContent.status = object1.optString("status");
                listContent.status_cn = object1.optString("status_cn");
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
        public String canUse;
        public String bonus_url;

        public List<ListContent> list;

        public Page page;


        public static class Page {
            public int nowPage;
            public int totalPages;
            public int pageSize;
            public int totalRows;
        }

        public static class ListContent {
            public String money_bonus;
            public String bonus_invest_min;
            public String start_time;
            public String end_time;
            public String status;
            public String status_cn;
        }

    }
}
