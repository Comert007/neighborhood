package com.coder.neighborhood.fragment.home;

import android.text.TextUtils;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.HelpAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.home.QuestionBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.home.HelpView;
import com.coder.neighborhood.utils.ToastUtils;

import java.util.List;

import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/20
 */

public class GoodFriendHelpFragment extends BaseFragment<HelpView, HomeModel> {

    private HelpAdapter adapter;

    private int page = 1;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_good_friend_help;
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new HelpAdapter(getContext());
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(true);
    }


    private void initData() {
        crv.setAdapter(adapter);
        friendQuestions();
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setFooterRefreshAble(true);
                friendQuestions();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                friendQuestions();
            }
        });
    }


    private void friendQuestions() {

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null || TextUtils.isEmpty(user.getUserId())) {
            ToastUtils.showToast("用户信息有误");
            return;
        }
        m.friendQuestions(user.getUserId(), page + "", Constants.PAGE_SIZE + "",
                new HttpSubscriber<List<QuestionBean>>(getContext(), false) {
                    @Override
                    public void onNext(List<QuestionBean> questionBeans) {
                        v.getCsr().setRefreshFinished();
                        if (questionBeans != null && questionBeans.size() > 0) {
                            if (page == 1) {
                                adapter.addList(questionBeans);
                            } else {
                                v.getCrv().removeFooterView(v.getFooterView());
                                adapter.appendList(questionBeans);
                            }

                            if (questionBeans.size() == Constants.PAGE_SIZE) {
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
