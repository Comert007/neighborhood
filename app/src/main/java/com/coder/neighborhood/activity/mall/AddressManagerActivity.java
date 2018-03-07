package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.AddressManagerAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.AddressBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.AddressManagerView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/3/7.
 */

public class AddressManagerActivity extends BaseActivity<AddressManagerView, MallModel> {

    private AddressManagerAdapter adapter;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private int page = 1;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_address_manager;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }


    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new AddressManagerAdapter(this);
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
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                onAddress();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onAddress();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        onAddress();
    }

    private void onAddress(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null) {
            ToastUtils.showToast("用户信息有误");
            return;
        }
        m.getRecipientList(user.getUserId(), page+"", Constants.PAGE_SIZE + "", bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<AddressBean>>(this,false) {
                    @Override
                    public void onNext(List<AddressBean> addressBeans) {
                        v.getCsr().setRefreshFinished();
                        if (addressBeans != null && addressBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(addressBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(addressBeans);
                            }

                            if (addressBeans.size() == Constants.PAGE_SIZE) {
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
                        adapter.getList().clear();
                        adapter.notifyDataSetChanged();
                        csr.setRefreshFinished();
                        csr.setFooterRefreshAble(false);
                    }
                });

    }
}
