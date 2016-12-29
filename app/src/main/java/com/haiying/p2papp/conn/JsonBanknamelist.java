package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/22/2016.
 */
@HttpInlet("user/bank_name_list")
public class JsonBanknamelist extends HYAsyGet<JsonBanknamelist.Info> {
    public String access_token,uid;

    public JsonBanknamelist(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            JSONArray array = object.optJSONArray("list");
            List<JsonBanklist.Info.ListContent> list = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                JsonBanklist.Info.ListContent listContent = new JsonBanklist.Info.ListContent();
                listContent.id = object1.optString("id");
                listContent.bank_name = object1.optString("name");
                listContent.bank_ico = object1.optString("bank_ico");
                list.add(listContent);
            }
            info.list = list;

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public List<JsonBanklist.Info.ListContent> list;

    }
}
