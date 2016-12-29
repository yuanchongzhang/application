package com.haiying.p2papp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonBorrowdo;
import com.zcx.helper.activity.AppPictureActivity;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilSDCard;
import com.zcx.helper.util.UtilToast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class IoanActivity extends AppPictureActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    /*
        private ImageView ivback;
        private TextView et01;
        private EditText et02;
        private EditText et03;
        private EditText et04;
        private ImageView ivcb01;
        private Button bt01;
        private TextView tv001;
        private EditText et05;
        private TextView tvhint;*/
    private Button bt02;
   /* public List<JsonBorrowindex.Info.ListContent> daishou = new ArrayList<>();
    public List<String> stringListDaishou = new ArrayList<>();
    private Spinner sp01;
    private ImageView iv01;
    private String path;
    private TextView tv01, tv02, tv03;
    private PopupWindow popWindow;*/

    private String sex;
    private String borrow_money;

    private String borrow_use;

    private String borrow_use_limit;
    private TextView et01;
    private EditText et02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.INSTANCE.addActivity(this);
        setContentView(R.layout.activity_ioan);

        sex = getIntent().getStringExtra("sex");

        borrow_money = getIntent().getStringExtra("borrow_money");
        borrow_use = getIntent().getStringExtra("borrow_use");
        borrow_use_limit = getIntent().getStringExtra("borrow_use_limit");


        Log.d(sex + borrow_money + borrow_use + borrow_use_limit, "wocaoshoumeishoudaoshuu");

        BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) getWindow().getDecorView()));
        initView();
    }

    private void initView() {
      /*  this.iv01 = (ImageView) findViewById(R.id.iv_01);
        this.sp01 = (Spinner) findViewById(R.id.sp_01);*/
        this.bt02 = (Button) findViewById(R.id.bt_02);
        this.et01 = (TextView) findViewById(R.id.et_01);
        this.et02 = (EditText) findViewById(R.id.et_02);
        bt02.setOnClickListener(this);
       /* this.tvhint = (TextView) findViewById(R.id.tv_hint);
        this.et05 = (EditText) findViewById(R.id.et_05);
        this.tv001 = (TextView) findViewById(R.id.tv_01);
        this.bt01 = (Button) findViewById(R.id.bt_01);
        this.ivcb01 = (ImageView) findViewById(R.id.iv_cb_01);
        this.et04 = (EditText) findViewById(R.id.et_04);
        this.et03 = (EditText) findViewById(R.id.et_03);
        this.et02 = (EditText) findViewById(R.id.et_02);
        this.et01 = (TextView) findViewById(R.id.et_01);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        et05.setOnClickListener(this);
        ivcb01.setOnClickListener(this);
        iv01.setOnClickListener(this);
        et01.setOnClickListener(this);
        bt01.setOnClickListener(this);
        bt02.setOnClickListener(this);
        et04.setEnabled(false);
        et04.setFocusable(false);
        et04.setFocusableInTouchMode(false);
        et05.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvhint.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    tvhint.setVisibility(View.VISIBLE);
                } else {
                    tvhint.setVisibility(View.GONE);
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_02:
                if (et01.getText().toString().isEmpty() | et02.getText().toString().isEmpty()) {
                    Toast.makeText(IoanActivity.this, "请填写完信息", Toast.LENGTH_SHORT).show();
                } else {
                    getAccessToken();
                }


                break;
        }

    }

    private void getAccessToken() {
        new JsonAccessToken("index/borrow_do", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(IoanActivity.this, "安全验证失败！");
                    }
                }
            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(IoanActivity.this, "安全验证失败！");
            }
        }).execute(this, true);

    }


    private void initData(String access_token) {
        String ISPASS;
        Log.d(access_token + MyApplication.myPreferences.readUserName() + sex + MyApplication.myPreferences.readUser_phone() + borrow_money + borrow_use + borrow_use_limit + et01.getText().toString() + et02.getText().toString(), "55555555555555555555555555555555555");
       /* sex=getIntent().getStringExtra("sex");

        borrow_money=getIntent().getStringExtra("borrow_money");
        borrow_use=getIntent().getStringExtra("borrow_use");
        borrow_use_limit=getIntent().getStringExtra("borrow_use_limit");*/

        // new JsonBorrowdo(access_token,MyApplication.myPreferences.readUserName(), sex,MyApplication.myPreferences.readUser_phone(),borrow_use,borrow_money,borrow_use_limit,borrow_use_limit,borrow_use_limit,
        //   new JsonBorrowdo(access_token,MyApplication.myPreferences.readUserName(), sex,MyApplication.myPreferences.readUser_phone(),borrow_use,borrow_use_limit,et01.getText().toString(),et02.getText().toString(),"adlskjf",
     //   new JsonBorrowdo(access_token, MyApplication.myPreferences.readUserName(), sex, MyApplication.myPreferences.readUser_phone(), borrow_money, borrow_use, borrow_use_limit, et01.getText().toString(), et02.getText().toString(),
        new JsonBorrowdo(access_token,getIntent().getStringExtra("username"), sex, getIntent().getStringExtra("userphone"), borrow_money, borrow_use, borrow_use_limit, et01.getText().toString(), et02.getText().toString(),
                new AsyCallBack() {
                    @Override
                    public void onSuccess(String toast, int type, Object o) throws Exception {
                        if (o.equals("")) {
                            MyApplication.canClean = false;
                           /* ((MainActivity)).setSelect(1);
                            ((MainActivity)).setFirstSelect(false);
                            MyApplication.myPreferences.saveLoR(false);*/
                            finish();
                        }
                    }

                    @Override
                    public void onEnd(String toast, int type) throws Exception {
                        UtilToast.show(IoanActivity.this, JsonBorrowdo.TOAST);

                    }

                }).execute(this, true);
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
//        path = s;
//        tv001.setText("已选择图片");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.INSTANCE.finishActivity(this);
    }

    public void onBack(View view) {
        MyApplication.canClean = false;
        onBackPressed();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyApplication.canClean = false;
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);

    }

    public static String getEncode64(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bStream);
        bitmap.recycle();
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
