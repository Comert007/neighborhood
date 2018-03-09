package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.CartFlagBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/3/7.
 */

public class CommitOrderAdapter extends RvAdapter<CartFlagBean> {

    public CommitOrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_commit_order;
    }

    @Override
    protected RvViewHolder<CartFlagBean> getViewHolder(int viewType, View view) {
        return new CommitOrderViewHolder(view);
    }

    class CommitOrderViewHolder extends RvViewHolder<CartFlagBean> {

        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_type)
        TextView tvGoodsType;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;

        public CommitOrderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CartFlagBean bean) {
            ImageLoader.getInstance().displayImage(bean.getImgUrl(), iv, BaseApplication
                    .getDisplayImageOptions(R.mipmap.pic_default));
            tvGoodsName.setText(bean.getItemName());
            tvGoodsNum.setText("数量："+bean.getBuyCount());
            tvGoodsPrice.setText(bean.getItemPrice());
        }
    }
}
