package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.coder.neighborhood.BaseApplication;
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

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @date 2018/1/9
 */

public class GoodFriendsActivity extends BaseActivity<GoodFriendsView, UserModel> {

    @BindView(R.id.et_search)
    EditText etSearch;

    private GoodFriendsAdapter adapter;
    private int page = 1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodFriendsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_good_friends;
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

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH)
            {

                String searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)){
                    ToastUtils.showToast("输入搜索内容");
                    return false;
                }
                MakeFriendsActivity.start(GoodFriendsActivity.this,searchContent);
            }
            return false;
        });
    }

    @OnClick(R.id.iv_search)
    public void onSearch(){
        String searchContent = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchContent)){
            ToastUtils.showToast("输入搜索内容");
            return;
        }
        MakeFriendsActivity.start(GoodFriendsActivity.this,searchContent);
    }

    private void onFriends() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null) {
            ToastUtils.showToast("用户信息有误");
            return;
        }
        m.friends(user.getUserId(), page + "", Constants.PAGE_SIZE + "",
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
