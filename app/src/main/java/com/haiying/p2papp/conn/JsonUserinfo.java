package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/7/2016.
 */
@HttpInlet("user/userinfo")
public class JsonUserinfo extends HYAsyGet<JsonUserinfo.Info> {

    public String access_token,uid;

    public JsonUserinfo(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;

        if (object.optString("status").equals("1")) {
            JSONObject jsonObject = object.optJSONObject("minfo");

            info = new Info();

            info.id = jsonObject.optString("id");
            info.user_name = jsonObject.optString("user_name");
            info.user_email = jsonObject.optString("user_email");
            info.user_phone = jsonObject.optString("user_phone");
            info.sex = jsonObject.optString("sex");
            info.real_name = jsonObject.optString("real_name");
            info.idcard = jsonObject.optString("idcard");
            info.money_freeze = jsonObject.optString("money_freeze");
            info.money_collect = jsonObject.optString("money_collect");
            info.account_money = jsonObject.optString("account_money");
            info.money_experience = jsonObject.optString("money_experience");
            info.all_money = jsonObject.optString("all_money");
            info.userpic = jsonObject.optString("userpic");
            info.pin_pass = jsonObject.optString("pin_pass");
            info.id_status = jsonObject.optString("id_status");
            info.vip_status = jsonObject.optString("vip_status");
            info.phone_status = jsonObject.optString("phone_status");
            info.email_status = jsonObject.optString("email_status");

            info.nick_name=jsonObject.optString("nick_name");
            info.time_limit=jsonObject.optString("time_limit");

            info.incode=jsonObject.optString("incode");
            TOAST = object.optString("tips");
            return info;

        }

        TOAST = object.optString("tips");

        Log.d(object.toString(),"8888888888888888888888888888888888888");

        return null;
    }

    public static class Info {

        /**
         * id : 1324
         * user_name : 18234089407
         * user_email :
         * user_phone : 18234089407
         * sex : 0
         * real_name :
         * idcard :
         * money_freeze : 0.00
         * money_collect : 0.00
         * account_money : 0.00
         * money_experience : 0.00
         * all_money : 0.00
         * pin_pass : 0
         * userpic : http://www.haiying365.com/Style/header/images/noavatar_big.gif
         */

        public String id;
        public String user_name;
        public String user_email;
        public String user_phone;
        public String sex;
        public String real_name;
        public String idcard;
        public String money_freeze;
        public String money_collect;
        public String account_money;
        public String money_experience;
        public String all_money;
        public String userpic;
        public String pin_pass;
        public String id_status;
        public String vip_status;
        public String phone_status;
        public String email_status;
        public String  nick_name;

        public String time_limit;

        public String incode;

    }
}
