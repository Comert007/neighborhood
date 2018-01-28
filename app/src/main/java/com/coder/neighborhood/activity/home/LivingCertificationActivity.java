package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

@SuppressLint("Registered")
public class LivingCertificationActivity extends BaseActivity<VoidView,VoidModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, LivingCertificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_living_certification;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
