package com.coder.neighborhood.mvp.presenter;

import android.app.Activity;

import com.coder.neighborhood.mvp.model.IModel;
import com.coder.neighborhood.mvp.vu.IView;


/**
 * @author feng
 * @Date 2017/12/21.
 */
public interface IPresenter {


    Activity getPresenterActivity();

    /**
     * 获取数据层
     * <br />
     * 添加module后缀为了区分
     *
     * @return
     */
    IModel getModelModule();

    void setModelModule(IModel modelModule);

    /**
     * 获取UI渲染层
     * <br />
     * 添加module后缀为了区分
     *
     * @return
     */
    IView getViewModule();

    void setViewModule(IView viewModule);
}
