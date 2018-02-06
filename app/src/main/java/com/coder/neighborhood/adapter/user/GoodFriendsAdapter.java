package com.coder.neighborhood.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.user.FriendsInfoActivity;
import com.coder.neighborhood.bean.user.FriendBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.RoundImageView;

/**
 * @author feng
 * @date 2018/1/9
 */

public class GoodFriendsAdapter extends RvAdapter<FriendBean> {


    public GoodFriendsAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_good_friends;
    }

    @Override
    protected RvViewHolder<FriendBean> getViewHolder(int viewType, View view) {
        return new GoodFriendsViewHolder(view);
    }

    class GoodFriendsViewHolder extends RvViewHolder<FriendBean>{
        @BindView(R.id.riv)
        RoundImageView riv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_grade)
        TextView tvGrade;

        public GoodFriendsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, FriendBean bean) {
            ImageLoader.getInstance().displayImage(bean.getImgUrl(),riv, BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            tvName.setText(TextUtils.isEmpty(bean.getNickName())?"未设置":bean.getNickName());

            itemView.setOnClickListener(v -> {
                FriendsInfoActivity.start(getContext(),bean);
            });
        }
    }
}
