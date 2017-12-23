package com.coder.neighborhood.activity.user;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 * Created by feng on 2017/12/23.
 */

public class LoginActivity extends BaseActivity<VoidView,VoidModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

    }
}
