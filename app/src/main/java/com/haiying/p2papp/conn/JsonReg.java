package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/5/2016.
 */
@HttpInlet("common/reg")
public class JsonReg extends HYAsyGet {

    public String access_token, txtUsername, txtPwd, txtCode, codeId, txtIncode;


    public JsonReg(String access_token, String txtUsername, String txtPwd, String txtCode, String codeId, String txtIncode, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.txtPwd = txtPwd;
        this.txtCode = txtCode;
        this.codeId = codeId;
        this.txtIncode = txtIncode;
        this.txtUsername = txtUsername;
    }


    @Override
    protected Object parser(JSONObject object) {

        if (object.optString("status").equals("1")) {

            TOAST = "注册成功";

            return "";

        }

        return null;
    }

}
