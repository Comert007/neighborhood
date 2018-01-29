package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.home.QuestionBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2018/1/8.
 */

public class HelpAdapter extends RvAdapter<QuestionBean> {

    public HelpAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_help;
    }

    @Override
    protected RvViewHolder<QuestionBean> getViewHolder(int viewType, View view) {
        return new HelpViewHolder(view);
    }

    class  HelpViewHolder extends RvViewHolder<QuestionBean>{
        @BindView(R.id.riv)
        RoundImageView riv;
        @BindView(R.id.tv_question_content)
        TextView tvContent;
        @BindView(R.id.tv_question_time)
        TextView tvTime;
        @BindView(R.id.tv_question_type)
        TextView tvType;

        public HelpViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, QuestionBean bean) {
            ImageLoader.getInstance().displayImage(bean.getHeaderImgUrl(),riv,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default_avatar));
            tvContent.setText(bean.getQuestionInfo());
            tvTime.setText(bean.getQuestionDate()+"");
        }
    }
}
