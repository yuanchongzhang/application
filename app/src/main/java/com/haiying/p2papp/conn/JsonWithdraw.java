package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/26/2016.
 */
@HttpInlet("user/withdraw")
public class JsonWithdraw  extends HYAsyGet<JsonWithdraw.Info> {
    public String access_token,uid, withdraw_money, passwd, bank_id;

    public JsonWithdraw(String access_token,String uid, String withdraw_money, String passwd, String bank_id,AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
        this.withdraw_money = withdraw_money;
        this.passwd = passwd;
        this.bank_id = bank_id;
    }


    @Override
    protected Info parser(JSONObject object) {
        Info info = null;
        if (object.optString("status").equals("1")) {

            info = new Info();
            info.tips = object.optString("tips");
            info.account_money = object.optString("account_money");
            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }


    public static class Info {
        public String tips;
        public String account_money;
    }
}