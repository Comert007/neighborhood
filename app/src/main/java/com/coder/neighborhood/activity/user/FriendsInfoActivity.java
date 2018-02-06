package com.coder.neighborhood.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.user.FriendsInfoAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.user.FriendBean;
import com.coder.neighborhood.bean.user.FriendInfoBean;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.user.FriendsInfoView;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.RoundImageView;

/**
 * @author feng
 * @date 2018/1/16
 */

@SuppressLint("Registered")
public class FriendsInfoActivity extends BaseActivity<FriendsInfoView, UserModel> {

    private FriendsInfoAdapter adapter;
    private FriendBean friendBean;
    private FriendInfoBean friendInfoBean;

    private RoundImageView rivHeader;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvIntroduction;
    private Button btnAdd;


    public static void start(Context context, FriendBean friendBean) {
        Intent intent = new Intent(context, FriendsInfoActivity.class);
        intent.putExtra("friendBean", friendBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_friends_info;
    }

    @Override
    protected void init() {
        friendBean = (FriendBean) getIntent().getSerializableExtra("friendBean");
        initView();
        initData();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initView() {
        View vi = LayoutInflater.from(this).inflate(R.layout.layout_friends_info_header, null);
        rivHeader = ButterKnife.findById(vi, R.id.riv_header);
        tvName = ButterKnife.findById(vi, R.id.tv_name);
        tvIntroduction = ButterKnife.findById(vi, R.id.tv_introduction);
        tvPhone = ButterKnife.findById(vi, R.id.tv_phone);
        tvAddress = ButterKnife.findById(vi, R.id.tv_address);
        btnAdd = ButterKnife.findById(vi, R.id.btn_add_friend);
        btnAdd.setOnClickListener(v -> {
            addFriend();
        });
        vi.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ScreenUtil.scale(vi);
        adapter = new FriendsInfoAdapter(this);
        v.getCrv().setAdapter(adapter);
        v.getCrv().addHeadView(vi);
    }

    private void initData() {
        friendInfo();
    }

    private void addFriend() {
        if (friendInfoBean==null){
            ToastUtils.showToast("system error");
            return;
        }
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null) {
            m.addFriend(user.getUserId(), friendInfoBean.getUserId(), user.getEasemobUsername()
                    , bindUntilEvent(ActivityEvent.DESTROY)
                    , new HttpSubscriber<String>(this, true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s, true);
                        }
                    });
        }
    }

    private void friendInfo() {
        if (friendBean != null && !TextUtils.isEmpty(friendBean.getUserId())) {
            m.friendInfo(friendBean.getUserId(), bindUntilEvent(ActivityEvent.DESTROY)
                    , new HttpSubscriber<FriendInfoBean>(this, true) {
                        @Override
                        public void onNext(FriendInfoBean friendInfoBean) {
                            FriendsInfoActivity.this.friendInfoBean = friendInfoBean;
                            setFriendInfo();
                        }
                    });
        }
    }

    private void setFriendInfo() {
        tvName.setText(friendInfoBean.getNickname());
        ImageLoader.getInstance().displayImage(friendInfoBean.getHeadImgUrl(), rivHeader,
                BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_default_avatar));
        tvPhone.setText(TextUtils.isEmpty(friendInfoBean.getPhone())?"未设置电话":friendInfoBean.getPhone());
        tvAddress.setText(TextUtils.isEmpty(friendInfoBean.getAddress())?"未设置地址":friendInfoBean.getAddress());
        tvIntroduction.setText(TextUtils.isEmpty(friendInfoBean.getUserInfo())?"未设置个人信息":friendInfoBean.getUserInfo());
    }
}
