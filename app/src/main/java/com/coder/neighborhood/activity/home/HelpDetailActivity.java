package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.HelpDetailAdapter;
import com.coder.neighborhood.bean.home.HelpDetailBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpDetailView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.OnClick;

/**
 * @author feng
 * @Date 2018/1/29.
 */

public class HelpDetailActivity extends BaseActivity<HelpDetailView,HomeModel> {

    private int page =1;
    private String questionId;

    private HelpDetailAdapter adapter;


    public static void start(Context context,String questionId) {
        Intent intent = new Intent(context, HelpDetailActivity.class);
        intent.putExtra("questionId",questionId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_help_detail;
    }

    @Override
    protected void init() {

        initView();
        initData();
    }

    private void initView(){

    }

    private void initData(){
        adapter = new HelpDetailAdapter(this);
        v.getCrv().setAdapter(adapter);

        questionId = getIntent().getStringExtra("questionId");

        questionsComment(questionId);
    }

    private void questionsComment(String questionId){
        m.questionsComment(questionId, page + "", Constants.PAGE_SIZE + "", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<List<HelpDetailBean>>(this,false) {
                    @Override
                    public void onNext(List<HelpDetailBean> helpDetailBeans) {
                        adapter.addList(helpDetailBeans);
                    }
                });
    }

    @OnClick(R.id.btn_ask)
    public void onHelp(){

    }
}
