package com.coder.neighborhood.fragment.circle;

import android.view.LayoutInflater;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.adapter.circle.MakeFriendsAdapter;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.circle.MakeFriendsView;

import java.util.Arrays;

import ww.com.core.ScreenUtil;
import ww.com.core.widget.TranslateTabBar;

/**
 * @Author feng
 * @Date 2018/1/16
 */

public class MakeFriendsFragment extends BaseFragment<MakeFriendsView,VoidModel> {

    TranslateTabBar translate;

    private MakeFriendsAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_make_friends;
    }

    @Override
    protected void init() {
        adapter = new MakeFriendsAdapter(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_make_friends_header,null);
        translate = view.findViewById(R.id.translate);
        ScreenUtil.scale(view);
        v.getCrv().setAdapter(adapter);
        v.getCrv().addHeadView(view);
        adapter.addList(Arrays.asList("1","2","3"));

        translate.setOnTabChangeListener(new TranslateTabBar.OnTabChangeListener() {
            @Override
            public void onChange(int index) {

            }
        });
    }
}
