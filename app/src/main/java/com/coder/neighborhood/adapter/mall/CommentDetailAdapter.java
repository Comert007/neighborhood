package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.CommentBean;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2018/1/9.
 */

public class CommentDetailAdapter extends RvAdapter<CommentBean>{

    public CommentDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_comment_detail;
    }

    @Override
    protected RvViewHolder<CommentBean> getViewHolder(int viewType, View view) {
        return new CommentDetailViewHolder(view);
    }

    class CommentDetailViewHolder extends RvViewHolder<CommentBean>{

        public CommentDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CommentBean bean) {

        }
    }
}
