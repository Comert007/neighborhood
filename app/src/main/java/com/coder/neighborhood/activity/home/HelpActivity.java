package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.home.HelpAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;

import java.util.Arrays;

import butterknife.OnClick;

/**
 * Created by feng on 2018/1/8.
 */

@SuppressLint("Registered")
public class HelpActivity extends BaseActivity<HelpView,VoidModel> {


    private HelpAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_help;
    }

    @Override
    protected void init() {
        adapter = new HelpAdapter(this);
        v.getCrv().setAdapter(adapter);

        adapter.addList(Arrays.asList("1","2","3"));
    }


    @Override
    public void onTitleLeft() {
        finish();
    }


    @OnClick({R.id.btn_ask})
    public void onHelp(View v){
        switch (v.getId()){
            case R.id.btn_ask:
                PublishQuestionActivity.start(this,1);
                break;
        }
    }
}
