package com.coder.neighborhood.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.user.FriendsInfoAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.user.FriendsInfoView;

import java.util.Arrays;

import ww.com.core.ScreenUtil;

/**
 * @Author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class FriendsInfoActivity extends BaseActivity<FriendsInfoView, VoidModel> {

    private FriendsInfoAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FriendsInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_friends_info;
    }

    @Override
    protected void init() {
        adapter = new FriendsInfoAdapter(this);
        v.getCrv().setAdapter(adapter);
        View vi = LayoutInflater.from(this).inflate(R.layout.layout_friends_info_header, null);
        ScreenUtil.scale(vi);
        v.getCrv().addHeadView(vi);
        adapter.addList(Arrays.asList("1", "2", "3"));
    }
}
