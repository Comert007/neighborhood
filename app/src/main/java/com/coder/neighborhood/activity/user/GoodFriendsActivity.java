package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.user.GoodFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.user.FriendBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.user.GoodFriendsView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/1/9
 */

public class GoodFriendsActivity extends BaseActivity<GoodFriendsView,UserModel> {

    private GoodFriendsAdapter adapter;
    private int page =1;

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodFriendsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_good_friends;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData(){
        adapter = new GoodFriendsAdapter(this);
        v.getCrv().setAdapter(adapter);
        onFriends();
    }

    private void onFriends(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user==null){
            ToastUtils.showToast("用户信息有误");
            return;
        }
        m.friends(user.getUserId(), page + "", Constants.PAGE_SIZE + "",
                bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<List<FriendBean>>(this,true) {
                    @Override
                    public void onNext(List<FriendBean> friendBeans) {
                        adapter.addList(friendBeans);
                    }
                });

    }
}
