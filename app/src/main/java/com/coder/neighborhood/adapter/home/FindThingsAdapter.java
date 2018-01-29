package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.home.ThingDetailActivity;
import com.coder.neighborhood.bean.home.ThingsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.utils.TimeUtils;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2018/1/8.
 */

public class FindThingsAdapter extends RvAdapter<ThingsBean> {

    public FindThingsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_find_things;
    }

    @Override
    protected RvViewHolder<ThingsBean> getViewHolder(int viewType, View view) {
        return new HelpViewHolder(view);
    }

    class  HelpViewHolder extends RvViewHolder<ThingsBean>{
        @BindView(R.id.riv)
        RoundImageView riv;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_phone)
        TextView tvPhone;


        public HelpViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, ThingsBean bean) {

            ImageLoader.getInstance().displayImage(bean.getHeaderImgUrl(),riv,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default_avatar));

            tvContent.setText(bean.getLostInfo());
            tvTime.setText(TimeUtils.milliseconds2String(bean.getLostDate(),new SimpleDateFormat("yyyy.MM.dd")));
            tvPhone.setText("联系方式："+bean.getLostPhone());

            itemView.setOnClickListener(v -> ThingDetailActivity.start(getContext(),bean.getLostId()));
        }
    }
}
