package com.coder.neighborhood.activity.circle;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.circle.MakingFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.circle.MakingFriendsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.aop.CheckUser;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.base.RefreshView;

import java.util.List;

import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/3/16.
 */

public class MakingFriendsActivity extends BaseActivity<RefreshView,CircleModel> {

    private MakingFriendsAdapter adapter;

    private int page = 1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private final int REQUEST_CODE = 0x21;


    public static void start(Context context) {
        Intent intent = new Intent(context, MakingFriendsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_making_friends;
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initView() {
        adapter = new MakingFriendsAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                csr.setEnableRefresh(true);
                csr.setFooterRefreshAble(false);
                makingFriends();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                makingFriends();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        makingFriends();
    }

    private void makingFriends(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user !=null && !TextUtils.isEmpty(user.getUserId())){
            m.makingFriends(user.getUserId(), page + "", Constants.PAGE_SIZE + "",
                    new HttpSubscriber<List<MakingFriendsBean>>(this,false) {

                        @Override
                        public void onNext(List<MakingFriendsBean> makingFriendsBeans) {
                            v.getCsr().setRefreshFinished();
                            if (makingFriendsBeans != null && makingFriendsBeans.size() > 0) {
                                if (page == 1) {
                                    adapter.addList(makingFriendsBeans);
                                    csr.setFooterRefreshAble(true);
                                } else {
                                    v.getCrv().removeFooterView(v.getFooterView());
                                    adapter.appendList(makingFriendsBeans);
                                }

                                if (makingFriendsBeans.size() == Constants.PAGE_SIZE) {
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

    @CheckUser
    @OnClick(R.id.btn_ask)
    public void onPublish(){
        PublishMakingFriendsActivity.startForResult(getPresenterActivity(),REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            page = 1;
            makingFriends();
        }
    }
}
