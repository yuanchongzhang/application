package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/20/2016.
 */
@HttpInlet("index/article_list")
public class JsonArticlelist extends HYAsyGet<JsonArticlelist.Info> {
    public String access_token,id, p;

    public JsonArticlelist(String access_token, String id, String p, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.id = id;
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
            List<JsonMsglist.Info.ListContent> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                JsonMsglist.Info.ListContent listContent = new JsonMsglist.Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.title = object1.optString("title");
                listContent.send_time = object1.optString("add_time");
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

        public List<JsonMsglist.Info.ListContent> list;

        public Page page;


        public static class Page {
            public int nowPage;
            public int totalPages;
            public int pageSize;
            public int totalRows;
        }

    }
}
