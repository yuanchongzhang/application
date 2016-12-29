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
@HttpInlet("user/vip")
public class JsonVip extends HYAsyGet<JsonVip.Info>{
    public String access_token,uid;

    public JsonVip(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
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
                listContent.real_name = object1.optString("real_name");
                listContent.qq = object1.optString("qq");
                listContent.phone = object1.optString("phone");
                listContent.avatar = object1.optString("avatar");
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


        public static class ListContent {
            public String id;
            public String real_name;
            public String qq;
            public String phone;
            public String avatar;
        }
    }
}
