package com.coder.neighborhood.fragment.home;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.FindThingsAdapter;
import com.coder.neighborhood.bean.home.ThingsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class LostThingsFragment extends BaseFragment<HelpView,HomeModel>{

    private FindThingsAdapter adapter;
    private int page = 1;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_lost_things;
    }

    @Override
    protected void init() {
        adapter = new FindThingsAdapter(getContext());
        v.getCrv().setAdapter(adapter);

        lostThings();

    }

    private void lostThings(){
        m.lostThings("1", page + "", Constants.PAGE_SIZE + "",
                new HttpSubscriber<List<ThingsBean>>(getContext(),false) {
                    @Override
                    public void onNext(List<ThingsBean> thingsBeans) {
                        adapter.addList(thingsBeans);
                    }
                });
    }
}
