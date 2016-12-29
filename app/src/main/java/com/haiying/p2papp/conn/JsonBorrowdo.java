package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/28/2016.
 */
//@HttpInlet("index/borrow_do")feedback_do
@HttpInlet("index/feedback_do")

public class JsonBorrowdo extends HYAsyPost {

    public String access_token;
    public String user_name;
    public String sex;


    public String user_phone;
    public String borrow_money;

    public String borrow_use;

    public String borrow_duration;

    public String borrow_profession;
    public String borrow_info;

    public JsonBorrowdo(String access_token, String user_name, String sex, String user_phone, String borrow_money, String borrow_use, String borrow_duration, String borrow_profession, String borrow_info, AsyCallBack asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.user_name = user_name;
        this.sex = sex;
        this.user_phone = user_phone;
        this.borrow_money = borrow_money;
        this.borrow_use = borrow_use;
        this.borrow_duration = borrow_duration;
        this.borrow_profession = borrow_profession;

        this.borrow_info = borrow_info;
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