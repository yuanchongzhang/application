package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/11/2016.
 */
@HttpInlet("user/change_pinpass")
public class JsonChangepinpass extends HYAsyGet{
    public String access_token,uid,oldpassword,newpassword,newpasswordre;

    public JsonChangepinpass(String access_token,String uid, String oldpassword, String newpassword,String newpasswordre,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
        this.newpasswordre = newpasswordre;
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
