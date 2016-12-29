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
@HttpInlet("user/charge_log")
public class JsonChargelog extends HYAsyGet<JsonChargelog.Info>{
    public String access_token,uid, p;

    public JsonChargelog(String access_token,String uid, String p, AsyCallBack<Info> asyCallBack) {
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
                listContent.money = object1.optString("money");
                listContent.add_time = object1.optString("add_time");
                listContent.status = object1.optString("status");
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
            public String money;
            public String add_time;
            public String status;
        }

    }
}
