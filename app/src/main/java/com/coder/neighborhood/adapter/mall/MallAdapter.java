package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class MallAdapter extends RvAdapter<String> {

    private static final int TYPE_TITEL = 1;
    private static final int TYPE_CONTENT =2;

    public MallAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position%3 ==0){
            return TYPE_TITEL;
        }else {
            return TYPE_CONTENT;
        }
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        if (viewType == TYPE_TITEL){
            return R.layout.item_mall_title;
        }else {
            return R.layout.item_mall_content;
        }
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        if (viewType ==  TYPE_TITEL){
            return new MallTitleViewHolder(view);
        }else {
            return new MallContentViewHolder(view);
        }

    }

    class MallTitleViewHolder extends RvViewHolder<String>{

        public MallTitleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }

    class MallContentViewHolder extends RvViewHolder<String>{

        public MallContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
