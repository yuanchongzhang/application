package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/13/2016.
 */
@HttpInlet("user/vip_apply")
public class JsonVipapply extends HYAsyGet{
    public String access_token,uid,customer_id,des;


    public JsonVipapply(String access_token,String uid, String customer_id, String des,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.customer_id = customer_id;
        this.des = des;
    }


    @Override
    protected Object parser(JSONObject object) {

        if (object.optString("status").equals("1")) {

            TOAST = object.optString("tips");

            return "";

        }

        TOAST = object.optString("tips");

        return null;
    }
}
