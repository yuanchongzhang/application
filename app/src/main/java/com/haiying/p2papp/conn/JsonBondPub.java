package com.haiying.p2papp.conn;

import android.util.Log;

import com.google.gson.Gson;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

/**
 * Created by Administrator on 4/26/2016.
 */
@HttpInlet("user/bond_pub")
public class JsonBondPub extends HYAsyPost<JsonBondPub.Info> {
    public String uid;
    public String id;

    public JsonBondPub(String uid, String id, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.uid=uid;
        this.id=id;
    }

    @Override
    protected Info parser(JSONObject object) {
        //  return super.parser(object);

       /* Info info = null;
        Gson gson=new Gson();
        if (object.optString("status").equals("1")) {
            info = new Info();

        }

        return info;*/

        Gson gson = new Gson();
        Info info = gson.fromJson(object.toString(), Info.class);

        Log.d(object.toString(),"9999999999999");

        return info;


    }

    public static class Info {
        public String borrow_name;
        public String bond_max;

        public String bond_fee_rate;
        public String id;
    }

}