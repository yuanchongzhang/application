package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/20/2016.
 */
@HttpInlet("user/msgview")
public class JsonMsgview extends HYAsyGet<JsonMsgview.Info> {

    public String access_token,uid;
    public String id;

    public JsonMsgview(String access_token,String uid, String id, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.id = id;

    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;

        if (object.optString("status").equals("1")) {
            info = new Info();
            info.title = object.optString("title");
            info.inner_msg = object.optString("inner_msg");
            TOAST = object.optString("tips");
            return info;
        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {
        public String title;
        public String inner_msg;
    }
}