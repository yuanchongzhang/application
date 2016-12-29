package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;
import com.zcx.helper.util.UtilMD5;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/5/2016.
 */
@HttpInlet("common/login")
public class JsonLogin extends HYAsyGet<JsonLogin.Info> {

    public String access_token,txtUsername, txtPwd;


    public JsonLogin(String access_token,String txtUsername, String txtPwd, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.txtUsername = txtUsername;
        this.txtPwd = UtilMD5.MD5Encode(txtPwd, "UTF-8");
    }


    @Override
    protected Info parser(JSONObject object) {

        Info info = null;

        if (object.optString("status").equals("1")) {

            info = new Info();

            info.uid = object.optString("uid");

            info.user_name = object.optString("user_name");

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public String uid, user_name;

    }

}
