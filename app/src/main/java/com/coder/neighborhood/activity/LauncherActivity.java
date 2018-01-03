package com.coder.neighborhood.activity;

import android.os.Handler;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.user.LoginActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 * Created by feng on 2017/12/23.
 */

public class LauncherActivity extends BaseActivity<VoidView,VoidModel>{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.start(LauncherActivity.this);
                finish();
            }
        },2000);
    }
}
