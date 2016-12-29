package com.haiying.p2papp.conn;

import android.util.Log;

import com.google.gson.Gson;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/27/2016.
 */
@HttpInlet("index/feedback")
public class JsonBorrowReleaseindex extends HYAsyGet<JsonBorrowReleaseindex.Info> {
    public String access_token;
    public JsonBorrowReleaseindex(String access_token, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
    }

  /*  @Override
    protected Info parser(JSONObject object) {
       // return super.parser(object);
   *//*  Info info=new Info();

        Gson gson=new Gson();
        info=gson.fromJson(object.toString(),Info.class);
        return info;*//*
        Gson gson = new Gson();
        Info info = gson.fromJson(object.toString(), Info.class);
        Log.d(object.toString(),"9999999999999");
        return info;

    }*/

    @Override
    protected Info parser(JSONObject object) {


        Gson gson = new Gson();
        Info info = gson.fromJson(object.toString(), Info.class);
        Log.d(object.toString(),"9999999999999");
        return info;
        //return super.parser(object);
    }

    public static class Info {

        public String sex;

        public String borrow_use;

        public String borrow_money;

        public String borrow_duration;



    }
}
