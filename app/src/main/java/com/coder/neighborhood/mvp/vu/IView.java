package com.coder.neighborhood.mvp.vu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author feng
 * @Date 2017/12/21.
 */
public interface IView {
    /**
     * 建立依赖关系(创建)
     * View 必须依赖于Window才能进行显示。
     * 在android中Activity就是这个window
     *
     * @param preActivity 宿主Activity
     * @param contentView rootView
     */
    void onAttach(@NonNull Activity preActivity, @NonNull View contentView);

    /**
     * 运行
     */
    void onResume();

    /**
     * 销毁
     */
    void onDestroy();
}
