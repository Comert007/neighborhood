package com.coder.neighborhood.activity.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.user.TranslateTabAdapter;
import com.coder.neighborhood.fragment.OnlineConsultationFragment;
import com.coder.neighborhood.fragment.PhoneConsultationFragment;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ww.com.core.widget.TranslateTabBar;

/**
 * @Author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class CustomerServiceActivity extends BaseActivity<VoidView,VoidModel>{
    @BindView(R.id.translate)
    TranslateTabBar translate;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private TranslateTabAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomerServiceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void init() {
        translate.setOnTabChangeListener(index -> vp.setCurrentItem(index));
//
        fragmentManager = getSupportFragmentManager();
        addFragment();
        adapter = new TranslateTabAdapter(fragmentManager, fragments);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(2);

        vp.setOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void addFragment(){
        if (fragments==null){
            fragments = new ArrayList<>();
        }
        fragments.add(new PhoneConsultationFragment());
        fragments.add(getOnlineFragment());
    }

    private Fragment getOnlineFragment(){
        OnlineConsultationFragment chatFragment = new OnlineConsultationFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, "10000");
        chatFragment.setArguments(args);
        return chatFragment;
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int
                positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            translate.setCurrentIndex(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
