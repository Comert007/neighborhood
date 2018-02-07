package com.coder.neighborhood.activity.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.GoodsSearchAdapter;
import com.coder.neighborhood.bean.mall.GoodsSearchBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.base.RefreshView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/2/7.
 */

@SuppressLint("Registered")
public class GoodsSearchActivity extends BaseActivity<RefreshView, MallModel> {

    private GoodsSearchAdapter adapter;
    private int page = 1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private String mallType;
    private String itemName;

    public static void start(Context context, String mallType, String itemName) {
        Intent intent = new Intent(context, GoodsSearchActivity.class);
        intent.putExtra("mallType", mallType);
        intent.putExtra("itemName", itemName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_search;
    }

    @Override
    protected void init() {
        mallType = getIntent().getStringExtra("mallType");
        itemName = getIntent().getStringExtra("itemName");

        setTitleText(TextUtils.isEmpty(itemName)?"搜索结果":itemName);
        initView();
        initListener();
        initData();
    }


    private void initView() {
        adapter = new GoodsSearchAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        crv.setLayoutManager(new GridLayoutManager(this, 2));
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }

    private void initData() {
        crv.setAdapter(adapter);
        onGoodsSearch();
    }


    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                onGoodsSearch();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onGoodsSearch();
            }
        });
    }

    private void onGoodsSearch() {
        m.goodsSearch(mallType, itemName, page + "", Constants.PAGE_SIZE + "",
                bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<GoodsSearchBean>>(this, false) {
                    @Override
                    public void onNext(List<GoodsSearchBean> goodsSearchBeans) {
                        v.getCsr().setRefreshFinished();
                        if (goodsSearchBeans != null && goodsSearchBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(goodsSearchBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(goodsSearchBeans);
                            }

                            if (goodsSearchBeans.size() == Constants.PAGE_SIZE) {
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
