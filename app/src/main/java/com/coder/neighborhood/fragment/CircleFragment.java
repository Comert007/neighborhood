package com.coder.neighborhood.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.coder.neighborhood.R;
import com.coder.neighborhood.adapter.user.TranslateTabAdapter;
import com.coder.neighborhood.fragment.circle.BigEventFragment;
import com.coder.neighborhood.fragment.circle.DoingsFragment;
import com.coder.neighborhood.fragment.circle.MakeFriendsFragment;
import com.coder.neighborhood.fragment.circle.TopicFragment;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ww.com.core.widget.TranslateTabBar;

/**
 * Created by feng on 2017/12/23.
 */

public class CircleFragment extends BaseFragment<VoidView,VoidModel>{

    @BindView(R.id.translate)
    TranslateTabBar translate;
    @BindView(R.id.vp)
    ViewPager vp;


    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private TranslateTabAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void init() {

        translate.setOnTabChangeListener(new TranslateTabBar.OnTabChangeListener() {
            @Override
            public void onChange(int index) {
                vp.setCurrentItem(index);
            }
        });
//
        fragmentManager = getChildFragmentManager();
        addFragment();
        adapter = new TranslateTabAdapter(fragmentManager, fragments);
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);

        vp.setOnPageChangeListener(pageChangeListener);

    }

    private void addFragment(){
        if (fragments==null){
            fragments = new ArrayList<>();
        }
        fragments.add(new MakeFriendsFragment());
        fragments.add(new BigEventFragment());
        fragments.add(new TopicFragment());
        fragments.add(new DoingsFragment());
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
