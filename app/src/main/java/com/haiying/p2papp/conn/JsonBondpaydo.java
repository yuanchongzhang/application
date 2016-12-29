package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/26/2016.
 */
//@HttpInlet("index/bond_pay_do")bond_pub
@HttpInlet("index/bond_pay_do")
public class JsonBondpaydo extends HYAsyPost {
  //  public String access_token,uid, id, paypass,money;

    public String access_token,id,paypass, uid;
    public JsonBondpaydo(String access_token, String id,String uid,String paypass,AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
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