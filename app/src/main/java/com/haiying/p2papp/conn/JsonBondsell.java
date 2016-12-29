package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/26/2016.
 */
//@HttpInlet("user/bond_sell")
@HttpInlet("user/bond_sell")
public class JsonBondsell extends HYAsyPost {
    //    public String access_token,uid, money, id, paypass;
//    public String access_token, uid, id, borrow_name, bond_max, bond_fee_rate, paypass;
    public String access_token, uid, money, id, paypass;

    public JsonBondsell(String access_token, String uid, String money,String id, String paypass, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.money = money;
        this.id = id;

        this.paypass = paypass;
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