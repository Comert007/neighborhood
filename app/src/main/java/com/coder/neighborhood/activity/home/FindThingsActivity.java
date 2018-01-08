package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.home.FindThingsAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;

import java.util.Arrays;

import butterknife.OnClick;

/**
 * Created by feng on 2018/1/8.
 */

@SuppressLint("Registered")
public class FindThingsActivity extends BaseActivity<HelpView,VoidModel> {

    private FindThingsAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, FindThingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_find_things;
    }

    @Override
    protected void init() {
        adapter = new FindThingsAdapter(this);
        v.getCrv().setAdapter(adapter);

        adapter.addList(Arrays.asList("1","2","3"));
    }

    @OnClick({R.id.btn_ask})
    public void onFindThings(View v){
        switch (v.getId()){
            case R.id.btn_ask:
                PublishQuestionActivity.start(this,2);
                break;
        }
    }
}
