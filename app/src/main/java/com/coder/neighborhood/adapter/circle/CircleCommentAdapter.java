package com.coder.neighborhood.adapter.circle;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.circle.CircleBean;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.utils.TimeUtils;

/**
 * Created by feng on 2018/1/9.
 */

public class CircleCommentAdapter extends RvAdapter<CircleBean.CommentsBean>{

    public CircleCommentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_circle_comment;
    }

    @Override
    protected RvViewHolder<CircleBean.CommentsBean> getViewHolder(int viewType, View view) {
        return new CircleCommentViewHolder(view);
    }

    class CircleCommentViewHolder extends RvViewHolder<CircleBean.CommentsBean>{
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;


        public CircleCommentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CircleBean.CommentsBean bean) {
            tvName.setText(bean.getNickname());
            tvContent.setText(bean.getCommentsContent());
            if (!TextUtils.isEmpty(bean.getCommentsDate())){
                tvTime.setText(TimeUtils.milliseconds2String(
                        TimeUtils.string2Milliseconds(bean.getCommentsDate(),new SimpleDateFormat("yyyy-MM-dd")),
                        new SimpleDateFormat("yyyy.MM.dd")));
            }

        }
    }
}
