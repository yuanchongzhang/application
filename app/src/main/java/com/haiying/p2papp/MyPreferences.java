package com.haiying.p2papp;

import android.content.Context;

import com.zcx.helper.app.AppPreferences;

/**
 * Created by Administrator on 2/16/2016.
 */
public class MyPreferences extends AppPreferences {

    private String id;
    private String user_name;
    private String user_email;
    private String user_phone;
    private String sex;
    private String real_name;
    private String idcard;
    private String money_freeze;
    private String money_collect;
    private String account_money;
    private String money_experience;
    private String all_money;
    private String userpic;

    private String nick_name;

    private String et_01;
    private String et_03;
    private String et_04;
    public MyPreferences(Context context, String name) {
        super(context, name);
    }

//    public void saveAutoLogin(boolean autoLogin) {
//
//        putBoolean("autoLogin", autoLogin);
//
//    }
//
//    public boolean readAutoLogin() {
//
//        return getBoolean("autoLogin", false);
//
//    }

    public void saveEt01(String et_01) {

        putString("et_01", et_01);

    }

    public String readEt01() {

        return getString("et_03", "");

    }




    public void saveEt04(String et_04) {

        putString("et_04", et_04);

    }

    public String readEt04() {

        return getString("et_04", "");

    }


    public void saveEt03(String et_03) {

        putString("et_03", et_03);

    }

    public String readEt03() {

        return getString("et_01", "");

    }


    public void saveNickName(String nick_name) {

        putString("nick_name", nick_name);

    }

    public String readNickName() {

        return getString("nick_name", "");

    }


    public void saveUserName(String username) {

        putString("username", username);

    }

    public String readUserName() {

        return getString("username", "");

    }

    public void saveUid(String Uid) {
        putString("uid", Uid);
    }

    public String readUid() {

        return getString("uid", "");

    }

    public String readUserpic() {
        return getString("userpic", "");
    }

    public void saveUserpic(String userpic) {
        putString("userpic", userpic);
    }


    public String readUser_phone() {
        return getString("user_phone", "");
    }

    public void saveUser_phone(String user_phone) {
        putString("user_phone", user_phone);
    }

    public String readSex() {
        return getString("sex", "0");
    }

    public void saveSex(String sex) {
        putString("sex", sex);
    }

    public String readReal_name() {
        return getString("real_name", "");
    }

    public void saveReal_name(String real_name) {
        putString("real_name", real_name);
    }

    public String readIdcard() {
        return getString("idcard", "");
    }

    public void saveIdcard(String idcard) {
        putString("idcard", idcard);
    }

    public String readMoney_freeze() {
        return getString("money_freeze", "");
    }

    public void saveMoney_freeze(String money_freeze) {
        putString("money_freeze", money_freeze);
    }

    public String readMoney_collect() {
        return getString("money_collect", "");
    }

    public void saveMoney_collect(String money_collect) {
        putString("money_collect", money_collect);
    }

    public String readAccount_money() {
        return getString("account_money", "");
    }

    public void saveAccount_money(String account_money) {
        putString("account_money", account_money);
    }

    public String readMoney_experience() {
        return getString("money_experience", "");
    }

    public void saveMoney_experience(String money_experience) {
        putString("money_experience", money_experience);
    }

    public String readAll_money() {
        return getString("all_money", "");
    }

    public void saveAll_money(String all_money) {
        putString("all_money", all_money);
    }

    public String readUser_email() {
        return getString("user_email", "");
    }

    public void saveUser_email(String user_email) {
        putString("user_email", user_email);
    }

    public String readPin_pass() {
        return getString("pin_pass", "");
    }

    public void savePin_pass(String pin_pass) {
        putString("pin_pass", pin_pass);
    }

    public void saveIsGuide(boolean isGuide) {

        putBoolean("isGuide", isGuide);

    }

    public boolean readIsGuide() {

        return getBoolean("isGuide", false);

    }

    public Boolean readLoR() {return getBoolean("LOR", true);}

    public void saveLoR(Boolean LOR) {
        putBoolean("LOR", LOR);
    }

    public String readId_status() {
        return getString("id_status", "");
    }

    public String readVip_status() {
        return getString("vip_status", "");
    }

    public String readPhone_status() {
        return getString("phone_status", "");
    }

    public String readEmail_status() {
        return getString("email_status", "");
    }

    public void saveId_status(String id_status) {
        putString("id_status", id_status);
    }

    public void saveVip_status(String vip_status) {
        putString("vip_status", vip_status);
    }

    public void savePhone_status(String phone_status) {
        putString("phone_status", phone_status);
    }

    public void saveEmail_status(String email_status) {
        putString("email_status", email_status);
    }

}
