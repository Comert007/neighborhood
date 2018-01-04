package com.coder.neighborhood.adapter;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2018/1/4.
 */

public class HomeAdapter extends RvAdapter<String> {



    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position ==2){
            return 2;
        }else {
            return 1;
        }
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        if (viewType ==2){
            return R.layout.item_home_mall;
        }else {
            return R.layout.item_home;
        }

    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        if (viewType ==2){
            return new HomeMallViewHolder(view);
        }else {
            return new HomeViewHolder(view);
        }

    }

    class HomeViewHolder extends RvViewHolder<String>{

        public HomeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }

    class HomeMallViewHolder extends RvViewHolder<String>{

        public HomeMallViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
