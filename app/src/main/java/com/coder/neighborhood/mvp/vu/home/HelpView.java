package com.coder.neighborhood.mvp.vu.home;

import com.coder.neighborhood.mvp.vu.base.RefreshView;

/**
 * Created by feng on 2018/1/8.
 */

public class HelpView extends RefreshView {

    @Override
    public void attach() {
        super.attach();
        qmuiEmptyView.setTitleText("暂无更多问题");
    }
}
