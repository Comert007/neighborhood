package com.coder.neighborhood.fragment;

import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 * Created by feng on 2017/12/23.
 */

public class UserFragment extends BaseFragment<VoidView,VoidModel>{

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void init() {

    }
}
