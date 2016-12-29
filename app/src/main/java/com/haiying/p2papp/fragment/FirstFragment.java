package com.haiying.p2papp.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haiying.p2papp.MyApplication;
import com.haiying.p2papp.activity.AdvertisingActivity;
import com.haiying.p2papp.activity.MainActivity;
import com.haiying.p2papp.activity.NewsActivity;
import com.haiying.p2papp.activity.R;
import com.haiying.p2papp.activity.TiroAndExperienceActivity;
import com.haiying.p2papp.adapter.HomeListAdapter;
import com.haiying.p2papp.conn.JsonAccessToken;
import com.haiying.p2papp.conn.JsonIndex;
import com.zcx.helper.activity.AppV4Fragment;
import com.zcx.helper.bound.BoundViewHelper;
import com.zcx.helper.http.AsyCallBack;
import com.zcx.helper.util.UtilToast;
import com.zcx.helper.view.AdaptListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends AppV4Fragment implements View.OnClickListener {
    public final static String FLAG = "IsTiro";

    @Bind(R.id.iv_mailer)
    ImageView ivMailer;
    @Bind(R.id.ll_01)
    LinearLayout ll01;
    @Bind(R.id.ll_02)
    LinearLayout ll02;
    //    @Bind(R.id.ll_11)
//    LinearLayout ll11;
//    @Bind(R.id.ll_12)
//    LinearLayout ll12;
//    @Bind(R.id.ll_31)
//    LinearLayout ll31;
//    @BoundView(R.id.tv_01)
//    TextView tv01;
//    @Bind(R.id.tv_011)
//    TextView tv011;
//    @Bind(R.id.tv_02)
//    TextView tv02;
//    @Bind(R.id.tv_022)
//    TextView tv022;
    //    @Bind(R.id.tv_03)
//    TextView tv03;
//    @Bind(R.id.tv_033)
//    TextView tv033;
//    @Bind(R.id.tv_04)
//    TextView tv04;
//    @Bind(R.id.tv_044)
//    TextView tv044;
//    @Bind(R.id.tv_05)
//    TextView tv05;
//    @Bind(R.id.tv_055)
//    TextView tv055;
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    //    @Bind(R.id.tv_06)
//    TextView tv06;
//    @Bind(R.id.tv_066)
//    TextView tv066;
//    @Bind(R.id.ll_32)
//    LinearLayout ll32;
    @Bind(R.id.lv_home_list)
    AdaptListView lv_home_list;

    private List<JsonIndex.Info.MenuListContent> menuListContent = new ArrayList<>();

    private HomeListAdapter homeListAdapter;

    private String news_id;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = BoundViewHelper.boundView(this, MyApplication.scaleScreenHelper.loadView((ViewGroup) inflater.inflate(R.layout.fragment_first, null)));
        ButterKnife.bind(this, view);

        lv_home_list.setFocusable(false);

        lv_home_list.setDividerHeight(1);

        lv_home_list.setDivider(getResources().getDrawable(R.drawable.divider_home));

        homeListAdapter = new HomeListAdapter(getContext(), menuListContent);

        lv_home_list.setAdapter(homeListAdapter);


        lv_home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                  /*  Intent intent1 = new Intent();
                    intent1.putExtra(FLAG, false);
                    intent1.setClass(getActivity(), TiroAndExperienceActivity.class);
                    startActivity(intent1);*/
                    Intent intent2 = new Intent();
                    intent2.putExtra(FLAG, true);
                    intent2.setClass(getActivity(), TiroAndExperienceActivity.class);
                    startActivity(intent2);
                } else if (i==1){
                   /* Intent intent1 = new Intent();
                    intent1.putExtra(FLAG, false);
                    intent1.setClass(getActivity(), TiroAndExperienceActivity.class);
                    startActivity(intent1);*/
                    ((MainActivity) getActivity()).setFirstSelect(false);
                    MyApplication.myPreferences.saveLoR(false);
                    ((MainActivity) getActivity()).setSelect(2);
                }else {
                   // ToastUtils.show(getActivity(),"敬请期待！");
                    Intent intent1 = new Intent();
                    intent1.putExtra(FLAG, false);
                    intent1.setClass(getActivity(), TiroAndExperienceActivity.class);
                    startActivity(intent1);
                }


            }
        });

        initListener();
        getAccessToken();
        return view;
    }

    private void getAccessToken() {
        new JsonAccessToken("index/index", new AsyCallBack<JsonAccessToken.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonAccessToken.Info info) throws Exception {
                if (info != null) {
                    if (!TextUtils.isEmpty(info.access_token)) {
                        initData(info.access_token);
                    } else {
                        UtilToast.show(getActivity(), "安全验证失败！");
                    }

                }

            }

            @Override
            public void onFail(String toast, int type) throws Exception {
                super.onFail(toast, type);
                UtilToast.show(getActivity(), "安全验证失败！");
            }
        }).execute(getActivity(), false);

    }

    private void initData(String access_token) {
        new JsonIndex(access_token, new AsyCallBack<JsonIndex.Info>() {
            @Override
            public void onSuccess(String toast, int type, JsonIndex.Info info) throws Exception {
                if (info != null) {

                    menuListContent.clear();

                    menuListContent.addAll(info.menuList);

                    homeListAdapter.notifyDataSetChanged();

                    news_id = info.news_id;

                    convenientBanner.setPages(
                            new CBViewHolderCreator<LocalImageHolderView>() {
                                @Override
                                public LocalImageHolderView createHolder() {
                                    return new LocalImageHolderView();
                                }
                            }, info.bannerList).setPageIndicator(new int[]{R.mipmap.ic_dots_uncheck, R.mipmap.ic_dots_check})
                            //设置指示器的方向
                            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL).setCanLoop(true);

                } else {
                    UtilToast.show(getActivity(), JsonIndex.TOAST);
                }

            }

            @Override
            public void onEnd(String toast, int type) throws Exception {

            }

        }).execute(getActivity(), false);
    }

    private void initListener() {

        ll01.setOnClickListener(this);
        ll02.setOnClickListener(this);
        ivMailer.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_01:
                ((MainActivity) getActivity()).setFirstSelect(true);
                ((MainActivity) getActivity()).setSelect(1);
                break;
            case R.id.ll_02:
                ((MainActivity) getActivity()).setSelect(1);
                ((MainActivity) getActivity()).setFirstSelect(false);
                MyApplication.myPreferences.saveLoR(false);




                break;
//            case R.id.ll_11:
//                Intent intent1 = new Intent();
//                intent1.putExtra(FLAG, false);
//                intent1.setClass(getActivity(), TiroAndExperienceActivity.class);
//                startActivity(intent1);
//                break;
//            case R.id.ll_12:
//                Intent intent2 = new Intent();
//                intent2.putExtra(FLAG, true);
//                intent2.setClass(getActivity(), TiroAndExperienceActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.ll_31:
//                ((MainActivity) getActivity()).setSelect(2);
//                break;
//            case R.id.ll_32:
//                ToastUtils.show(getActivity(),"敬请期待！");
//                break;
            case R.id.iv_mailer:
                if (!TextUtils.isEmpty(news_id)) {
                    Intent intent = new Intent();
                    intent.putExtra("id", news_id);
                    intent.setClass(getActivity(), NewsActivity.class);
                    startActivity(intent);
                } else {
                    getAccessToken();
                }
                break;
        }
    }

    public class LocalImageHolderView implements Holder<JsonIndex.Info.BannerListContent> {
        private SimpleDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new SimpleDraweeView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, final JsonIndex.Info.BannerListContent listContent) {
            imageView.setImageURI(Uri.parse(listContent.img));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", listContent.title);
                    intent.putExtra("url", listContent.url);

                    Log.d(listContent.url,"88888888888888888888888888");
                    intent.setClass(getActivity(), AdvertisingActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }
}
