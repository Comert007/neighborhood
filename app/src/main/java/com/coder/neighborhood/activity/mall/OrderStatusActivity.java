package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.OrderStatusAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.CartBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.OrderStatusView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

/**
 * @author feng
 * @Date 2018/1/9
 */

public class OrderStatusActivity extends BaseActivity<OrderStatusView,MallModel> {


    private OrderStatusAdapter adapter;
    private int page = 1;


    public static void start(Context context) {
        Intent intent = new Intent(context, OrderStatusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_status;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData(){
        adapter = new OrderStatusAdapter(this);
        v.getCrv().setAdapter(adapter);
        onGoodsOrders("0");
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
                bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<List<CartBean>>(this,true) {
                    @Override
                    public void onNext(List<CartBean> cartBeans) {

                    }
                });
    }
}
