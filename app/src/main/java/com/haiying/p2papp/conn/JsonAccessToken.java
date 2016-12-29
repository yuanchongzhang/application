package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;
import com.zcx.helper.util.UtilMD5;

import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 5/13/2016.
 */
@HttpInlet("index/token")
public class JsonAccessToken extends HYAsyGet<JsonAccessToken.Info> {

    public String client_id, grant_type, source_from, add_time, sign_info;

    public JsonAccessToken(String grant_type, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.client_id = "20160001";
        this.grant_type = grant_type;
        this.source_from = "1";
        this.add_time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        this.sign_info = (UtilMD5.MD5Encode(grant_type + this.client_id + this.add_time + "24SD%F4}S5", "UTF-8")).toUpperCase();
    }


    @Override
    protected Info parser(JSONObject object) {

        Info info = null;

        if (object.optString("status").equals("1")) {

            info = new Info();

            info.access_token = object.optString("access_token");


            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public String access_token;

    }

}

