package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.TravelAdapter;
import com.coder.neighborhood.bean.home.TravelBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.TravelView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

@SuppressLint("Registered")
public class TravelActivity extends BaseActivity<TravelView,HomeModel> {

    private TravelAdapter adapter;

    private int page =1;

    public static void start(Context context) {
        Intent intent = new Intent(context, TravelActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_travel;
    }

    @Override
    public void onTitleLeft() {
        finish();
    }

    @Override
    protected void init() {
        adapter = new TravelAdapter(this);
        v.getCrv().setAdapter(adapter);

        onTravels();
    }

    private void onTravels(){
        m.travels(page + "", Constants.PAGE_SIZE + "", bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<TravelBean>>(this,true) {
                    @Override
                    public void onNext(List<TravelBean> travelBeans) {
                        adapter.addList(travelBeans);

                    }
                });
    }
}
