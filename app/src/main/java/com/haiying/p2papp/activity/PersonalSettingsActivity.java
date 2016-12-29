package com.haiying.p2papp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonSetavatar;
import com.zcx.helper.activity.AppPictureActivity;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilSDCard;
import com.zcx.helper.util.UtilToast;

import java.io.ByteArrayOutputStream;
import java.io.File;

//个人设置

public class PersonalSettingsActivity extends AppPictureActivity implements View.OnClickListener {

    private TextView tvtitle;
    private ImageView ivback;
    private SimpleDraweeView ivcamera;
    private ImageView ivpersonalsettings01;
    private TextView etname, tv01, tv02, tv03;
    private android.widget.Button btpsconfirm;
    private PopupWindow popWindow;
    private ImageView ivhead;

    private String PATH;

    private LinearLayout img_layout;
    private TextView et_nicheng;

    private String str_nicheng;

    private LinearLayout nickname_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.INSTANCE.addActivity(this);
        setContentView(R.layout.activity_personal_settings);
//        this.ivhead = (ImageView) findViewById(R.id.iv_head);
        BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) getWindow().getDecorView()));
        this.btpsconfirm = (Button) findViewById(R.id.bt_ps_confirm);
        this.etname = (TextView) findViewById(R.id.et_name);
        this.ivpersonalsettings01 = (ImageView) findViewById(R.id.iv_personalsettings_01);
        this.ivcamera = (SimpleDraweeView) findViewById(R.id.iv_camera);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.tvtitle = (TextView) findViewById(R.id.tv_title);
        this.img_layout = (LinearLayout) findViewById(R.id.img_layout);
        et_nicheng = (TextView) findViewById(R.id.et_nicheng);
     //   str_nicheng = getIntent().getStringExtra("nicheng");


        nickname_layout = (LinearLayout) findViewById(R.id.nickname_layout);
        nickname_layout.setOnClickListener(this);
        img_layout.setOnClickListener(this);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.evictFromMemoryCache(Uri.parse(MyApplication.myPreferences.readUserpic()));
        imagePipeline.evictFromDiskCache(Uri.parse(MyApplication.myPreferences.readUserpic()));

        Log.v("maning", MyApplication.myPreferences.readUserpic());

        ivcamera.setImageURI(Uri.parse(MyApplication.myPreferences.readUserpic()));

        String phone = MyApplication.myPreferences.readUserName();
        if (!TextUtils.isEmpty(MyApplication.myPreferences.readUserName())) {
//            etname.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
//            etname.setText(phone.substring(0, 2)+ "****" + phone.substring(7, 10));
            etname.setText(phone);
        }

        ivcamera.setOnClickListener(this);
//        btpsconfirm.setOnClickListener(this);
//        ivhead.setOnClickListener(this);
        ivpersonalsettings01.setOnClickListener(this);
    }

    @Override
    protected String getCameraAbsolutePath() {
        if (UtilSDCard.isSDCardEnable()) {
            File file = new File(Environment.getExternalStorageDirectory() + "/head");
            if (!file.exists()) {
                file.mkdir();
            }
        } else {
            File dir = new File(this.getApplicationInfo().dataDir + "/head/");
            if (!dir.exists())
                dir.mkdir();
            return dir.getAbsolutePath();
        }

        return Environment.getExternalStorageDirectory() + "/head";
    }

    @Override
    protected void resultPhotoPath(ImageView imageView, String s) {
        if (!TextUtils.isEmpty(s)) {
            PATH = s;

//            ivcamera.setVisibility(View.GONE);
            ivcamera.setVisibility(View.VISIBLE);
            ivcamera.setImageURI(Uri.parse("file:///" + s));

//            ivcamera.setImageBitmap(BitmapFactory.decodeFile(s));

            Log.e("DDDDDDDDDD", s);

            getAccessToken();

        }

    }

    protected Crop getCrop() {
        return new Crop();
    }


//    @Override
//    protected AppCrop getCrop() {
//
//        return new AppCrop().setCrop(true);
//
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_camera:
            case R.id.iv_personalsettings_01:
                showPopupWindow(ivcamera);
                break;
            case R.id.img_layout:
                showPopupWindow(ivcamera);
                break;

            case R.id.bt_ps_confirm:
                if (TextUtils.isEmpty(PATH)) {
                    onBackPressed();
                } else {
                    getAccessToken();
                }
                break;

            case R.id.nickname_layout:
              //  startActivity(new Intent(PersonalSettingsActivity.this, ChangeNickNameActivity.class));
                Intent intent=new Intent();
                intent.putExtra("nickname",MyApplication.myPreferences.readNickName());
                intent.setClass(PersonalSettingsActivity.this, ChangeNickNameActivity.class);
                startActivity(intent);
                break;

        }

    }


    private void getAccessToken() {
        new JsonAccessToken("user/setavatar", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initUpload(info.access_token);
                    } else {
                        UtilToast.show(PersonalSettingsActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(PersonalSettingsActivity.this, "安全验证失败！");
            }
        }).execute(this);

    }

    private void initUpload(String access_token) {
        new JsonSetavatar(access_token, MyApplication.myPreferences.readUid(), getEncode64(PATH), new AsyCallBack<JsonSetavatar.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonSetavatar.Info info) throws Exception {
                if (info != null) {
                    MyApplication.myPreferences.saveUserpic(info.avatar);
                    ivcamera.setSelected(true);
                }
            }

            @Override
            public void onEnd(String toast, int type) throws Exception {
                UtilToast.show(PersonalSettingsActivity.this, JsonSetavatar.TOAST);
            }

        }).execute(this, true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_nicheng.setText(MyApplication.myPreferences.readNickName());
    }

    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) LayoutInflater.from(this)
                    .inflate(R.layout.pop_select_photo, null)));
            //LayoutParams相当于一个Layout的信息包，它封装了Layout的位置、高、宽等信息。
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initPop(view);
        }
        //设置动画效果
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        //获取popwindow焦点
        popWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //实现软键盘不自动弹出,ADJUST_RESIZE属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置popwindow显示位置
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    public void initPop(View view) {
        tv01 = (TextView) view.findViewById(R.id.tv_01);//拍照
        tv02 = (TextView) view.findViewById(R.id.tv_02);//相册
        tv03 = (TextView) view.findViewById(R.id.tv_03);//取消

        tv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                startCamera(ivcamera);

            }
        });
        tv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
                startAlbum(ivcamera);
            }
        });
        tv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.INSTANCE.finishActivity(this);
    }

    public void onBack(View view) {
        finish();
    }

    public static String getEncode64(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        bitmap.recycle();
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
