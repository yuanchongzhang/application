package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/11/2016.
 */
@HttpInlet("user/change_mobile_do")
public class JsonChangemobiledo extends HYAsyGet{
    public String access_token,uid,txtCode,codeId,new_mobile;

    public JsonChangemobiledo(String access_token,String uid, String txtCode, String codeId,String new_mobile,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.txtCode = txtCode;
        this.codeId = codeId;
        this.new_mobile = new_mobile;
    }

    @Override
    protected Object parser(JSONObject object) {
        if (object.optString("status").equals("1")) {

            TOAST = "更换成功！";

            return "";

        }

        TOAST = object.optString("tips");

        return null;
    }
}
