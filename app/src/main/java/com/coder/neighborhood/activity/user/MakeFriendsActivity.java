package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.user.GoodFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.user.FriendBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.user.GoodFriendsView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @date 2018/1/9
 */

public class MakeFriendsActivity extends BaseActivity<GoodFriendsView, UserModel> {

    private GoodFriendsAdapter adapter;
    private int page = 1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private String username;

    public static void start(Context context,String username) {
        Intent intent = new Intent(context, MakeFriendsActivity.class);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_make_friends;
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }
    private void initView() {
        adapter = new GoodFriendsAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }


    private void initData() {
        username = getIntent().getStringExtra("username");
        crv.setAdapter(adapter);
        onFriends();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                onFriends();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onFriends();
            }
        });
    }

    private void onFriends() {
        UserBean user = (UserBean) baseApp.getUserInfo();
        if (user == null || TextUtils.isEmpty(user.getUserId())){
            ToastUtils.showToast("用户信息有误");
            return;
        }
        m.searchFriends(user.getUserId(),username, page + "", Constants.PAGE_SIZE + "",
                bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<List<FriendBean>>(this,
                        true) {
                    @Override
                    public void onNext(List<FriendBean> friendBeans) {
                        v.getCsr().setRefreshFinished();
                        if (friendBeans != null && friendBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(friendBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(friendBeans);
                            }

                            if (friendBeans.size() == Constants.PAGE_SIZE) {
                                page++;
                            } else {
                                v.getCsr().setFooterRefreshAble(false);
                            }
                        } else {
                            v.getCsr().setFooterRefreshAble(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        v.getCsr().setRefreshFinished();
                    }
                });

    }
}
