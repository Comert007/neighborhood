package com.coder.neighborhood.fragment.circle;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.circle.PublishCircleActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.circle.MakeFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.circle.CircleBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.circle.MakeFriendsView;
import com.coder.neighborhood.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/16
 */

public class MakeFriendsFragment extends BaseFragment<MakeFriendsView, CircleModel> {

    private List<TextView> circleViews = new ArrayList<>();
    private MakeFriendsAdapter adapter;
    private int page = 1;

    private int circleIndex =0;
    private final int REQUEST_CODE = 0x16;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_make_friends;
    }

    @Override
    protected void init() {
        initView();
        initListener();
        initData();
    }

    private void initView() {
        adapter = new MakeFriendsAdapter(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout
                .layout_make_friends_header, null);
        circleViews.add(ButterKnife.findById(view, R.id.tv_community));
        circleViews.add(ButterKnife.findById(view, R.id.tv_friends));
        circleViews.add(ButterKnife.findById(view, R.id.tv_customer));
        ScreenUtil.scale(view);
        csr = v.getCsr();
        crv = v.getCrv();
        csr.setEnableRefresh(true);
        csr.setFooterRefreshAble(false);
        crv.setAdapter(adapter);
        crv.addHeadView(view);
    }


    private void initListener() {
        for (int i = 0; i < circleViews.size(); i++) {
            TextView circleView = circleViews.get(i);
            int finalI = i;
            circleView.setOnClickListener(v -> {
                page =1;
                initIndicator(finalI);
            });
        }

        csr.setOnSwipeRefreshListener(new CustomSwipeRefreshLayout.OnSwipeRefreshLayoutListener() {
            @Override
            public void onHeaderRefreshing() {
                page = 1;
                v.getCsr().setEnableRefresh(true);
                csr.setFooterRefreshAble(false);
                circle(circleIndex);
            }

            @Override
            public void onFooterRefreshing() {
                v.getCrv().addFooterView(v.getFooterView());
                circle(circleIndex);
            }
        });

        adapter.setStrListener((position, str) -> {
            CircleBean circleBean = adapter.getItem(position);
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user!=null && !TextUtils.isEmpty(user.getUserId())){

                m.addCircleComment(user.getUserId(), (circleBean.getCircleId()), str, new HttpSubscriber<String>(getContext(),true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(s,true);
                        page =1;
                        circle(circleIndex);
                    }
                });
            }
        });

        adapter.setActionListener((position, view) -> {
            CircleBean circleBean = adapter.getItem(position);
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user!=null && !TextUtils.isEmpty(user.getUserId())){
                m.addCircleLike(user.getUserId(), circleBean.getCircleId(), new HttpSubscriber<String>(getContext(),true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(s,true);
                        page =1;
                        circle(circleIndex);
                    }
                });
            }

        });
    }

    private void initData() {
        initIndicator(0);
    }



    @OnClick({R.id.btn_add,R.id.btn_circle})
    public void onMakeFriends(View view){
        switch (view.getId()){
            case R.id.btn_add:
                PublishCircleActivity.startForResult(getPresenterActivity(),circleIndex+1,REQUEST_CODE);
                break;
            case R.id.btn_circle:
                break;
            default:
                break;
        }
    }


    private void initIndicator(int index) {
        for (TextView circleView : circleViews) {
            circleView.setSelected(false);
        }
        circleViews.get(index).setSelected(true);
        circle(index);
        circleIndex = index;
    }

    private void circle(int index) {
        switch (index) {
            case 0:
                communityCircle();
                break;
            case 1:
                friendsCircle();
                break;
            case 2:
                customerCircle();
                break;
        }
    }

    private void customerCircle() {
        m.customerCircle(page + "", Constants.PAGE_SIZE + "",
                new HttpSubscriber<List<CircleBean>>(getContext(), false) {
                    @Override
                    public void onNext(List<CircleBean> circleBeans) {
                        onNextCircles(circleBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        onErrorCircles();
                    }
                });
    }



    private void communityCircle() {

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null) {
            m.communityCircle(user.getUserId(), user.getCommunityId(), page + "", Constants
                            .PAGE_SIZE + "",
                    new HttpSubscriber<List<CircleBean>>(getContext(), false) {
                        @Override
                        public void onNext(List<CircleBean> circleBeans) {
                            onNextCircles(circleBeans);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            onErrorCircles();
                        }
                    });
        }

    }


    private void friendsCircle() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null) {
            m.friendsCircle(user.getUserId(), page + "", Constants.PAGE_SIZE + "",
                    new HttpSubscriber<List<CircleBean>>(getContext(), false) {
                        @Override
                        public void onNext(List<CircleBean> circleBeans) {
                            onNextCircles(circleBeans);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            onErrorCircles();
                        }
                    });
        }

    }


    private void onNextCircles(List<CircleBean> circleBeans){
        v.getCsr().setRefreshFinished();
        if (circleBeans != null && circleBeans.size() > 0) {
            if (page == 1) {
                adapter.addList(circleBeans);
                csr.setFooterRefreshAble(true);
            } else {
                v.getCrv().removeFooterView(v.getFooterView());
                adapter.appendList(circleBeans);
            }

            if (circleBeans.size() == Constants.PAGE_SIZE) {
                page++;
            } else {
                v.getCsr().setFooterRefreshAble(false);
            }
        } else {
            v.getCsr().setFooterRefreshAble(false);
        }
    }

    private void onErrorCircles(){
        if (page == 1) {
            adapter.getList().clear();
            adapter.notifyDataSetChanged();
        }

        v.getCsr().setRefreshFinished();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode == REQUEST_CODE){
            int circleType = data.getIntExtra("circleType",0);
            if (this.circleIndex == circleType -1){
                page = 1;
                circle(circleIndex);
            }
        }
    }
}
