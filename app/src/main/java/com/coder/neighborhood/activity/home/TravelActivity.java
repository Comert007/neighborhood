package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.home.TravelAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.home.TravelView;

import java.util.Arrays;

/**
 * Created by feng on 2018/1/8.
 */

@SuppressLint("Registered")
public class TravelActivity extends BaseActivity<TravelView,VoidModel> {

    private TravelAdapter adapter;

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

        adapter.addList(Arrays.asList("1","2","3"));
    }
}
