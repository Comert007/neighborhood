package com.coder.neighborhood.adapter.mall;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.user.CommentGoodsActivity;
import com.coder.neighborhood.bean.user.OrderBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @date 2018/1/9
 */

public class OrderStatusAdapter extends RvAdapter<OrderBean> {

    public OrderStatusAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_order_status;
    }

    @Override
    protected RvViewHolder<OrderBean> getViewHolder(int viewType, View view) {
        return new CommentDetailViewHolder(view);
    }

    class CommentDetailViewHolder extends RvViewHolder<OrderBean> {
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
        @BindView(R.id.btn_order)
        Button btnOrder;

        public CommentDetailViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, OrderBean bean) {
            /**
             *
             * 订单状态(1、待付款，2、待发货，3、待收货，4、待评价、5、已完成)
             */
            ImageLoader.getInstance().displayImage(bean.getImgUrl(), iv, BaseApplication
                    .getDisplayImageOptions(R.mipmap.pic_default));
            tvGoodsName.setText(bean.getItemName());
            tvGoodsPrice.setText(bean.getOrderPayment() + "元" + (TextUtils.isEmpty(bean
                    .getItemGroupUnit()) ? "" : "/" + bean.getItemGroupUnit()));
            tvGoodsNum.setText(TextUtils.isEmpty(bean.getBuyCount()) ? "" : "x" + bean
                    .getBuyCount());
            showStatus(bean.getOrderStatus());

            btnOrder.setOnClickListener(v -> {
                if ("4".equals(bean.getOrderStatus())) {
                    CommentGoodsActivity.start((Activity) getContext(), bean.getItemId());
                }
            });


        }

        private void showStatus(String status) {
            switch (status) {
                case "1":
                    btnOrder.setText("待付款");
                    break;
                case "2":
                    btnOrder.setText("催单");
                    break;
                case "3":
                    btnOrder.setText("确认收货");
                    break;
                case "4":
                    btnOrder.setText("评价");
                    break;
                case "5":
                    btnOrder.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }


        private void onClick() {

        }

    }
}
