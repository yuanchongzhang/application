package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/22/2016.
 */
@HttpInlet("user/banksave")
public class JsonBanksave extends HYAsyGet {
    public String access_token,uid, bank_num, bank_name, bank_address;


    public JsonBanksave(String access_token,String uid, String bank_num, String bank_name, String bank_address, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.uid = uid;
        this.bank_num = bank_num;
        this.bank_name = bank_name;
        this.bank_address = bank_address;

    }


    @Override
    protected Object parser(JSONObject object) {

        if (object.optString("status").equals("1")) {

            TOAST = "添加成功！";

            return "";

        }

        TOAST = object.optString("tips");

        return null;
    }
}