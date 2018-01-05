package com.coder.neighborhood.mvp.vu.home;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

public class SecondMarketView extends BannerView {

    @Override
    public void attach() {
        if (crv!=null){
            GridLayoutManager manager = new GridLayoutManager(preActivity,2);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position==0){
                        return 2;
                    }else {
                        return 1;
                    }

                }
            });
            crv.setLayoutManager(manager);
            crv.setItemAnimator(new DefaultItemAnimator());

            View emptyView = LayoutInflater.from(preActivity).inflate(R.layout.layout_empty, null);
            QMUIEmptyView qmuiEmptyView = ButterKnife.findById(emptyView,R.id.empty_view);
            qmuiEmptyView.setButton(getResources().getString(R.string.str_empty_btn), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (emptyListener!=null){
                        emptyListener.onButton();
                    }
                }
            });
            ScreenUtil.scale(emptyView);
            crv.addEmpty(emptyView);
        }

        if (csr!=null){
            csr.setRefreshView(crv);
            csr.setColorSchemeColors(new int[]{Color.parseColor("#14191d"), -65536});
            csr.setEnableRefresh(false);
        }

        bannerView = LayoutInflater.from(preActivity).inflate(R.layout.view_banner,null,false);
        ScreenUtil.scale(bannerView);
        banner = ButterKnife.findById(bannerView,R.id.banner);
    }

}
