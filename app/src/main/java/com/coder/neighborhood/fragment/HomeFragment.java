package com.coder.neighborhood.fragment;

import android.text.TextUtils;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.HomeAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.base.HomeView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ww.com.core.widget.CustomRecyclerView;

/**
 *
 * @author feng
 * @date 2017/12/23
 */

public class HomeFragment extends BaseFragment<HomeView,HomeModel>{

    private CustomRecyclerView crv;
    private HomeAdapter adapter;
    private List<String> urls;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null ){
            setTitleText(TextUtils.isEmpty(user.getCommunityName())?"首页":user.getCommunityName());
        }
        urls = new ArrayList<>();
        crv = v.getCrv();
        adapter = new HomeAdapter(getContext());
        crv.setAdapter(adapter);
        adapter.addList(Arrays.asList("1","2","3"));
        onBanner();
    }

    private void onBanner(){
        m.onBanner("1", new HttpSubscriber<List<BannerBean>>(getContext(),true) {
            @Override
            public void onNext(List<BannerBean> bannerBeans) {

                startBanner(bannerBeans);
            }
        });
    }

    private void startBanner(List<BannerBean> bannerBeans) {
        urls.clear();
        for (BannerBean banner : bannerBeans) {
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
}
