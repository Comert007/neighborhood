package com.coder.neighborhood.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.coder.neighborhood.api.BaseApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.DataCleanManager;
import com.coder.neighborhood.utils.DialogUtils;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;
import ww.com.core.exception.StorageSpaceException;
import ww.com.core.pick.ImagePick;
import ww.com.core.utils.PermissionDispose;
import ww.com.core.widget.RoundImageView;

/**
 * Created by feng on 2017/12/23.
 */

public class UserFragment extends BaseFragment<VoidView, UserModel> implements ImagePick
        .OnImageListener,
        PermissionDispose.OnPermissionListener {

    @BindView(R.id.riv_header)
    RoundImageView rivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_grade)
    TextView tvGrade;

    private ImagePick pick;
    private PermissionDispose dispose;

    private BottomSheetDialog bottomDialog;

    private final int PERMISSION_REQUEST_CODE = 0x14;

    private String path;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void init() {
        pick = ImagePick.init(this, this);
        // 初始化permissionDispose
        dispose = PermissionDispose.init(this, this);
    }

    private void initData() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null) {
            ImageLoader.getInstance().displayImage(BaseApi.HOST_URL + user.getImgUrl(),
                    rivHeader, BaseApplication
                            .getDisplayImageOptions(R.mipmap.ic_default_avatar));
            tvName.setText(TextUtils.isEmpty(user.getNickName()) ? user.getPhone() + "用户" : user
                    .getNickName());
            tvGrade.setText("LV." + user.getUserStatus());
        }
    }

    @OnClick({R.id.btn_loginout, R.id.ll_sign, R.id.rl_about_us, R.id.ll_cart, R.id.ll_order,
            R.id.rl_feedback, R.id.ll_user_info, R.id.riv_header})
    public void onUser(View v) {
        switch (v.getId()) {
            case R.id.btn_loginout:
                showQuitDialog();
                break;
            case R.id.ll_sign:
                querySign();
                break;
            case R.id.rl_about_us:
                AboutUsActivity.start(getContext());
                break;
            case R.id.ll_user_info:
                UserInfoActivity.start(getPresenterActivity());
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
            case R.id.riv_header:
                gainPermission();
                break;
            default:
                break;
        }
    }


    private void showBottomDialog() {
        if (bottomDialog == null) {
            bottomDialog = new BottomSheetDialog(getContext(), R.style.CustomerDialogStyle);
            View view = LayoutInflater.from(getContext()).inflate(R.layout
                    .layout_bottom_sheet_dialog, null);
            ScreenUtil.scale(view);
            TextView tvDialogCamera = ButterKnife.findById(view, R.id.tv_dialog_camera);
            TextView tvDialogPhoto = ButterKnife.findById(view, R.id.tv_dialog_photo);
            TextView tvDialogCancel = ButterKnife.findById(view, R.id.tv_dialog_cancel);
            tvDialogCamera.setOnClickListener(v -> {
                try {
                    pick.startCamera();
                } catch (StorageSpaceException e) {
                    e.printStackTrace();
                }
            });

            tvDialogPhoto.setOnClickListener(v -> pick.startAlbumSingle());

            tvDialogCancel.setOnClickListener(v -> bottomDialog.dismiss());
            bottomDialog.setContentView(view);
        }
        bottomDialog.show();
    }


    private void gainPermission() {
        int version = Build.VERSION.SDK_INT;
        if (version > Build.VERSION_CODES.LOLLIPOP_MR1) {
            dispose.requestPermission(PERMISSION_REQUEST_CODE, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
        } else {
            showBottomDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                pick.onActivityResult(requestCode, resultCode, data);
            } catch (StorageSpaceException e) {
                e.printStackTrace();
                ToastUtils.showToast(getString(R.string.toast_sdcard_exception));
            }
        }
    }

    private void showQuitDialog() {

        DialogUtils.showDialog(getContext(), "退出", "是否退出玩家邻里", true,
                "退出", (dialog, which) -> {

                    BaseApplication.getInstance().saveUserInfo(null);
                    BaseApplication.getInstance().exitApp();
                    LoginActivity.start(getContext());
                    dialog.dismiss();
                }, true, "留下", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }


    private void showSign(String count) {
        DialogUtils.showDialog(getContext(), "本月签到", count + "天", true,
                "签到", (dialog, which) -> {
                    dialog.dismiss();
                    signIn();
                }, false, "", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }


    private void signIn() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        m.signIn(user.getUserId(), new HttpSubscriber<ResponseBean>(getContext(), false) {
            @Override
            public void onNext(ResponseBean responseBean) {
                ToastUtils.showToast("签到成功", true);
            }
        });
    }

    private void querySign() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        m.querySign(user.getUserId(), new HttpSubscriber<String>(getContext(), true) {
            @Override
            public void onNext(String s) {
                if (TextUtils.isEmpty(s)) {
                    s = "1";
                }
                showSign(s);
            }

        });
    }

    @OnClick({R.id.ll_wait_pay, R.id.ll_wait_send, R.id.ll_wait_gain, R.id.ll_wait_comment, R.id
            .ll_return_goods, R.id.rl_setting,
            R.id.ll_good_friends})
    public void onUserClick(View v) {
        switch (v.getId()) {
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
            case R.id.rl_setting:
                cacheRel();
                break;
        }
    }

    @Override
    public void onSinglePath(String path) {
        Debug.d("path:" + path);
        if (bottomDialog.isShowing()) {
            bottomDialog.dismiss();
        }
        this.path = path;
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            m.modifyAvatar(user.getUserId(), path, new HttpSubscriber<String>(getContext(), true) {
                @Override
                public void onNext(String s) {
                    ToastUtils.showToast(s, true);
                }
            });
        }

    }

    @Override
    public void onMultiPaths(ArrayList<String> paths) {
        if (bottomDialog.isShowing()) {
            bottomDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(int requestCode, Map<String, Integer> successPermissions) {
        showBottomDialog();
    }

    @Override
    public void onFinal(int requestCode, Map<String, Integer> failPermissions) {

    }


    private void cacheRel() {
        try {
            String outCacheSize = DataCleanManager.getTotalCacheSize(getContext());
            DialogUtils.showDialog(getContext(), "是否清理缓存", outCacheSize, true,
                    "清理", (dialog, which) -> {
                        DataCleanManager.clearAllCache(getContext());
                        dialog.dismiss();
                    }, true, "返回", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
