package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpDetailView;

/**
 * @author feng
 * @Date 2018/1/29.
 */

public class HelpDetailActivity extends BaseActivity<HelpDetailView,HomeModel> {


    public static void start(Context context) {
        Intent intent = new Intent(context, HelpDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_help_detail;
    }

    @Override
    protected void init() {

    }
}
