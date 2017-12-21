package com.coder.neighborhood;

import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

public  class MainActivity extends BaseActivity<VoidView,VoidModel> {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }
}
