package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.GoodsTypeAdapter;
import com.coder.neighborhood.bean.mall.GoodsTypeBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.GoodsTypeView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/2/6.
 */

public class GoodsTypeActivity extends BaseActivity<GoodsTypeView,MallModel> {


    private GoodsTypeAdapter adapter;
    private int page =1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private String mallType;
    private String itemCategoryId;
    private String itemCategoryName;


    public static void start(Context context,String mallType,
                             String itemCategoryId,String itemCategoryName) {
        Intent intent = new Intent(context, GoodsTypeActivity.class);
        intent.putExtra("mallType",mallType);
        intent.putExtra("itemCategoryId",itemCategoryId);
        intent.putExtra("itemCategoryName",itemCategoryName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_type;
    }

    @Override
    protected void init() {
        mallType = getIntent().getStringExtra("mallType");
        itemCategoryId = getIntent().getStringExtra("itemCategoryId");
        itemCategoryName = getIntent().getStringExtra("itemCategoryName");
        setTitleText(itemCategoryName);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new GoodsTypeAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        crv.setLayoutManager(new GridLayoutManager(this,2));
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
                onGoods();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onGoods();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        onGoods();
    }

    private void onGoods(){
        m.goods(mallType, itemCategoryId, page+"", Constants.PAGE_SIZE+"",
                bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<GoodsTypeBean>>(this,false) {
                    @Override
                    public void onNext(List<GoodsTypeBean> goodsTypeBeans) {
                        v.getCsr().setRefreshFinished();
                        if (goodsTypeBeans != null && goodsTypeBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(goodsTypeBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(goodsTypeBeans);
                            }

                            if (goodsTypeBeans.size() == Constants.PAGE_SIZE) {
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
