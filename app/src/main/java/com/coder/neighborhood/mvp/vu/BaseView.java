package com.coder.neighborhood.mvp.vu;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author feng
 * @Date 2017/12/21.
 */

public class BaseView implements IView{

    protected Activity preActivity;
    protected View rootView;

    @Override
    public void onAttach(@NonNull Activity preActivity, @NonNull View contentView) {
        this.preActivity = preActivity;
        this.rootView = contentView;

        ButterKnife.bind(this, contentView);
    }

    /**
     * @return
     * @version 1.0.2
     */
    public Resources getResources() {
        return preActivity.getResources();
    }

    /**
     * @param resId
     * @return
     * @version 1.0.2
     */
    @NonNull
    public String getString(int resId) {
        return preActivity.getString(resId);
    }

    /**
     * 获得宿主Activity
     *
     * @return
     */
    @NonNull
    public Activity getPreActivity() {
        return preActivity;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        preActivity = null;
        rootView = null;
    }
}
