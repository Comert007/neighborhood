package com.coder.neighborhood.fragment;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.HomeAdapter;
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
        urls = new ArrayList<>();
        crv = v.getCrv();
        adapter = new HomeAdapter(getContext());
        crv.setAdapter(adapter);
        adapter.addList(Arrays.asList("1","2","3"));
        onBanner();
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
