package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/26/2016.
 */
@HttpInlet("index/invest_pay_do")
public class JsonInvestpaydo extends HYAsyPost {
    public String access_token,uid, id, invest_money,is_experience,member_interest_rate_id,bonus_id,password;


    public JsonInvestpaydo(String access_token,String uid, String id, String invest_money, String is_experience, String member_interest_rate_id, String bonus_id, String password, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.id = id;
        this.invest_money = invest_money;
        this.is_experience = is_experience;
        this.member_interest_rate_id = member_interest_rate_id;
        this.bonus_id = bonus_id;
        this.password = password;
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