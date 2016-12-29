package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/19/2016.
 */
@HttpInlet("index/index")
public class JsonIndex extends HYAsyGet<JsonIndex.Info> {
    public String access_token;

    public JsonIndex(String access_token, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            info.news_id = object.optString("news_id");
            JSONArray array = object.optJSONArray("banner");
            List<Info.BannerListContent> bannerList = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.BannerListContent listContent = new Info.BannerListContent();
                listContent.img = object1.optString("img");
                listContent.url = object1.optString("url");
                listContent.title = object1.optString("title");
                bannerList.add(listContent);
            }
            info.bannerList = bannerList;
            JSONArray array1 = object.optJSONArray("menuList");
            List<Info.MenuListContent> menuList = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.MenuListContent listContent = new Info.MenuListContent();
                listContent.title = object1.optString("title");
                listContent.desc = object1.optString("desc");
                menuList.add(listContent);
            }
            info.menuList = menuList;
            TOAST = object.optString("tips");
            return info;
        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {
        public String news_id;
        public List<BannerListContent> bannerList;
        public List<MenuListContent> menuList;


        public static class BannerListContent {
            public String img;
            public String url;
            public String title;
        }


        public static class MenuListContent {
            public String title;
            public String desc;
        }

    }
}
