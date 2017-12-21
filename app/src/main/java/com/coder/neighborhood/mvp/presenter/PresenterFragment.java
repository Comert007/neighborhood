package com.coder.neighborhood.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coder.neighborhood.mvp.WWApplication;
import com.coder.neighborhood.mvp.manager.IUserInfo;
import com.coder.neighborhood.mvp.manager.IUserInfoManager;
import com.coder.neighborhood.mvp.model.IModel;
import com.coder.neighborhood.mvp.vu.IView;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * @author feng
 * @Date 2017/12/21.
 */
public abstract class PresenterFragment<V extends IView, M extends IModel> extends Fragment
        implements IPresenter, IUserInfoManager {

    protected V v;
    protected M m;

    private View contentView;

    public PresenterFragment() {
        PresenterHelper.bind(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getLayoutResId(), container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v.onAttach(getActivity(), view);
        m.onAttach(getActivity());

        ScreenUtil.scale(view);
        ButterKnife.bind(this, view);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            v.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contentView = null;
        v.onDestroy();
    }

    protected abstract int getLayoutResId();

    protected abstract void init();

    @Override
    public Activity getPresenterActivity() {
        return getActivity();
    }

    @Override
    public M getModelModule() {
        return this.m;
    }

    @Override
    public void setModelModule(IModel modelModule) {
        this.m = (M) modelModule;
    }

    @Override
    public V getViewModule() {
        return this.v;
    }

    @Override
    public void setViewModule(IView viewModule) {
        this.v = (V) viewModule;
    }

    @Override
    public void clearUserInfo() {
        getWWApplication().clearUserInfo();
    }

    @Override
    public void saveUserInfo(IUserInfo userBean) {
        getWWApplication().saveUserInfo(userBean);
    }

    @Override
    public IUserInfo getUserInfo() {
        return getWWApplication().getUserInfo();
    }

    @Override
    public boolean isLogin() {
        return getWWApplication().isLogin();
    }

    @Override
    public String getToken() {
        return getWWApplication().getToken();
    }

    private WWApplication getWWApplication() {
        return (WWApplication) getActivity().getApplication();
    }
}
