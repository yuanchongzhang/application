package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/19/2016.
 */
@HttpInlet("index/invest_pay")

public class JsonInvestpay extends HYAsyGet<JsonInvestpay.Info> {

    public String access_token, id;
    public String uid;


    public JsonInvestpay(String access_token, String id, String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.id = id;
        this.uid = uid;


    }

    @Override
    protected Info parser(JSONObject object) {

        Info info = null;
        if (object.optString("status").equals("1")) {
            info = new Info();
            info.has_pin = object.optString("has_pin");
            info.account_money = object.optString("account_money");
            info.money_experience = object.optString("money_experience");
            info.invest_range = object.optString("invest_range");
            JSONArray array = object.optJSONArray("member_interest_rate_list");
            List<Info.BonusListContent> rateList = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.BonusListContent listContent = new Info.BonusListContent();
                listContent.id = object1.optString("id");
                listContent.end_time_cn = object1.optString("end_time_cn");
                listContent.interest_rate = object1.optString("interest_rate");
                rateList.add(listContent);
            }
            info.rateList = rateList;
            JSONArray array1 = object.optJSONArray("bonus_list");
            List<Info.BonusListContent> bonusList = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.BonusListContent listContent = new Info.BonusListContent();
                listContent.id = object1.optString("id");
                listContent.end_time_cn = object1.optString("end_time_cn");
                listContent.interest_rate = object1.optString("money_bonus");
                bonusList.add(listContent);
            }
            info.bonusList = bonusList;

            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

//        public List<ListContent> list;

        public String has_pin;
        public String account_money;
        public String money_experience;
        public String invest_range;

        public List<BonusListContent> bonusList;
        public List<BonusListContent> rateList;

        public static class BonusListContent {
            public String id;
            public String end_time_cn;
            public String interest_rate;
        }

    }
}