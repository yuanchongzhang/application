package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/27/2016.
 */
@HttpInlet("index/product_pay_do")
public class JsonProductpaydo extends HYAsyGet {
    public String access_token,uid, id, pin_pass, invest_money;


    public JsonProductpaydo(String access_token,String uid, String id, String pin_pass, String invest_money, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.id = id;
        this.pin_pass = pin_pass;
        this.invest_money = invest_money;
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