package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/28/2016.
 */
@HttpInlet("user/setavatar")
public class JsonSetavatar extends HYAsyPost<JsonSetavatar.Info> {

    public String access_token,uid, avatar_base64;


    public JsonSetavatar(String access_token,String uid, String avatar_base64, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.avatar_base64 = avatar_base64;
    }


    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
//        System.out.println("object:"+object.toString());

        if (object.optString("status").equals("1")) {

            info = new Info();

            info.avatar = object.optString("avatar");


            TOAST = "上传成功";

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public String avatar;

    }

}
