package com.coder.neighborhood.activity.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.CartAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.CartBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.listener.OnActionListener;
import com.coder.neighborhood.mvp.listener.OnItemClickListener;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.CartView;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.IconFontTextView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;

/**
 * @Author feng
 * @Date 2018/1/20
 */

@SuppressLint("Registered")
public class CartActivity extends BaseActivity<CartView,MallModel> {

    @BindView(R.id.itv_whole)
    IconFontTextView itvWhole;


    private CartAdapter adapter;
    private int page;
    private QMUIDialog quiteDialog;

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
        adapter = new CartAdapter(this);
        v.getCrv().setAdapter(adapter);
        initListener();
        onCartGoods();
    }


    private void initListener(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position, View view) {
               CartBean cartBean = adapter.getItem(position);
               cartBean.setCheck(!cartBean.isCheck());
               setWhole();
               adapter.notifyItemChanged(position);
            }
        });

        adapter.setOnActionListener(new OnActionListener() {
            @Override
            public void onAction(int position, View view) {
                CartBean cartBean = adapter.getItem(position);
                showDialog(cartBean);
            }
        });
    }


    private void setWhole(){
        if (isWhole()){
            if (isWhole()){
                itvWhole.setText("\ue6a5");
                itvWhole.setTextColor(ContextCompat.getColor(CartActivity.this,R.color.color_text));
            }else {
                itvWhole.setText("\ue678");
                itvWhole.setTextColor(ContextCompat.getColor(CartActivity.this,R.color.color_aaaaaa));
            }
        }
    }

    private boolean isWhole(){
        boolean isOk = true;
        List<CartBean> cartBeans = adapter.getList();
        for (CartBean cartBean : cartBeans) {
            if (!cartBean.isCheck()){
               isOk = false;
            }
        }
        return isOk;
    }


    private void onCartGoods(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null){
            ToastUtils.showToast("用户信息有误");
            return;
        }

        m.cartGoods(user.getUserId(), "1", page+"",
                Constants.PAGE_SIZE+"", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<List<CartBean>>(this,true) {
                    @Override
                    public void onNext(List<CartBean> cartBeans) {
                        adapter.addList(cartBeans);
                    }
                });

    }


    private void onDeleteGoods(CartBean cartBean){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null){
            ToastUtils.showToast("用户信息有误");
            return;
        }

        m.deleteCart(user.getUserId(), cartBean.getCartId(), bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<String>(this,true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast("删除商品成功");
                        onCartGoods();
                    }
                });
    }

    private void showDialog(final CartBean cartBean ){
        if (quiteDialog ==null){
            QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(this);
            builder.setTitle("提示");
            builder.setMessage("确定删除当前商品？");
            builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                }
            });

            builder.addAction("删除", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                   onDeleteGoods(cartBean);
                    dialog.dismiss();
                }
            });
            quiteDialog = builder.create();
        }

        if (quiteDialog.isShowing()){
            quiteDialog.dismiss();
        }else {
            quiteDialog.show();
        }
    }

}
