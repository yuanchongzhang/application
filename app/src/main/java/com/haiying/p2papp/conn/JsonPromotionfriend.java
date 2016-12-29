package com.haiying.p2papp.conn;

import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.http.note.HttpInlet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/12/2016.
 */
@HttpInlet("user/promotion_friend")
public class JsonPromotionfriend extends HYAsyGet<JsonPromotionfriend.Info> {
    public String access_token,uid;

    public JsonPromotionfriend(String access_token,String uid, AsyCallBack<Info> asyCallBack) {
        super(asyCallBack);
        this.access_token = access_token;
        this.uid = uid;
    }

    @Override
    protected Info parser(JSONObject object) {
        Info info = null;

        if (object.optString("status").equals("1")) {
            info = new Info();
            info.last_time = object.optString("last_time");
            JSONArray array = object.optJSONArray("members_list");
            List<Info.Members> memberslist = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.optJSONObject(i);
                Info.Members members = new Info.Members();
                members.user_name = object1.optString("user_name");
                members.reg_time = object1.optString("reg_time");
                memberslist.add(members);
            }
            info.memberslist = memberslist;
            JSONArray array1 = object.optJSONArray("award_money_log");
            List<Info.AwardMoneyLog> moneyLogList = new ArrayList();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object1 = array1.optJSONObject(i);
                Info.AwardMoneyLog moneyLog = new Info.AwardMoneyLog();
                moneyLog.user_name = object1.optString("user_name");
                moneyLog.award_money = object1.optString("award_money");
                moneyLogList.add(moneyLog);
            }
            info.moneyLogList = moneyLogList;


            TOAST = object.optString("tips");

            return info;

        }

        TOAST = object.optString("tips");

        return null;
    }

    public static class Info {

        public String last_time;

        public List<Members> memberslist;
        public List<AwardMoneyLog> moneyLogList;

        public static class Members {
            public String user_name;
            public String reg_time;
        }

        public static class AwardMoneyLog {
            public String user_name;
            public String award_money;
        }

    }

}
