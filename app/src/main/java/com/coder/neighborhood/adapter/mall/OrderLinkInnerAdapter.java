package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.user.OrderItemBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @date 2018/1/9
 */

public class OrderLinkInnerAdapter extends RvAdapter<OrderItemBean> {

    public OrderLinkInnerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_order_link_inner;
    }

    @Override
    protected RvViewHolder<OrderItemBean> getViewHolder(int viewType, View view) {
        return new CommentDetailViewHolder(view);
    }

    class CommentDetailViewHolder extends RvViewHolder<OrderItemBean> {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_type)
        TextView tvGoodsType;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;

        public CommentDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, OrderItemBean bean) {
            /**
             *
             * 订单状态(1、待付款，2、待发货，3、待收货，4、待评价、5、已完成)
             */
            ImageLoader.getInstance().displayImage(bean.getImgUrl(), iv, BaseApplication
                    .getDisplayImageOptions(R.mipmap.pic_default));

            tvGoodsName.setText(bean.getItemName());
            tvGoodsPrice.setText(bean.getItemPrice() + "元" + (TextUtils.isEmpty(bean
                    .getItemGroupUnit()) ? "" : "/" + bean.getItemGroupUnit()));
            tvGoodsNum.setText(TextUtils.isEmpty(bean.getBuyCount()) ? "" : "x" + bean
                    .getBuyCount());

        }

    }
}
