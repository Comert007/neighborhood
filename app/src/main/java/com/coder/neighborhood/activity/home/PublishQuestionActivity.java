package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

public class PublishQuestionActivity extends BaseActivity<VoidView,VoidModel> {


    private int type = 1;

    public static void start(Context context,int type) {
        Intent intent = new Intent(context, PublishQuestionActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_question;
    }

    @Override
    protected void init() {

        initData();
    }

    private void initData(){
        type = getIntent().getIntExtra("type",type);
    }


}
