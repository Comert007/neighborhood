package com.coder.neighborhood.adapter.user;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @Author feng
 * @Date 2018/1/16
 */

public class ImageAdapter extends RvAdapter<String> {

    public ImageAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_image;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new ImageViewHolder(view);
    }

    class ImageViewHolder extends RvViewHolder<String>{

        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

        }
    }
}
