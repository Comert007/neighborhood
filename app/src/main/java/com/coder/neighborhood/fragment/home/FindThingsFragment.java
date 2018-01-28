package com.coder.neighborhood.fragment.home;

import com.coder.neighborhood.R;
import com.coder.neighborhood.adapter.home.FindThingsAdapter;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;

import java.util.Arrays;

/**
 * @author feng
 * @Date 2018/1/20
 */
public class FindThingsFragment extends BaseFragment<HelpView,VoidModel>{

    private FindThingsAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_find_things;
    }

    @Override
    protected void init() {
        adapter = new FindThingsAdapter(getContext());
        v.getCrv().setAdapter(adapter);

        adapter.addList(Arrays.asList("1","2","3"));
    }
}
