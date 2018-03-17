package com.coder.neighborhood.fragment.circle;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.MainActivity;
import com.coder.neighborhood.activity.circle.DoingsDetailActivity;
import com.coder.neighborhood.activity.circle.EventDetailActivity;
import com.coder.neighborhood.activity.circle.MakingFriendsActivity;
import com.coder.neighborhood.activity.circle.PublishCircleActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.activity.user.FriendsInfoActivity;
import com.coder.neighborhood.adapter.circle.MakeFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.circle.CircleBean;
import com.coder.neighborhood.bean.circle.EventBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.aop.CheckUser;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.circle.MakeFriendsView;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    private int circleIndex = 0;
    private final int REQUEST_CODE = 0x16;

    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;

    private TextView eventTop1;
    private TextView eventTop2;
    private TextView activityTop1;
    private TextView activityTop2;

    private ImageView ivEventTop1;
    private ImageView ivEventTop2;
    private ImageView ivActivityTop1;
    private ImageView ivActivityTop2;

    private LinearLayout llEventMore;
    private LinearLayout llActivityMore;

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
        eventTop1 = ButterKnife.findById(view, R.id.event_top_1);
        eventTop2 = ButterKnife.findById(view, R.id.event_top_2);
        ivEventTop1 = ButterKnife.findById(view, R.id.iv_event_top_1);
        ivEventTop2 = ButterKnife.findById(view, R.id.iv_event_top_2);

        activityTop1 = ButterKnife.findById(view, R.id.activity_top_1);
        activityTop2 = ButterKnife.findById(view, R.id.activity_top_2);
        ivActivityTop1 = ButterKnife.findById(view, R.id.iv_activity_top_1);
        ivActivityTop2 = ButterKnife.findById(view, R.id.iv_activity_top_2);

        llEventMore = ButterKnife.findById(view, R.id.ll_event_more);
        llEventMore.setOnClickListener(v -> ((MainActivity) getPresenterActivity())
                .changeCircleMnue(1));
        llActivityMore = ButterKnife.findById(view, R.id.ll_activity_more);
        llActivityMore.setOnClickListener(v -> ((MainActivity) getPresenterActivity())
                .changeCircleMnue(3));

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
                page = 1;
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
            if (user != null && !TextUtils.isEmpty(user.getUserId())) {

                m.addCircleComment(user.getUserId(), (circleBean.getCircleId()), str, new
                        HttpSubscriber<String>(getContext(), true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(s, true);
                        page = 1;
                        circle(circleIndex);
                    }
                });
            }
        });

        adapter.setActionListener((position, view) -> {
            CircleBean circleBean = adapter.getItem(position);
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user != null && !TextUtils.isEmpty(user.getUserId())) {
                m.addCircleLike(user.getUserId(), circleBean.getCircleId(), new
                        HttpSubscriber<String>(getContext(), true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast(s, true);
                        page = 1;
                        circle(circleIndex);
                    }
                });
            }

        });
    }

    private void initData() {
        initIndicator(0);
    }


    public void showEventTopContent(List<EventBean> eventBeans) {
        if (eventBeans != null && eventBeans.size() > 0) {
            eventTop1.setText(eventBeans.get(0).getActivityInfo());
            eventTop1.setOnClickListener(v -> EventDetailActivity.start(getContext(),eventBeans.get(0).getActivityId()));
            ImageLoader.getInstance().displayImage(eventBeans.get(0).getImgUrl(), ivEventTop1,
                    BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            if (eventBeans.size() > 1) {
                eventTop2.setText(eventBeans.get(1).getActivityInfo());
                eventTop2.setOnClickListener(v -> EventDetailActivity.start(getContext(),eventBeans.get(1).getActivityId()));
                ImageLoader.getInstance().displayImage(eventBeans.get(1).getImgUrl(), ivEventTop2,
                        BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            }
        }
    }

    public void showActivityTopContent(List<EventBean> activityBeans) {
        if (activityBeans != null && activityBeans.size() > 0) {
            activityTop1.setOnClickListener(v -> DoingsDetailActivity.start(getContext(),activityBeans.get(0).getActivityId()));
            activityTop1.setText(activityBeans.get(0).getActivityInfo());
            ImageLoader.getInstance().displayImage(activityBeans.get(0).getImgUrl(), ivActivityTop1,
                    BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            if (activityBeans.size() > 1) {
                activityTop2.setOnClickListener(v -> DoingsDetailActivity.start(getContext(),activityBeans.get(1).getActivityId()));
                activityTop2.setText(activityBeans.get(1).getActivityInfo());
                ImageLoader.getInstance().displayImage(activityBeans.get(1).getImgUrl(), ivActivityTop2,
                        BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            }
        }
    }


    @OnClick({R.id.btn_add, R.id.btn_circle,R.id.btn_making_friends})
    public void onMakeFriends(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                publish();
                break;
            case R.id.btn_circle:
                UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
                if (user != null && !TextUtils.isEmpty(user.getUserId())) {
                    FriendsInfoActivity.start(getContext(), user.getUserId());
                }
                break;
            case R.id.btn_making_friends:
                MakingFriendsActivity.start(getContext(),"");
                break;
            default:
                break;
        }
    }

    @CheckUser
    private void publish(){
        PublishCircleActivity.startForResult(getPresenterActivity(), circleIndex + 1,
                REQUEST_CODE);
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


    private void onNextCircles(List<CircleBean> circleBeans) {
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
            adapter.getList().clear();
            adapter.notifyDataSetChanged();
            v.getCsr().setFooterRefreshAble(false);
        }
    }

    private void onErrorCircles() {
        if (page == 1) {
            adapter.getList().clear();
            adapter.notifyDataSetChanged();
        }

        v.getCsr().setRefreshFinished();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            int circleType = data.getIntExtra("circleType", 0);
            if (this.circleIndex == circleType - 1) {
                page = 1;
                circle(circleIndex);
            }
        }
    }

}
