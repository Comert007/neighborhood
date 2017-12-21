package com.coder.neighborhood.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.model.IModel;
import com.coder.neighborhood.mvp.presenter.PresenterActivity;
import com.coder.neighborhood.mvp.vu.IView;
import com.coder.neighborhood.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * @author feng
 * @Date 2017/12/21.
 */

public abstract class BaseActivity<V extends IView,M extends IModel> extends PresenterActivity<V,M> {

    protected LoadingDialog loadingDialog;

    protected BaseApplication baseApp;

    @Nullable
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        baseApp = BaseApplication.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Optional
    @OnClick({R.id.btn_title_left, R.id.btn_title_right})
    public void onTitleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_title_left:
                onTitleLeft();
                break;
            case R.id.btn_title_right:
                onTitleRight();
                break;
            default:
                break;
        }
    }

    public void onTitleLeft() {

    }

    public void onTitleRight() {

    }

    @Optional
    public void setTitleText(String titleText){
        if (tvTitle!=null){
            tvTitle.setText(titleText);
        }
    }

}
