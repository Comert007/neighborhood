package com.coder.neighborhood.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.HomeAdapter;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.base.BannerView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;

/**
 *
 * @author feng
 * @date 2017/12/23
 */

public class HomeFragment extends BaseFragment<BannerView,HomeModel>{

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
        urls = new ArrayList<>();
        crv = v.getCrv();
        adapter = new HomeAdapter(getContext());
        crv.setAdapter(adapter);
        adapter.addList(Arrays.asList("1","2","3"));
        onBanner();
    }


    private void addHeader(){
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_header,null);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ScreenUtil.scale(header);
        crv.addHeadView(header);
    }


    private void onBanner(){
        m.onBanner("1", new HttpSubscriber<List<String>>(getContext(),true) {
            @Override
            public void onNext(List<String> strings) {
                startBanner(strings);
            }
        });
    }

    private void startBanner(List<String> strings) {
        urls.clear();
        for (String string : strings) {
            urls.add(string);
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
