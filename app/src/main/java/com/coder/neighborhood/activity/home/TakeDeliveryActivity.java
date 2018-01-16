package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 * @Author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class TakeDeliveryActivity extends BaseActivity<VoidView,VoidModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, TakeDeliveryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_take_delivery;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }
}
