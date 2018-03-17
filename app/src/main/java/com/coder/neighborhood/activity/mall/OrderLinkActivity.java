package com.coder.neighborhood.activity.mall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.OrderLinkAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.user.OrderBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.OrderStatusView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @Author feng
 * @Date 2018/3/17
 */

public class OrderLinkActivity extends BaseActivity<OrderStatusView,MallModel> {

    /**
     *
     * 订单状态(1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',)
     */
    private OrderLinkAdapter adapter;
    private int page = 1;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;


    private int status;

    public static void start(Context context, int status) {
        Intent intent = new Intent(context, OrderLinkActivity.class);
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
        adapter = new OrderLinkAdapter(this);
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

        adapter.setOnActionListener((position, view) -> {
            OrderBean orderBean = adapter.getItem(position);
            dealOrder(orderBean);
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


                        v.getCsr().setRefreshFinished();
                        if (orderBeans != null && orderBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(orderBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(orderBeans);
                            }

                            if (orderBeans.size() == Constants.PAGE_SIZE) {
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


    /**
     * 对订单进行处理
     * @param orderBean
     */
    private void dealOrder(OrderBean orderBean){
        String orderStatus = orderBean.getOrderStatus();
        switch (orderStatus){
            case "1":
                payOrder(orderBean);
                break;
            case "2":
                urgeOrder(orderBean);
                break;
            case "3":
                confirmOrder(orderBean);
                break;
        }
    }

    /**
     * 确认订单
     * @param orderBean 订单实例
     */
    private void confirmOrder(OrderBean orderBean){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            m.confirmOrder(user.getUserId(), orderBean.getOrderId(), bindUntilEvent(ActivityEvent.DESTROY),
                    new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            onGoodsOrders(status+"");
                        }
                    });
        }
    }

    /**
     * 订单再支付
     * @param orderBean
     */
    private void payOrder(OrderBean orderBean){

    }

    /**
     * 催单
     * @param orderBean
     */
    private void urgeOrder(OrderBean orderBean){

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
