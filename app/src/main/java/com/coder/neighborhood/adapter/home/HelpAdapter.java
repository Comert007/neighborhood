package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2018/1/8.
 */

public class HelpAdapter extends RvAdapter<String> {

    public HelpAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_help;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new HelpViewHolder(view);
    }

    class  HelpViewHolder extends RvViewHolder<String>{

        public HelpViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
