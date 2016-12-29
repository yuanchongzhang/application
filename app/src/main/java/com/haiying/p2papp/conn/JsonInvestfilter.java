package com.haiying.p2papp.conn;

import android.util.Log;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/14/2016.
 */
@HttpInlet("index/invest_filter")
public class JsonInvestfilter extends HYAsyGet<JsonInvestfilter.Info> {
    public String access_token;

    public JsonInvestfilter(String access_token, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
    }

    @Override
    protected Info parser(JSONObject object) {

        Log.d(object.toString(), "666666666666666666666666666666666");
        Info info = null;


        if (object.optString("status").equals("1")) {
            info = new Info();
            JSONArray array = object.optJSONArray("borrow_type");
            List<Info.ListContent> borrowTypeList = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrowTypeList.add(listContent);
            }
            info.borrowTypeList = borrowTypeList;
            JSONArray array1 = object.optJSONArray("borrow_interest_rate");
            List<Info.ListContent> borrowInterestRateList = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrowInterestRateList.add(listContent);
            }
            info.borrowInterestRateList = borrowInterestRateList;
            JSONArray array2 = object.optJSONArray("borrow_duration");
            List<Info.ListContent> borrowDurationList = new ArrayList();
            for (int i = 0; i < array2.length(); i++) {
                JSONObject object1 = array2.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrowDurationList.add(listContent);
            }
            info.borrowDurationList = borrowDurationList;

            JSONArray array3 = object.optJSONArray("borrow_status");
            List<Info.ListContent> borrowStatusList = new ArrayList();
            for (int i = 0; i < array3.length(); i++) {
                JSONObject object1 = array3.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrowStatusList.add(listContent);
            }
            info.borrowStatusList = borrowStatusList;


            JSONArray array4 = object.optJSONArray("borrow_money");
            List<Info.ListContent> borrow_money = new ArrayList<>();
            for (int i = 0; i < array4.length(); i++) {
                JSONObject object1 = array4.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrow_money.add(listContent);
            }
            info.borrow_money = borrow_money;
          /*  JSONArray array4 = object.optJSONArray("borrow_money");
            List<Info.ListContent> borrow_money = new ArrayList();
            for (int i = 0; i < array4.length(); i++) {
                JSONObject object1 = array4.optJSONObject(i);
                Info.ListContent listContent = new Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.name = object1.optString("name");
                borrowStatusList.add(listContent);
            }
            info.borrow_money = borrow_money;*/


            TOAST = object.optString("tips");
            return info;
        }

        TOAST = object.optString("tips");

        return null;
    }


    public static class Info {

        public List<ListContent> borrowTypeList;
        public List<ListContent> borrowInterestRateList;
        public List<ListContent> borrowDurationList;
        public List<ListContent> borrowStatusList;

        public List<ListContent> borrow_money;

        public static class ListContent {
            public String id;
            public String name;
        }

    }
}
