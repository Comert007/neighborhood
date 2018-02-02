package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.mall.AddSecondHandActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.SecondHandMarketAdapter;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.mall.CategoryGoodsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.home.SecondMarketView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/5.
 */

@SuppressLint("Registered")
public class SecondHandMarketActivity extends BaseActivity<SecondMarketView, MallModel> {

    private SecondHandMarketAdapter adapter;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;
    private List<String> urls;

    private int page = 1;
    private int requestCode = 0x13;

    public static void start(Context context) {
        Intent intent = new Intent(context, SecondHandMarketActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_second_hand_market;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        initData();
    }

    private void initData() {
        adapter = new SecondHandMarketAdapter(this);
        crv = v.getCrv();
        crv.setAdapter(adapter);
        initListener();
        onBanner();
        categoryGoods(false);
    }

    private void initListener() {
        csr = v.getCsr();
//        csr.setEnableRefresh(true);
//        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener
// () {
//            @Override
//            public void onHeaderRefreshing() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        csr.setRefreshFinished();
//                    }
//                },2000);
//            }
//
//            @Override
//            public void onFooterRefreshing() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        csr.setRefreshFinished();
//                    }
//                },2000);
//            }
//        });
    }


    @OnClick({R.id.btn_publish_goods})
    public void onPublish(View view) {
        switch (view.getId()) {
            case R.id.btn_publish_goods:
                AddSecondHandActivity.startForResult(this,requestCode);
                break;
            default:
                break;
        }
    }

    private void onBanner() {
        m.onBanner("1", new HttpSubscriber<List<BannerBean>>(SecondHandMarketActivity.this, true) {
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

    private void categoryGoods(boolean showDialog) {
        m.categoryGoods(Constants.TYPE_SECOND_MALL, page + "", new
                HttpSubscriber<List<CategoryGoodsBean>>(this, showDialog) {

                    @Override
                    public void onNext(List<CategoryGoodsBean> categoryGoodsBeans) {
                        adapter.addList(categoryGoodsBeans);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode){
            categoryGoods(false);
        }
    }
}
