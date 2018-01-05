package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.home.LivingListView;

/**
 * @author feng
 * @Date 2018/1/5.
 */

@SuppressLint("Registered")
public class LivingListActivity extends BaseActivity<LivingListView,VoidModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_living_list;
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
