package com.coder.neighborhood.mvp.vu.user;

import com.coder.neighborhood.mvp.vu.base.RefreshView;

/**
 *
 * @author feng
 * @date 2018/1/9
 */

public class GoodFriendsView extends RefreshView {

    @Override
    public void attach() {
        super.attach();
        qmuiEmptyView.setTitleText("暂无更多好友");
    }
}
