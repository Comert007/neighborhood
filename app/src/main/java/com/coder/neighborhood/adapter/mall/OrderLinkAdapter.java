package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.user.OrderBean;
import com.coder.neighborhood.bean.user.OrderItemBean;
import com.coder.neighborhood.utils.ArithmeticUtils;

import java.util.List;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.widget.CustomRecyclerView;

/**
 * @Author feng
 * @Date 2018/3/17
 */

public class OrderLinkAdapter extends RvAdapter<OrderBean> {

    public OrderLinkAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_order_link;
    }

    @Override
    protected RvViewHolder<OrderBean> getViewHolder(int viewType, View view) {
        return new OrderLinkViewHolder(view);
    }

    class OrderLinkViewHolder extends RvViewHolder<OrderBean>{
        @BindView(R.id.crv)
        CustomRecyclerView crv;
        @BindView(R.id.tv_total_price)
        TextView tvTotalPrice;
        @BindView(R.id.btn_order)
        Button btnOrder;


        public OrderLinkViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, OrderBean bean) {

            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            crv.setLayoutManager(manager);
            OrderLinkInnerAdapter adapter = new OrderLinkInnerAdapter(getContext());
            adapter.addList(bean.getItems());
            crv.setAdapter(adapter);

            if (bean.getItems()!=null && bean.getItems().size()>0){
                tvTotalPrice.setText("总价："+getTotalPrice(bean.getItems()));
            }
            showStatus(bean.getOrderStatus());
        }


        private double getTotalPrice(List<OrderItemBean> beans){
            double price = 0;
            for (int i = 0; i < beans.size(); i++) {
                double perPrice = ArithmeticUtils.mul(beans.get(i)
                        .getItemPrice(), beans.get(i).getBuyCount())
                        .doubleValue();
                price = ArithmeticUtils.add(perPrice,price);
            }

            return price;
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
    }

}
