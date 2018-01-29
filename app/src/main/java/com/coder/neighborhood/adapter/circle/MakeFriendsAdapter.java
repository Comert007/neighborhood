package com.coder.neighborhood.adapter.circle;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.adapter.user.ImageAdapter;
import com.coder.neighborhood.bean.circle.CircleBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * @Author feng
 * @Date 2018/1/9
 */

public class MakeFriendsAdapter extends RvAdapter<CircleBean> {

    public MakeFriendsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_make_friends;
    }

    @Override
    protected RvViewHolder<CircleBean> getViewHolder(int viewType, View view) {
        return new GoodFriendsViewHolder(view);
    }

    class GoodFriendsViewHolder extends RvViewHolder<CircleBean>{
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
        @BindView(R.id.tv_prise)
        TextView tvPrise;


        @BindView(R.id.rv_comment)
        RecyclerView rvComment;
        @BindView(R.id.rv_images)
        RecyclerView rvImages;

        public GoodFriendsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CircleBean bean) {
            ImageAdapter adapter = new ImageAdapter(getContext());
            GridLayoutManager manager = new GridLayoutManager(getContext(),3);
            rvImages.setLayoutManager(manager);
            rvImages.setAdapter(adapter);
            List<String> images = new ArrayList<>();
            images.add(bean.getImgUrl());
            adapter.addList(images);

            CircleCommentAdapter commentAdapter = new CircleCommentAdapter(getContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            rvComment.setLayoutManager(layoutManager);
            rvComment.setAdapter(commentAdapter);

            commentAdapter.addList(bean.getComments());

            ImageLoader.getInstance().displayImage(bean.getHeadImg(),riv,
                    BaseApplication.getDisplayImageOptions(R.mipmap.ic_default_avatar));
            tvName.setText(bean.getNickName());
            tvSubScribe.setText(bean.getCircleInfo());
//            tvSubScribe.setText(TimeUtils.milliseconds2String(bean.getCircleDate(),new SimpleDateFormat("yyyy.MM.dd")));
            tvPrise.setText(TextUtils.isEmpty(bean.getCircleLike())?"0":bean.getCircleLike());

        }
    }


}
