package com.coder.neighborhood.mvp.vu.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.home.FindThingsActivity;
import com.coder.neighborhood.activity.home.HelpActivity;
import com.coder.neighborhood.activity.home.LivingListActivity;
import com.coder.neighborhood.activity.home.SecondHandMarketActivity;
import com.coder.neighborhood.activity.home.TravelActivity;
import com.coder.neighborhood.activity.user.CustomerServiceActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class HomeView extends RefreshView {

    Banner banner;
    private View bannerView;
    private List<String> urls;

    @Override
    public void attach() {
        super.attach();
        bannerView = LayoutInflater.from(preActivity).inflate(R.layout.layout_home_header,null);
        onBannerPosition();
        ScreenUtil.scale(bannerView);
        banner = ButterKnife.findById(bannerView,R.id.banner);
    }


    public void addBanner(){
        crv.addHeadView(bannerView);
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }


    public void startBanner(){
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoader.getInstance().displayImage((String) path, imageView, BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            }
        });

        if (urls!=null&& urls.size()>0){
            banner.setImages(urls);
            banner.start();
        }
    }

    public Banner getBanner() {
        return banner;
    }


    private void onBannerPosition(){
        ButterKnife.findById(bannerView,R.id.ll_living).setOnClickListener(v -> onBannerClick(1));
        ButterKnife.findById(bannerView,R.id.ll_second_market).setOnClickListener(v -> onBannerClick(2));
        ButterKnife.findById(bannerView,R.id.ll_travel).setOnClickListener(v -> onBannerClick(4));
        ButterKnife.findById(bannerView,R.id.ll_help).setOnClickListener(v -> onBannerClick(5));
        ButterKnife.findById(bannerView,R.id.ll_find_things).setOnClickListener(v -> onBannerClick(6));
        ButterKnife.findById(bannerView,R.id.ll_customer_service).setOnClickListener(v -> onBannerClick(8));
    }

    private void onBannerClick(int position){
        switch (position){
            case 1:
                LivingListActivity.start(preActivity);
                break;
            case 2:
                SecondHandMarketActivity.start(preActivity);
                break;
            case 3:
                break;
            case 4:
                TravelActivity.start(preActivity);
                break;
            case 5:
                HelpActivity.start(preActivity);
                break;
            case 6:
                FindThingsActivity.start(preActivity);
                break;
            case 7:
                break;
            case 8:
                CustomerServiceActivity.start(preActivity);
                break;
            default:
                break;
        }
    }
}
