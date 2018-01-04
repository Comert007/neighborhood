package com.coder.neighborhood.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coder.neighborhood.R;
import com.coder.neighborhood.adapter.HomeAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import java.util.Arrays;

import butterknife.BindView;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;

/**
 * Created by feng on 2017/12/23.
 */

public class HomeFragment extends BaseFragment<VoidView,VoidModel>{
    @BindView(R.id.crv)
    CustomRecyclerView crv;

    private HomeAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        adapter = new HomeAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        crv.setLayoutManager(manager);
        crv.setItemAnimator(new DefaultItemAnimator());
        crv.setAdapter(adapter);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_home_header,null);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ScreenUtil.scale(header);
        crv.addHeadView(header);
        adapter.addList(Arrays.asList("1","2","3"));
    }


}
