package com.coder.neighborhood.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.listener.OnActionListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/1/16
 */

public class ImageAdapter extends RvAdapter<String> {

    private OnActionListener onActionListener;

    public ImageAdapter(Context context) {
        super(context);
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
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

        @BindView(R.id.iv)
        ImageView iv;

        public ImageViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {
            iv.setVisibility(TextUtils.isEmpty(bean)?View.GONE:View.VISIBLE);
            ImageLoader.getInstance().displayImage(bean,iv, BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));

            iv.setOnClickListener(v -> {
                if (onActionListener!=null) {
                    onActionListener.onAction(position, v);
                }
            });
        }
    }
}
