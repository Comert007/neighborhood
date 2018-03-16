package com.coder.neighborhood.activity.mall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.OrderStatusAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.user.OrderBean;
import com.coder.neighborhood.bean.user.OrderComposeBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.OrderStatusView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/9
 */

public class OrderStatusActivity extends BaseActivity<OrderStatusView,MallModel> {


    /**
     *
     * 订单状态(1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',)
     */
    private OrderStatusAdapter adapter;
    private int page = 1;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;


    private int status;

    public static void start(Context context,int status) {
        Intent intent = new Intent(context, OrderStatusActivity.class);
        intent.putExtra("status",status);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_status;
    }

    @Override
    protected void init() {
        status = getIntent().getIntExtra("status",0);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new OrderStatusAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                onGoodsOrders(status+"");
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onGoodsOrders(status+"");
            }
        });

    }

    private void initData(){
        crv.setAdapter(adapter);
        setTitleText(getTitle(status));
    }

    private String getTitle(int status){
        String title = "我的订单";
        if (status == 1){
            title = "待付款";
        }else if (status ==2){
            title = "待发货";
        }else if (status ==3){
            title = "待收货";
        }else if (status ==4){
            title = "待评价";
        }else if (status ==5){
            title = "已完成";
        }

        return title;
    }


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void onGoodsOrders(String orderStatus){

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null){
            ToastUtils.showToast("用户信息有误");
            return;
        }

        m.goodsOrders(user.getUserId(), orderStatus, page + "", Constants.PAGE_SIZE + "",
                bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<List<OrderBean>>(this,true) {
                    @Override
                    public void onNext(List<OrderBean> orderBeans) {

                        List<OrderComposeBean> composeBeans = orderList(orderBeans);

                        v.getCsr().setRefreshFinished();
                        if (composeBeans != null && composeBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(composeBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(composeBeans);
                            }

                            if (composeBeans.size() == Constants.PAGE_SIZE) {
                                page++;
                            } else {
                                v.getCsr().setFooterRefreshAble(false);
                            }
                        } else {
                            v.getCsr().setFooterRefreshAble(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        v.getCsr().setRefreshFinished();
                    }
                });
    }

    private List<OrderComposeBean> orderList(List<OrderBean> orderBeans){
        List<OrderComposeBean> orderComposeBeans = new ArrayList<>();

        for (OrderBean orderBean : orderBeans) {
            List<OrderBean.ItemsBean> items = orderBean.getItems();
            for (OrderBean.ItemsBean item : items) {
                OrderComposeBean composeBean = new OrderComposeBean();
                composeBean.setOrderId(orderBean.getOrderId());
                composeBean.setOrderPayment(orderBean.getOrderPayment());
                composeBean.setOrderStatus(orderBean.getOrderStatus());
                composeBean.setBuyCount(item.getBuyCount());
                composeBean.setImgUrl(item.getImgUrl());
                composeBean.setItemGroupUnit(item.getItemGroupUnit());
                composeBean.setItemId(item.getItemId());
                composeBean.setItemName(item.getItemName());
                composeBean.setItemPrice(item.getItemPrice());
                orderComposeBeans.add(composeBean);
            }
        }

        return orderComposeBeans;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            page = 1;
            v.getCsr().setFooterRefreshAble(true);
            csr.setFooterRefreshAble(false);
            onGoodsOrders(status+"");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGoodsOrders(status+"");
    }
}
