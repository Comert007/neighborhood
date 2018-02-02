package com.coder.neighborhood.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.home.FeedBackQuestionActivity;
import com.coder.neighborhood.activity.mall.CartActivity;
import com.coder.neighborhood.activity.mall.OrderStatusActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.activity.user.AboutUsActivity;
import com.coder.neighborhood.activity.user.GoodFriendsActivity;
import com.coder.neighborhood.activity.user.LoginActivity;
import com.coder.neighborhood.activity.user.UserInfoActivity;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/12/23.
 */

public class UserFragment extends BaseFragment<VoidView, UserModel> {

    @BindView(R.id.riv_header)
    RoundImageView rivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_grade)
    TextView tvGrade;

    private QMUIDialog quiteDialog;
    private QMUIDialog signDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null) {
            ImageLoader.getInstance().displayImage(user.getImgUrl(), rivHeader, BaseApplication
                    .getDisplayImageOptions(R.mipmap.ic_default_avatar));
            tvName.setText(TextUtils.isEmpty(user.getNickName())?user.getPhone()+"用户":user.getNickName());
            tvGrade.setText("LV.0");
        }
    }

    @OnClick({R.id.btn_loginout,R.id.ll_sign,R.id.rl_about_us,R.id.ll_cart,R.id.ll_order,
    R.id.rl_feedback})
    public void onUser(View v){
        switch (v.getId()){
            case R.id.btn_loginout:
                showDialog();
                break;
            case R.id.ll_sign:
                querySign();
                break;
            case R.id.rl_about_us:
                AboutUsActivity.start(getContext());
                break;
            case R.id.ll_user_info:
                UserInfoActivity.start(getContext());
                break;
            case R.id.ll_cart:
                CartActivity.start(getContext());
                break;
            case R.id.ll_order:
                OrderStatusActivity.start(getContext());
                break;
            case R.id.rl_feedback:
                FeedBackQuestionActivity.start(getContext());
                break;
        }
    }

    private void showDialog(){
        if (quiteDialog ==null){
            QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(getContext());
            builder.setTitle("提示");
            builder.setMessage("确定退出登录？");
            builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                }
            });

            builder.addAction("退出", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    BaseApplication.getInstance().saveUserInfo(null);
                    BaseApplication.getInstance().exitApp();
                    LoginActivity.start(getContext());
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


    private void showSign(String count){
        if (signDialog ==null){
            QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(getContext());
            builder.setTitle("签到记录");
            builder.setMessage("本月签到"+count+"天");
            builder.addAction("签到", new QMUIDialogAction.ActionListener() {
                @Override
                public void onClick(QMUIDialog dialog, int index) {
                    dialog.dismiss();
                   signIn();
                }
            });

            signDialog = builder.create();
        }

        if (signDialog.isShowing()){
            signDialog.dismiss();
        }else {
            signDialog.show();
        }
    }


    private void signIn(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        m.signIn(user.getUserId(), new HttpSubscriber<ResponseBean>(getContext(),false) {
            @Override
            public void onNext(ResponseBean responseBean) {
                ToastUtils.showToast(responseBean.getMessage());
            }
        });
    }

    private void querySign(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        m.querySign(user.getUserId(), new HttpSubscriber<String>(getContext(),true) {
            @Override
            public void onNext(String s) {
                if (TextUtils.isEmpty(s)){
                    s= "1";
                }
                showSign(s);
            }

        });
    }

    @OnClick({R.id.ll_wait_pay,R.id.ll_wait_send,R.id.ll_wait_gain,R.id.ll_wait_comment,R.id.ll_return_goods,
    R.id.ll_good_friends})
    public void onUserClick(View v){
        switch (v.getId()){
            case R.id.ll_wait_pay:
                OrderStatusActivity.start(getContext());
                break;
            case R.id.ll_wait_send:
                OrderStatusActivity.start(getContext());
                break;
            case R.id.ll_wait_gain:
                OrderStatusActivity.start(getContext());
                break;
            case R.id.ll_wait_comment:
                OrderStatusActivity.start(getContext());
                break;
            case R.id.ll_return_goods:
                OrderStatusActivity.start(getContext());
                break;
            case R.id.ll_good_friends:
                GoodFriendsActivity.start(getContext());
                break;
        }
    }
}
