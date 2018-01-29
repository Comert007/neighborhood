package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.home.ThingDetailBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.base.BannerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ww.com.core.widget.RoundImageView;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class ThingDetailActivity extends BaseActivity<BannerView,HomeModel> {
    @BindView(R.id.riv)
    RoundImageView riv;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private String lostId;
    private List<String> urls;

    public static void start(Context context,String lostId) {
        Intent intent = new Intent(context, ThingDetailActivity.class);
        intent.putExtra("lostId",lostId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_thing_detail;
    }

    @Override
    protected void init() {
        lostId = getIntent().getStringExtra("lostId");
        urls = new ArrayList<>();
        thingsDetail(lostId);
    }

    private void showData(ThingDetailBean thingDetailBean){
        startBanner(thingDetailBean.getImgUrl());
        ImageLoader.getInstance().displayImage(thingDetailBean.getHeaderImgUrl(),riv,
                BaseApplication.getDisplayImageOptions(R.mipmap.ic_default_avatar));

        tvContent.setText(thingDetailBean.getLostInfo());
        tvPhone.setText("联系方式："+thingDetailBean.getLostPhone());
    }


    private void startBanner(List<ThingDetailBean.ImgUrlBean> bannerBeans) {
        urls.clear();
        for (ThingDetailBean.ImgUrlBean banner : bannerBeans) {
            urls.add(banner.getImgUrl());
        }

        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (urls.size() > 0) {

                }
            }
        });
        v.addBanner();

    }


    private void thingsDetail(String lostId){
        m.thingsDetail(lostId, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<ThingDetailBean>(this,true) {

            @Override
            public void onNext(ThingDetailBean thingDetailBean) {
                if (thingDetailBean !=null){
                    showData(thingDetailBean);
                }
            }
        });
    }
}
