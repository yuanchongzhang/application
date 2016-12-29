package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/6/2016.
 */
@HttpInlet("common/sendcode")
public class JsonSendcode extends HYAsyPost<JsonSendcode.Info> {
    public String access_token,txtPhone, act;



    public JsonSendcode(String access_token,String txtPhone, String act, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.txtPhone = txtPhone;
        this.act = act;
    }

    @Override
    protected Info parser(JSONObject object) {
        Log.d(object.toString(),"asdlkjjjjjjjjjjf");
        Info info = null;

        if (object.optString("status").equals("1")) {

            info = new Info();

            info.codeId = object.optString("codeId");
            info.content = object.optString("content");
            TOAST = object.optString("tips");




            return info;

        }

        TOAST = object.optString("tips");



        return null;
    }

    public static class Info {

        public String codeId;
        public String  content;
    }
}
