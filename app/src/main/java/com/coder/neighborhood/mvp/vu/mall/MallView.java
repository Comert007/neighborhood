package com.coder.neighborhood.mvp.vu.mall;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.vu.base.BannerView;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class MallView extends BannerView {

    @Override
    public void attach() {
        if (crv!=null){
            LinearLayoutManager manager = new LinearLayoutManager(preActivity);
            crv.setLayoutManager(manager);
            crv.setItemAnimator(new DefaultItemAnimator());

            View emptyView = LayoutInflater.from(preActivity).inflate(R.layout.layout_empty, null);
            QMUIEmptyView qmuiEmptyView = ButterKnife.findById(emptyView,R.id.empty_view);
            qmuiEmptyView.setTitleText("暂无更多商品");

            ScreenUtil.scale(emptyView);
            crv.addEmpty(emptyView);
        }

        if (csr!=null){
            csr.setRefreshView(crv);
            csr.setColorSchemeColors(new int[]{Color.parseColor("#14191d"), -65536});
            csr.setEnableRefresh(false);
        }

        bannerView = LayoutInflater.from(preActivity).inflate(R.layout.view_search_banner,null,false);
        ScreenUtil.scale(bannerView);
        banner = ButterKnife.findById(bannerView,R.id.banner);
    }

}
