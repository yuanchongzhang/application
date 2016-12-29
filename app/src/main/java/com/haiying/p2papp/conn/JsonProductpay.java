package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/27/2016.
 */
@HttpInlet("index/product_pay")
public class JsonProductpay extends HYAsyGet<JsonProductpay.Info> {

    public String access_token,id;
    public String uid;


    public JsonProductpay(String access_token,String id, String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.id = id;
        this.uid = uid;


    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            info.account_money = object.optString("account_money");
            info.invest_range = object.optString("invest_range");
            TOAST = object.optString("tips");
            return info;
        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {
        public String account_money;
        public String invest_range;
    }
}