package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/11/2016.
 */
@HttpInlet("user/moneylimit_apply")
public class JsonMoneylimitapply extends HYAsyGet{
    public String access_token,uid,apply_type,apply_money,apply_info;

    public JsonMoneylimitapply(String access_token,String uid, String apply_type, String apply_money,String apply_info,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.apply_type = apply_type;
        this.apply_money = apply_money;
        this.apply_info = apply_info;
    }

    @Override
    protected Object parser(JSONObject object) {
        if (object.optString("status").equals("1")) {

            TOAST = "提交成功！";

            return "";

        }

        TOAST = object.optString("tips");

        return null;
    }
}
