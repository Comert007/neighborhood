package com.coder.neighborhood.mvp.vu.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class BannerView extends RefreshView {

    Banner banner;
    private View bannerView;
    private List<String> urls;

    @Override
    public void attach() {
        super.attach();
        bannerView = LayoutInflater.from(preActivity).inflate(R.layout.view_banner,null);
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
}
