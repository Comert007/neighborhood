package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.CommentBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * @author feng
 * @date 2018/1/9
 */

public class CommentDetailAdapter extends RvAdapter<CommentBean> {

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

    class CommentDetailViewHolder extends RvViewHolder<CommentBean> {

        @BindView(R.id.riv)
        RoundImageView riv;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;


        public CommentDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CommentBean bean) {
            tvContent.setText(bean.getCommentsContent());
            tvTime.setText(bean.getCommentsDate());
            ImageLoader.getInstance().displayImage(bean.getImgUrl(), riv, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_default_avatar));
        }
    }
}
