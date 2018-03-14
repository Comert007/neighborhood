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

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/9
 */

public class CommentDetailActivity extends BaseActivity<CommentDetailView, MallModel> {

    private CommentDetailAdapter adapter;
    private int page = 1;

    private String goodSid;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;


    public static void start(Context context, String goodSid) {
        Intent intent = new Intent(context, CommentDetailActivity.class);
        intent.putExtra("goodSid", goodSid);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void init() {
        goodSid = getIntent().getStringExtra("goodSid");
        initView();
        initListener();
        initData();

    }

    private void initView() {
        adapter = new CommentDetailAdapter(this);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                csr.setFooterRefreshAble(false);
                comments();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                comments();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        comments();
    }


    private void comments() {
        m.comments(goodSid, page + "", Constants.PAGE_SIZE + "", bindUntilEvent(ActivityEvent
                        .DESTROY)
                , new HttpSubscriber<List<CommentBean>>(this, false) {
                    @Override
                    public void onNext(List<CommentBean> commentBeans) {
                        v.getCsr().setRefreshFinished();
                        if (commentBeans != null && commentBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(commentBeans);
                                csr.setFooterRefreshAble(true);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(commentBeans);
                            }

                            if (commentBeans.size() == Constants.PAGE_SIZE) {
                                page++;
                            } else {
                                v.getCsr().setFooterRefreshAble(false);
                            }
                        } else {
                            v.getCsr().setFooterRefreshAble(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        v.getCsr().setRefreshFinished();
                    }
                });
    }
}
