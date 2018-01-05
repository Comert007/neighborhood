package com.coder.neighborhood.fragment;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.MallAdapter;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.MallView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 *
 * @author feng
 * @date 2017/12/23
 */

public class MallFragment extends BaseFragment<MallView,MallModel>{

    private MallAdapter adapter;
    private CustomRecyclerView crv;
    private CustomSwipeRefreshLayout csr;
    private List<String> urls;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        initData();
    }

    private void initData(){
        adapter = new MallAdapter(getContext());
        crv = v.getCrv();
        csr = v.getCsr();

        crv.setAdapter(adapter);
        onBanner();
        adapter.addList(Arrays.asList("1","2","3","4","5","6","7","8","9"));

    }

    private void onBanner(){
        m.onBanner("1", new HttpSubscriber<List<String>>(getContext(),false) {
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
