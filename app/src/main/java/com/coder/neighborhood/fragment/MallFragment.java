package com.coder.neighborhood.fragment;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.MallAdapter;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.home.GoodsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.MallView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @date 2017/12/23
 */

public class MallFragment extends BaseFragment<MallView, MallModel> {

    private MallAdapter adapter;
    private CustomRecyclerView crv;
    private CustomSwipeRefreshLayout csr;
    private List<String> urls;

    private int page =1;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        initData();
    }

    private void initData() {
        adapter = new MallAdapter(getContext());
        crv = v.getCrv();
        csr = v.getCsr();

        crv.setAdapter(adapter);
        onBanner();
        goods(false);
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

    private void goods(boolean showDialog) {
        m.goods(Constants.TYPE_MALL, Constants.TYPE_NORMAL_FLAG, page + "",  new
                HttpSubscriber<List<GoodsBean>>(getContext(),showDialog) {

            @Override
            public void onNext(List<GoodsBean> goodsBeans) {
                goodsBeans.add(0,new GoodsBean());
                adapter.addList(goodsBeans);
            }
        });
    }
}
