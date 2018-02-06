package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.TravelAdapter;
import com.coder.neighborhood.bean.home.TravelBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.TravelView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

@SuppressLint("Registered")
public class TravelActivity extends BaseActivity<TravelView,HomeModel> {

    private TravelAdapter adapter;

    private int page =1;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    public static void start(Context context) {
        Intent intent = new Intent(context, TravelActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_travel;
    }

    @Override
    public void onTitleLeft() {
        finish();
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new TravelAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                onTravels();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onTravels();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        onTravels();
    }


    private void onTravels(){
        m.travels(page + "", Constants.PAGE_SIZE + "", bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<TravelBean>>(this,true) {
                    @Override
                    public void onNext(List<TravelBean> travelBeans) {
                        v.getCsr().setRefreshFinished();
                        if (travelBeans != null && travelBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(travelBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(travelBeans);
                            }

                            if (travelBeans.size() == Constants.PAGE_SIZE) {
                                page++;
                            } else {
                                v.getCsr().setFooterRefreshAble(false);
                            }
                        } else {
                            v.getCsr().setFooterRefreshAble(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        v.getCsr().setRefreshFinished();
                    }
                });
    }
}
