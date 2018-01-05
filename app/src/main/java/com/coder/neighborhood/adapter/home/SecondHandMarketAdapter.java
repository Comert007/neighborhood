package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class SecondHandMarketAdapter extends RvAdapter<String> {

    public SecondHandMarketAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_second_hand_market;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new SecondHandMarketViewHolder(view);
    }

    class SecondHandMarketViewHolder extends RvViewHolder<String>{

        public SecondHandMarketViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
