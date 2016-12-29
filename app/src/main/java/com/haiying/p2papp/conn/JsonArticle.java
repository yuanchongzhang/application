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
@HttpInlet("index/article")
public class JsonArticle extends HYAsyGet<JsonArticle.Info> {

    public String access_token;

    public JsonArticle(String access_token, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            JSONArray array = object.optJSONArray("list");
            List<Info.ListContent> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.type_name = object1.optString("type_name");
                listContent.type_set = object1.optString("type_set");
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
            public String type_name;
            public String type_set;
        }

    }
}
