package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.mall.OrderStatusAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.mall.OrderStatusView;

import java.util.Arrays;

/**
 * @Author feng
 * @Date 2018/1/9
 */

public class OrderStatusActivity extends BaseActivity<OrderStatusView,VoidModel> {


    private OrderStatusAdapter adapter;


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
        adapter.addList(Arrays.asList("1","2","3"));
    }
}
