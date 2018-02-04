package com.coder.neighborhood.mvp.vu.home;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.vu.BaseView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @Author feng
 * @Date 2018/2/4
 */

public class DetailBannerView  extends BaseView {

    private Banner banner;
    protected List<String> urls;


    @Override
    public void onAttach(@NonNull Activity preActivity, @NonNull View contentView) {
        super.onAttach(preActivity, contentView);
        banner = ButterKnife.findById(contentView, R.id.banner);
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
