package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.home.GoodsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class MallAdapter extends RvAdapter<GoodsBean> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_CONTENT = 2;

    public MallAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        if (viewType == TYPE_TITLE) {
            return R.layout.item_mall_title;
        } else {
            return R.layout.item_mall_content;
        }
    }

    @Override
    protected RvViewHolder<GoodsBean> getViewHolder(int viewType, View view) {
        if (viewType == TYPE_TITLE) {
            return new MallTitleViewHolder(view);
        } else {
            return new MallContentViewHolder(view);
        }

    }

    class MallTitleViewHolder extends RvViewHolder<GoodsBean> {



        public MallTitleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, GoodsBean bean) {

        }
    }

    class MallContentViewHolder extends RvViewHolder<GoodsBean> {
        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_title)
        TextView tvGoodsTitle;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_type)
        TextView tvGoodsType;
        @BindView(R.id.tv_goods_use)
        TextView tvGoodsUse;
        public MallContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, GoodsBean bean) {
            ImageLoader.getInstance().displayImage(bean.getImgUrl(), ivGoodsImage,
                    BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            tvGoodsTitle.setText(TextUtils.isEmpty(bean.getItemName())?"":bean.getItemName());
            tvGoodsPrice.setText(TextUtils.isEmpty(bean.getItemPrice())?"":bean.getItemPrice());
            tvGoodsType.setText(TextUtils.isEmpty(bean.getItemCategoryName())?"":bean.getItemCategoryName());
            tvGoodsUse.setText(TextUtils.isEmpty(bean.getItemPickingPrice())?"":bean.getItemPickingPrice());
        }
    }
}
