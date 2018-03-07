package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/3/7.
 */

public class CommitOrderAdapter extends RvAdapter<String> {

    public CommitOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_commit_order;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new CommitOrderViewHolder(view);
    }

    class CommitOrderViewHolder extends RvViewHolder<String>{

        public CommitOrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
