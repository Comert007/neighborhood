package com.coder.neighborhood.adapter.circle;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.circle.EventDetailActivity;
import com.coder.neighborhood.activity.circle.ImageShowActivity;
import com.coder.neighborhood.adapter.user.ImageAdapter;
import com.coder.neighborhood.bean.circle.EventBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.utils.TimeUtils;
import ww.com.core.widget.RoundImageView;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class EventAdapter extends RvAdapter<EventBean>{

    public EventAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_event;
    }

    @Override
    protected RvViewHolder<EventBean> getViewHolder(int viewType, View view) {
        return new EventViewHolder(view);
    }

    class EventViewHolder extends RvViewHolder<EventBean>{
        @BindView(R.id.riv_header)
        RoundImageView riv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_subscribe)
        TextView tvSubScribe;
        @BindView(R.id.tv_grade)
        TextView tvGrade;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.rv_images)
        RecyclerView rvImages;


        public EventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, EventBean bean) {
            ImageAdapter adapter = new ImageAdapter(getContext());
            GridLayoutManager manager = new GridLayoutManager(getContext(),3);
            rvImages.setLayoutManager(manager);
            rvImages.setAdapter(adapter);
            List<String> images = new ArrayList<>();
            images.add(bean.getImgUrl());
            adapter.setOnActionListener((position1, view) -> {
                ImageShowActivity.start(getContext(),position1, (ArrayList<String>) images);
            });
            adapter.addList(images);

            ImageLoader.getInstance().displayImage(bean.getHeadImg(),riv,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default_avatar));
            tvName.setText(bean.getNickName());
            tvContent.setText(bean.getActivityInfo());
            if (bean.getActivityDate()!=0){
                tvSubScribe.setText(TimeUtils.milliseconds2String(bean.getActivityDate(),new SimpleDateFormat("yyyy.MM.dd")));
            }

            itemView.setOnClickListener(v -> EventDetailActivity.start(getContext(),bean.getActivityId()));
        }
    }
}
