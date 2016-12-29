package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/12/2016.
 */
@HttpInlet("user/tendindex")
public class JsonTendindex extends HYAsyGet<JsonTendindex.Info> {

    public String access_token,uid;

    public JsonTendindex(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;

        if (object.optString("status").equals("1")) {
            info = new Info();

            info.total_tending = object.optString("total_tending");
            info.total_tendbacking = object.optString("total_tendbacking");
            info.total_tenddone = object.optString("total_tenddone");
            info.total_tendbreak = object.optString("total_tendbreak");
            info.total_all = object.optString("total_all");

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        /**
         * total_tending : 0.00
         * total_tendbacking : 0.00
         * total_tenddone : 0.00
         * total_tendbreak : 0.00
         * total_all : 0.00
         */

        public String total_tending;
        public String total_tendbacking;
        public String total_tenddone;
        public String total_tendbreak;
        public String total_all;
    }
}
