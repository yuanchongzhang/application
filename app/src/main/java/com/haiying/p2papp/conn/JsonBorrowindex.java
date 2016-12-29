package com.haiying.p2papp.conn;

import android.util.Log;

import com.google.gson.Gson;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 4/27/2016.
 */
//@HttpInlet("index/borrow_index")
@HttpInlet("index/feedback")
public class JsonBorrowindex extends HYAsyGet<JsonBorrowindex.Info> {
    public String access_token;
    public JsonBorrowindex(String access_token,AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
    }

   /* @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {

            info = new Info();

            JSONArray array1 = object.optJSONArray("borrow_use");
            List<Info.ListContent> borrow_use = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrow_use.add(listContent);
            }
            info.borrow_use = borrow_use;



            TOAST = object.optString("tips");
            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }
*/

    @Override
    protected Info parser(JSONObject object) {
/*
        Info info = null;
        if (object.optString("status").equals("1")) {

            info = new Info();


            JSONArray array1 = object.optJSONArray("borrow_use");
            List<Info.ListContent> borrow_use = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrow_use.add(listContent);
            }
            info.borrow_use = borrow_use;




            TOAST = object.optString("tips");
            return info;

        }

        TOAST = object.optString("tips");

        return null;*/
        Gson gson = new Gson();
        Info info = gson.fromJson(object.toString(), Info.class);
        Log.d(object.toString(),"9999999999999");
        return info;

    }


    public static class Info {


        public List<ListContent> sex;
        public List<ListContent> borrow_use;

        public List<ListContent> borrow_money;
        public List<ListContent> borrow_duration;


        public static class ListContent {
            public String id;
            public String name;
        }
    }
}
