package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/11/2016.
 */
@HttpInlet("user/moneylimit")
public class JsonMoneylimit extends HYAsyGet<JsonMoneylimit.Info> {
    public String access_token,uid;

    public JsonMoneylimit(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {
        Log.d(object.toString(),"asdlkjjjjjjjjjjjjjjjjjjjjjf");

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            JSONArray array = object.optJSONArray("apply_type");
            List<Info.applyType> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.applyType applyType = new Info.applyType();
                applyType.id = object1.optString("id");
                applyType.name = object1.optString("name");
                list.add(applyType);
            }
            info.list = list;

            TOAST = object.optString("tips");

            return info;

        }else {
            TOAST = object.optString("tips");
        }



        return null;
    }

    public static class Info {
        public List<applyType> list;

        public static class applyType {
            public String id;
            public String name;
        }
    }


}