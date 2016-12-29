package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/22/2016.
 */
@HttpInlet("user/bankdel")
public class JsonBankdel extends HYAsyGet{
    public String access_token,uid,id;


    public JsonBankdel(String access_token,String uid, String id,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.id = id;
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
