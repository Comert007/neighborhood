package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.HelpDetailAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.home.HelpDetailBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpDetailView;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.EditDialog;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/29.
 */

public class HelpDetailActivity extends BaseActivity<HelpDetailView, HomeModel> {

    @BindView(R.id.tv_input)
    TextView tvInput;

    private int page = 1;
    private String questionId;
    private String replyStr;

    private HelpDetailAdapter adapter;


    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, HelpDetailActivity.class);
        intent.putExtra("questionId", questionId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_help_detail;
    }

    @Override
    protected void init() {

        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new HelpDetailAdapter(this);
        v.getCrv().setAdapter(adapter);
        v.getCsr().setEnableRefresh(true);
        v.getCsr().setFooterRefreshAble(true);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initData() {
        questionId = getIntent().getStringExtra("questionId");
        questionsComment(questionId);
    }


    private void initListener() {
        v.getCsr().setOnSwipeRefreshListener(
                new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
                    @Override
                    public void onHeaderRefreshing() {
                        page = 1;
                        v.getCsr().setFooterRefreshAble(true);
                        questionsComment(questionId);
                    }

                    @Override
                    public void onFooterRefreshing() {
                        v.getCrv().addFooterView(v.getFooterView());
                        questionsComment(questionId);
                    }
                });
    }

    private void questionsComment(String questionId) {
        m.questionsComment(questionId, page + "", Constants.PAGE_SIZE + "",
                bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<List<HelpDetailBean>>(this, false) {
                    @Override
                    public void onNext(List<HelpDetailBean> helpDetailBeans) {
                        v.getCsr().setRefreshFinished();
                        if (helpDetailBeans != null && helpDetailBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(helpDetailBeans);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(helpDetailBeans);
                            }

                            if (helpDetailBeans.size() == Constants.PAGE_SIZE) {
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

    @OnClick(R.id.btn_ask)
    public void onHelp() {
        EditDialog editDialog = new EditDialog(this, 4);
        editDialog.setParms(tvInput, "请输入帮助信息", 200);
        editDialog.setNums(true, 200);
        editDialog.setEdiClickInterface(str -> {
            Debug.d("str:" + str);
            replyStr = str;
            helpQuestionReply();
            editDialog.dismiss();
        });

        editDialog.show();
    }

    private void helpQuestionReply() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            m.addHelpQuestionReply(user.getUserId(), questionId, replyStr, bindUntilEvent
                            (ActivityEvent.DESTROY)

                    , new HttpSubscriber<String>(this, true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
                            v.getCsr().onRefresh();
                        }
                    });
        }

    }

}
