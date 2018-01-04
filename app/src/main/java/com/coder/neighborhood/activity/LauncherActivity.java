package com.coder.neighborhood.activity;

import android.os.Handler;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.user.LoginActivity;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 *
 * @author feng
 * @date 2017/12/23
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
               enter();
            }
        },2000);
    }


    private void enter(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user ==null){
            LoginActivity.start(LauncherActivity.this);

        }else {
            MainActivity.start(this);
        }
        finish();
    }
}
