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
@HttpInlet("user/capital_config")
public class JsonCapitalconfig extends HYAsyGet<JsonCapitalconfig.Info> {
    public String access_token,uid;

    public JsonCapitalconfig(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            JSONArray array = object.optJSONArray("logtype");
            List<Info.logtype> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.logtype logtype = new Info.logtype();
                logtype.id = object1.optString("id");
                logtype.name = object1.optString("name");
                list.add(logtype);
            }
            info.list = list;

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {
        public List<logtype> list;

        public static class logtype {
            public String id;
            public String name;
        }
    }


}
