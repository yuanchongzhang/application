package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/7/2016.
 */
@HttpInlet("common/findpass")
public class JsonFindpass extends HYAsyGet{
    public String access_token,txtPhone,txtCode,codeId,txtPassword;

    public JsonFindpass(String access_token,String txtPhone, String txtCode, String codeId, String txtPassword,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.txtPhone = txtPhone;
        this.txtCode = txtCode;
        this.codeId = codeId;
        this.txtPassword = txtPassword;
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
