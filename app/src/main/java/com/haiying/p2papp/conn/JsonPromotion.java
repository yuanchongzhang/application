package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/12/2016.
 */
@HttpInlet("user/promotion")
public class JsonPromotion extends HYAsyGet<JsonPromotion.Info>{
    public String access_token,uid;

    public JsonPromotion(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;

        if (object.optString("status").equals("1")) {

            info = new Info();

            info.promotion_desc = object.optString("promotion_desc");
            info.promotion_link = object.optString("promotion_link");
            info.promotion_award = object.optString("promotion_award");
            TOAST = object.optString("tips");

            Log.d(info.promotion_desc+info.promotion_award,"dasjkllllllllllllllllllf");
            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public String promotion_desc;
        public String promotion_award;

        public String promotion_link;

    }
}
