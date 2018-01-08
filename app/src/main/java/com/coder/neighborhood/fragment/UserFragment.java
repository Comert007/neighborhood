package com.coder.neighborhood.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.user.LoginActivity;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/12/23.
 */

public class UserFragment extends BaseFragment<VoidView, VoidModel> {

    @BindView(R.id.riv_header)
    RoundImageView rivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;

    private QMUIDialog dialog;


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
            tvName.setText("LV.0 "+(TextUtils.isEmpty(user.getNickName())?user.getPhone()+"用户":user.getNickName()));
        }
    }

    @OnClick({R.id.btn_loginout})
    public void onUser(View v){
        switch (v.getId()){
            case R.id.btn_loginout:
                showDialog();
                break;
        }
    }

    private void showDialog(){
        if (dialog ==null){
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
            dialog = builder.create();
        }

        if (dialog.isShowing()){
            dialog.dismiss();
        }else {
            dialog.show();
        }
    }
}
