package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/11/2016.
 */
@HttpInlet("user/setnick_name")
public class JsonChangenickname extends HYAsyGet {
    public String access_token, uid, nick_name;

    public JsonChangenickname(String access_token, String uid, String nick_name, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.nick_name = nick_name;
    }

    @Override
    protected Object parser(JSONObject object) {
        if (object.optString("status").equals("1")) {

            TOAST = "修改成功！";

            return "";

        }

        TOAST = object.optString("tips");

        return null;
    }
}
