package com.coder.neighborhood.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.coder.neighborhood.mvp.WWApplication;
import com.coder.neighborhood.mvp.manager.IUserInfo;
import com.coder.neighborhood.mvp.manager.IUserInfoManager;
import com.coder.neighborhood.mvp.model.IModel;
import com.coder.neighborhood.mvp.vu.IView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * 支持RxLifecycle
 * 调度IView 和IModel 控制类
 *
 *
 * @author feng
 * @Date 2017/12/21.
 */

public abstract class PresenterActivity<V extends IView, M extends IModel>
        extends RxAppCompatActivity implements IPresenter, IUserInfoManager {

    WWApplication wwApp;

    protected V v;
    protected M m;

    public PresenterActivity() {
        PresenterHelper.bind(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wwApp = (WWApplication) getApplication();
        wwApp.addRunActivity(this);

        setContentView(getLayoutResId());
        View contentView = findViewById(android.R.id.content);
        v.onAttach(this, contentView);
        m.onAttach(this);

        ScreenUtil.scale(contentView);
        ButterKnife.bind(this);

        init();
    }

    /**
     * 获取 layout 资源id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     */
    protected abstract void init();


    @Override
    protected void onResume() {
        super.onResume();
        v.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        v.onDestroy();
        wwApp.removeRunActivity(this);
    }

    @Override
    public Activity getPresenterActivity() {
        return this;
    }

    @Override
    public M getModelModule() {
        return m;
    }

    @Override
    public void setModelModule(IModel modelModule) {
        this.m = (M) modelModule;
    }

    @Override
    public V getViewModule() {
        return v;
    }

    @Override
    public void setViewModule(IView viewModule) {
        this.v = (V) viewModule;
    }

    @Override
    public void clearUserInfo() {
        wwApp.clearUserInfo();
    }

    @Override
    public void saveUserInfo(IUserInfo userBean) {
        wwApp.saveUserInfo(userBean);
    }

    @Override
    public IUserInfo getUserInfo() {
        return wwApp.getUserInfo();
    }

    @Override
    public boolean isLogin() {
        return wwApp.isLogin();
    }

    @Override
    public String getToken() {
        return wwApp.getToken();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBord() {
        View v = getCurrentFocus();
        if (v == null){
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
