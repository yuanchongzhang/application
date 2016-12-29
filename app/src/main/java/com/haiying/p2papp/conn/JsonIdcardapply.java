package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/11/2016.
 */
@HttpInlet("user/idcard_apply")
public class JsonIdcardapply extends HYAsyGet{
    public String access_token,uid,realname,idcard;

    public JsonIdcardapply(String access_token,String uid, String realname, String idcard,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.realname = realname;
        this.idcard = idcard;
    }

    @Override
    protected Object parser(JSONObject object) {
        if (object.optString("status").equals("1")) {

            TOAST = "实名认证申请成功！";

            return "";

        }

        TOAST = object.optString("tips");

        return null;
    }
}
