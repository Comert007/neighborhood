package com.coder.neighborhood.activity.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.CartAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.CartBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.CartView;
import com.coder.neighborhood.utils.DialogUtils;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.IconFontTextView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @Author feng
 * @Date 2018/1/20
 */

@SuppressLint("Registered")
public class CartActivity extends BaseActivity<CartView, MallModel> {

    @BindView(R.id.itv_whole)
    IconFontTextView itvWhole;


    private CartAdapter adapter;
    private int page = 1;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private QMUIDialog quiteDialog;

    private boolean isWhole = false;

    public static void start(Context context) {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cart;
    }

    @Override
    protected void init() {

        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new CartAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initListener() {
        adapter.setOnItemClickListener((position, view) -> {
            CartBean cartBean = adapter.getItem(position);
            cartBean.setCheck(!cartBean.isCheck());
            setWhole();
            adapter.notifyItemChanged(position);
        });

        adapter.setOnActionListener((position, view) -> {
            CartBean cartBean = adapter.getList().get(position);
            Debug.d("cardId:"+cartBean.getCartId());
            showDeleteDialog(cartBean.getCartId());
        });

        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                onCartGoods();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                onCartGoods();
            }
        });
    }


    private void initData() {
        crv.setAdapter(adapter);
        onCartGoods();
    }


    private void setWhole() {
        if (isWhole()) {
            itvWhole.setText("\ue677");
            itvWhole.setTextColor(ContextCompat.getColor(CartActivity.this, R.color.color_text));
            isWhole = true;
        } else {
            itvWhole.setText("\ue678");
            itvWhole.setTextColor(ContextCompat.getColor(CartActivity.this, R.color.color_aaaaaa));
            isWhole = false;
        }
    }

    private boolean isWhole() {
        boolean isOk = true;
        List<CartBean> cartBeans = adapter.getList();
        for (CartBean cartBean : cartBeans) {
            if (!cartBean.isCheck()) {
                isOk = false;
            }
        }
        return isOk;
    }


    @OnClick(R.id.itv_whole)
    public void modifyWhole() {
        isWhole = !isWhole;
        itvWhole.setText(isWhole ? "\ue677" : "\ue678");
        itvWhole.setTextColor(isWhole ? ContextCompat.getColor(this, R.color.color_text) :
                ContextCompat.getColor(this, R.color.color_aaaaaa));
        for (CartBean cartBean : adapter.getList()) {
            cartBean.setCheck(isWhole);
        }
        adapter.notifyDataSetChanged();
    }


    private void onCartGoods() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null) {
            ToastUtils.showToast("用户信息有误");
            return;
        }

        m.cartGoods(user.getUserId(), page + "",
                Constants.PAGE_SIZE + "", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<List<CartBean>>(this, true) {
                    @Override
                    public void onNext(List<CartBean> cartBeans) {
                        v.getCsr().setRefreshFinished();
                        if (cartBeans != null && cartBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(cartBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(cartBeans);
                            }

                            if (cartBeans.size() == Constants.PAGE_SIZE) {
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
                        v.getCsr().setRefreshFinished();
                        v.getCsr().setFooterRefreshAble(false);
                    }
                });

    }


    private void onDeleteGoods(String cardId) {
        Debug.d("cardId:"+cardId);
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null) {
            ToastUtils.showToast("用户信息有误");
            return;
        }

        m.deleteCart(user.getUserId(), cardId, bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<String>(this, true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast("删除商品成功");
                        page = 1;
                        onCartGoods();
                    }
                });
    }

    private void showDeleteDialog(String cardId) {
        DialogUtils.showDialog(this, "提示", "确定删除当前商品？", true,
                "删除", (dialog, which) -> {
                    Debug.d("cardId:"+cardId);
                    onDeleteGoods(cardId);
                    dialog.dismiss();
                }, true, "取消", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }

}
