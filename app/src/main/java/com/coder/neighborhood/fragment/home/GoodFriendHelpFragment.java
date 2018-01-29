package com.coder.neighborhood.fragment.home;

import com.coder.neighborhood.R;
import com.coder.neighborhood.adapter.home.HelpAdapter;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;

import java.util.Arrays;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class GoodFriendHelpFragment extends BaseFragment<HelpView,HomeModel>{

    private HelpAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_good_friend_help;
    }

    @Override
    protected void init() {
        adapter = new HelpAdapter(getContext());
        v.getCrv().setAdapter(adapter);

        adapter.addList(Arrays.asList("1","2","3"));
    }


}
