package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.home.HelpDetailBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.utils.TimeUtils;
import ww.com.core.widget.RoundImageView;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class HelpDetailAdapter extends RvAdapter<HelpDetailBean> {

    public HelpDetailAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_help_detail;
    }

    @Override
    protected RvViewHolder<HelpDetailBean> getViewHolder(int viewType, View view) {
        return new HelpDetailViewHolder(view);
    }

    class HelpDetailViewHolder extends RvViewHolder<HelpDetailBean>{
        @BindView(R.id.riv)
        RoundImageView riv;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_bounty)
        TextView tvBounty;



        public HelpDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, HelpDetailBean bean) {
            ImageLoader.getInstance().displayImage(bean.getHeadImgUrl(),riv,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default_avatar));

            tvContent.setText(bean.getCommentsContent());
            tvTime.setText(TimeUtils.milliseconds2String(bean.getCommentsDate(),new SimpleDateFormat("yyyy.MM.dd")));


        }
    }
}
