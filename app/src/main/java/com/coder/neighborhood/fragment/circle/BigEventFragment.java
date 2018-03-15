package com.coder.neighborhood.fragment.circle;

import android.content.Intent;
import android.text.TextUtils;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.MainActivity;
import com.coder.neighborhood.activity.circle.PublishEventActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.circle.EventAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.circle.EventBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.aop.CheckUser;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.base.RefreshView;

import java.util.List;

import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @Author feng
 * @Date 2018/1/16
 */

public class BigEventFragment extends BaseFragment<RefreshView,CircleModel> {

    private EventAdapter adapter;
    private int page = 1;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private final int REQUEST_CODE = 0x20;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_big_event;
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }


    private void initView() {
        adapter = new EventAdapter(getContext());
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
    }

    private void initListener() {
        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                csr.setEnableRefresh(true);
                csr.setFooterRefreshAble(false);
                events();
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                events();
            }
        });
    }

    private void initData() {
        crv.setAdapter(adapter);
        events();
    }

    private void events(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user !=null && !TextUtils.isEmpty(user.getUserId())){
            m.events(user.getUserId(), page + "", Constants.PAGE_SIZE + "",
                    new HttpSubscriber<List<EventBean>>(getContext(),false) {

                @Override
                public void onNext(List<EventBean> eventBeans) {
                    if (page ==1){
                        ((MainActivity) getPresenterActivity()).showEventTopContent(eventBeans);
                    }
                    v.getCsr().setRefreshFinished();
                    if (eventBeans != null && eventBeans.size() > 0) {
                        if (page == 1) {
                            adapter.addList(eventBeans);
                            csr.setFooterRefreshAble(true);
                        } else {
                            v.getCrv().removeFooterView(v.getFooterView());
                            adapter.appendList(eventBeans);
                        }

                        if (eventBeans.size() == Constants.PAGE_SIZE) {
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

    @CheckUser
    @OnClick(R.id.btn_ask)
    public void onPublish(){
        PublishEventActivity.startForResult(getPresenterActivity(),REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            Debug.d("=====>>>>>>EVent");
            page = 1;
            events();
        }
    }
}
