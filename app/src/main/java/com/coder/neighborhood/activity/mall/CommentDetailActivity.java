package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.CommentDetailAdapter;
import com.coder.neighborhood.bean.mall.CommentBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.CommentDetailView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/1/9
 *
 */

public class CommentDetailActivity extends BaseActivity<CommentDetailView,MallModel> {

    private CommentDetailAdapter adapter;
    private int page = 1;

    private String goodSid;

    public static void start(Context context,String goodSid) {
        Intent intent = new Intent(context, CommentDetailActivity.class);
        intent.putExtra("goodSid",goodSid);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void init() {
        goodSid = getIntent().getStringExtra("goodSid");
        initData();
    }

    private void initData(){
        adapter = new CommentDetailAdapter(this);
        v.getCrv().setAdapter(adapter);

        comments();
    }


    private void comments(){
        m.comments(goodSid, page+"", Constants.PAGE_SIZE+"", bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<CommentBean>>(this,false) {
                    @Override
                    public void onNext(List<CommentBean> commentBeans) {
                        if (commentBeans!=null && commentBeans.size()>0){
                            adapter.addList(commentBeans);
                        }else {

                        }
                    }
                });
    }
}
