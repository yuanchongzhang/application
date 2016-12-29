package com.haiying.p2papp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zcx.helper.app.AppApplication;
import com.zcx.helper.http.Http;
import com.zcx.helper.init.Helper;
import com.zcx.helper.scale.ScaleScreenHelper;
import com.zcx.helper.scale.ScaleScreenHelperFactory;

/**
 * Created by Administrator on 2/16/2016.
 */
public class MyApplication extends AppApplication {

    public static ScaleScreenHelper scaleScreenHelper;

    public static MyPreferences myPreferences;

    public static MyPreferences guidePreferences;


    public static boolean canClean = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Helper.Initialize(this, "海赢p2p");
        myPreferences = new MyPreferences(this, "my_project_data");
        guidePreferences = new MyPreferences(this, "ISGUIDE");
        scaleScreenHelper = ScaleScreenHelperFactory.create(this, 720);
        Http.getInstance().setIsLog(false);
        Http.getInstance().setOnStartEndCreateDialog(new Http.OnStartEndCreateDialog() {

            @Override
            public Dialog onCreate(Context context) {
                return new ProgressDialog(context);
            }
            @Override
            public void show(Dialog dialog) {
                dialog.show();
            }
            @Override
            public void dismiss(Dialog dialog) {
                dialog.dismiss();
            }

        });
    }
}
