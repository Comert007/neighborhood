package com.coder.neighborhood.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.user.FriendsInfoView;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.Arrays;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.RoundImageView;

/**
 * @Author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class FriendsInfoActivity extends BaseActivity<FriendsInfoView, UserModel> {

    private FriendsInfoAdapter adapter;
    private FriendBean friendBean;
    private RoundImageView rivHeader;
    private TextView tvName;
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

    private void initView() {
        View vi = LayoutInflater.from(this).inflate(R.layout.layout_friends_info_header, null);
        rivHeader = ButterKnife.findById(vi, R.id.riv_header);
        tvName = ButterKnife.findById(vi, R.id.tv_name);
        btnAdd = ButterKnife.findById(vi, R.id.btn_add_friend);
        btnAdd.setOnClickListener(v -> {
            addFriend();
        });
        vi.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ScreenUtil.scale(vi);
        v.getCrv().addHeadView(vi);
    }

    private void initData() {
        tvName.setText(friendBean.getNickName());
        ImageLoader.getInstance().displayImage(friendBean.getImgUrl(), rivHeader, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_default_avatar));
        adapter = new FriendsInfoAdapter(this);
        v.getCrv().setAdapter(adapter);
        adapter.addList(Arrays.asList("1", "2", "3"));
    }

    private void addFriend() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null) {
            m.addFriend(user.getUserId(), friendBean.getUserId(), user.getEasemobUsername()
                    , bindUntilEvent(ActivityEvent.DESTROY)
                    , new HttpSubscriber<String>(this, true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
                        }
                    });
        }
    }
}
