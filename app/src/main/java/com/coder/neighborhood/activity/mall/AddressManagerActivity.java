package com.coder.neighborhood.activity.mall;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

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
import com.coder.neighborhood.utils.DialogUtils;
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

    public static void start(Activity context) {
        Intent intent = new Intent(context, AddressManagerActivity.class);
        context.startActivityForResult(intent, 0x23);
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
    public void onTitleRight() {
        super.onTitleRight();
        AddAddressActivity.start(this);
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    private void initView() {
        setTitleRightText("管理");
        adapter = new AddressManagerAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }


    private void initListener() {
        adapter.setOnActionListener((position, view) -> {
            AddressBean bean = adapter.getItem(position);
            Intent intent = getIntent();
            intent.putExtra("address", bean);
            AddressManagerActivity.this.setResult(Activity.RESULT_OK, intent);
            finish();
        });

        adapter.setOnLongActionListener((position, view) -> {
            AddressBean address = adapter.getItem(position);
            showDeleteDialog(address);

        });

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

    private void onAddress() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null) {
            ToastUtils.showToast("用户信息有误");
            return;
        }
        m.getRecipientList(user.getUserId(), page + "", Constants.PAGE_SIZE + "", bindUntilEvent
                        (ActivityEvent.DESTROY)
                , new HttpSubscriber<List<AddressBean>>(this, false) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onAddress();
    }


    private void showDeleteDialog(AddressBean address) {

        DialogUtils.showDialog(this, "删除", "是否删除收件人地址", true,
                "删除", (dialog, which) -> {
                    deleteAddress(address);
                    dialog.dismiss();
                }, true, "取消", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

    private void deleteAddress(AddressBean address) {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            m.deleteRecipient(user.getUserId(), address.getRecipientId(), bindUntilEvent(ActivityEvent.DESTROY),
                    new HttpSubscriber<String>(this, true) {

                        @Override
                        public void onNext(String s) {
                            adapter.removeItem(address);
                            ToastUtils.showToast("删除成功",true);
                        }
                    });
        }
    }
}
