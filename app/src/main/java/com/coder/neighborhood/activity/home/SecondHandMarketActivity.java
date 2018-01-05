package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.SecondHandMarketAdapter;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.SecondMarketView;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/5.
 */

@SuppressLint("Registered")
public class SecondHandMarketActivity extends BaseActivity<SecondMarketView,HomeModel> {

    private SecondHandMarketAdapter adapter;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;
    private List<String> urls;

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

    private void initData(){
        adapter = new SecondHandMarketAdapter(this);
        crv = v.getCrv();
        csr = v.getCsr();
        crv.setAdapter(adapter);
        csr.setEnableRefresh(true);
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        csr.setRefreshFinished();
                    }
                },2000);
            }

            @Override
            public void onFooterRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        csr.setRefreshFinished();
                    }
                },2000);
            }
        });

        onBanner();
        adapter.addList(Arrays.asList("1","2","3","4","5","6","7","8"));
    }


    private void onBanner(){
        m.onBanner("1", new HttpSubscriber<List<String>>(this,false) {
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
