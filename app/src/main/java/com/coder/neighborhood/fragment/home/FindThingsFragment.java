package com.coder.neighborhood.fragment.home;

import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.FindThingsAdapter;
import com.coder.neighborhood.bean.home.ThingsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/20
 */
public class FindThingsFragment extends BaseFragment<HelpView, HomeModel> {

    private FindThingsAdapter adapter;
    private int page = 1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find_things;
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new FindThingsAdapter(getContext());
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
                lostThings();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                lostThings();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        lostThings();
    }

    private void lostThings() {
        m.lostThings("1", page + "", Constants.PAGE_SIZE + "",
                new HttpSubscriber<List<ThingsBean>>(getContext(), false) {
                    @Override
                    public void onNext(List<ThingsBean> thingsBeans) {
                        v.getCsr().setRefreshFinished();
                        if (thingsBeans != null && thingsBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(thingsBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(thingsBeans);
                            }

                            if (thingsBeans.size() == Constants.PAGE_SIZE) {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        page = 1;
        v.getCsr().setFooterRefreshAble(true);
        csr.setFooterRefreshAble(false);
        lostThings();
    }
}
