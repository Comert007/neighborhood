package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.user.GoodFriendsAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.user.GoodFriendsView;

import java.util.Arrays;

/**
 * @Author feng
 * @Date 2018/1/9
 */

public class GoodFriendsActivity extends BaseActivity<GoodFriendsView,VoidModel> {

    private GoodFriendsAdapter adapter;

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

        adapter.addList(Arrays.asList("1","2","3"));
    }
}
