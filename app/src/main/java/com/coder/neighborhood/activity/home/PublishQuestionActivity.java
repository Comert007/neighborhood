package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import butterknife.BindView;

/**
 * Created by feng on 2018/1/8.
 */

public class PublishQuestionActivity extends BaseActivity<VoidView,VoidModel> {

    @BindView(R.id.ll_add_images)
    LinearLayout llAddImages;
    @BindView(R.id.ll_add_remuneration)
    LinearLayout llAddRemuneration;
    @BindView(R.id.ll_commit_more)
    LinearLayout llCommitMore;
    @BindView(R.id.btn_commit_single)
    Button btnCommitSingle;

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
        onType(type);
    }

    private void onType(int type){
        switch (type){
            case 1:
                llAddImages.setVisibility(View.GONE);
                llAddRemuneration.setVisibility(View.VISIBLE);
                llCommitMore.setVisibility(View.GONE);
                btnCommitSingle.setVisibility(View.VISIBLE);
                break;
            case 2:
                llAddImages.setVisibility(View.VISIBLE);
                llAddRemuneration.setVisibility(View.GONE);
                llCommitMore.setVisibility(View.VISIBLE);
                btnCommitSingle.setVisibility(View.GONE);
                break;
            case 3:
                llAddImages.setVisibility(View.VISIBLE);
                llAddRemuneration.setVisibility(View.GONE);
                llCommitMore.setVisibility(View.GONE);
                btnCommitSingle.setVisibility(View.VISIBLE);
                break;
            case 4:
                llAddImages.setVisibility(View.GONE);
                llAddRemuneration.setVisibility(View.GONE);
                llCommitMore.setVisibility(View.GONE);
                btnCommitSingle.setVisibility(View.VISIBLE);
                break;

        }
    }
}
