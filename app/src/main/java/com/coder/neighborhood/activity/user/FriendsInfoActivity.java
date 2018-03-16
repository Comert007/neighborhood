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
import com.coder.neighborhood.bean.circle.CircleBean;
import com.coder.neighborhood.bean.user.FriendInfoBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.aop.CheckUser;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.user.FriendsInfoView;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;
import ww.com.core.widget.RoundImageView;

/**
 * @author feng
 * @date 2018/1/16
 */

@SuppressLint("Registered")
public class FriendsInfoActivity extends BaseActivity<FriendsInfoView, UserModel> {

    private FriendsInfoAdapter adapter;
    private FriendInfoBean friendInfoBean;

    private RoundImageView rivHeader;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvIntroduction;
    private Button btnAdd;

    private int  page = 1;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;
    private String userId;



    public static void start(Context context, String userId) {
        Intent intent = new Intent(context, FriendsInfoActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_friends_info;
    }

    @Override
    protected void init() {
        userId = getIntent().getStringExtra("userId");
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
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
        crv.setAdapter(adapter);
        crv.addHeadView(vi);
    }


    private void initListener(){
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                profileCircles();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                profileCircles();
            }
        });

        adapter.setStrListener((position, str) -> {
            CircleBean circleBean = adapter.getItem(position);
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user!=null && !TextUtils.isEmpty(user.getUserId())){

                m.addCircleComment(user.getUserId(), (circleBean.getCircleId()), str, new HttpSubscriber<String>(this,true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(s,true);
                        page =1;
                        profileCircles();
                    }
                });
            }
        });

        adapter.setActionListener((position, view) -> {
            CircleBean circleBean = adapter.getItem(position);
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user!=null && !TextUtils.isEmpty(user.getUserId())){
                m.addCircleLike(user.getUserId(), circleBean.getCircleId(), new HttpSubscriber<String>(this,true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(s,true);
                        page =1;
                        profileCircles();
                    }
                });
            }

        });
    }

    private void initData() {
        friendInfo();
        profileCircles();
    }

    @CheckUser
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
                            btnAdd.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private void friendInfo() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            if ( !TextUtils.isEmpty(userId)) {
                m.friendInfo(user.getUserId(), userId,bindUntilEvent(ActivityEvent.DESTROY)
                        , new HttpSubscriber<FriendInfoBean>(this, true) {
                            @Override
                            public void onNext(FriendInfoBean friendInfoBean) {
                                FriendsInfoActivity.this.friendInfoBean = friendInfoBean;
                                setFriendInfo();
                            }
                        });
            }
        }

    }

    private void profileCircles(){
        if (!TextUtils.isEmpty(userId)) {
            m.profileCircles(userId, page + "", Constants.PAGE_SIZE + "",
                    bindUntilEvent(ActivityEvent.DESTROY) , new HttpSubscriber<List<CircleBean>>(this,false) {
                        @Override
                        public void onNext(List<CircleBean> circleBeans) {
                            v.getCsr().setRefreshFinished();
                            if (circleBeans != null && circleBeans.size() > 0) {
                                if (page == 1) {
                                    adapter.addList(circleBeans);
                                    csr.setFooterRefreshAble(true);
                                } else {
                                    v.getCrv().removeFooterView(v.getFooterView());
                                    adapter.appendList(circleBeans);
                                }

                                if (circleBeans.size() == Constants.PAGE_SIZE) {
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

    private void setFriendInfo() {

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        String flag = friendInfoBean.getFriendFlag();
        if (user!=null){
            if ("1".equals(flag)|| "0".equals(flag)){
                setTitleText("好友信息");
            }else if ("3".equals(userId.equals(user.getUserId()))){
                setTitle("个人信息");
            }
        }
        btnAdd.setVisibility("1".equals(flag)||"3".equals(flag)?View.GONE:View.VISIBLE);
        tvName.setText(friendInfoBean.getNickname());
        ImageLoader.getInstance().displayImage(friendInfoBean.getHeadImgUrl(), rivHeader,
                BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_default_avatar));
        tvPhone.setText(TextUtils.isEmpty(friendInfoBean.getPhone())?"未设置电话":friendInfoBean.getPhone());
        tvAddress.setText(TextUtils.isEmpty(friendInfoBean.getAddress())?"未设置地址":friendInfoBean.getAddress());
        tvIntroduction.setText(TextUtils.isEmpty(friendInfoBean.getUserInfo())?"未设置个人信息":friendInfoBean.getUserInfo());
    }
}
