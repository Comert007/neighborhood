package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 * Created by feng on 2018/1/9.
 */

public class GoodsDetailActivity extends BaseActivity<VoidView,VoidModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void init() {

    }
}
