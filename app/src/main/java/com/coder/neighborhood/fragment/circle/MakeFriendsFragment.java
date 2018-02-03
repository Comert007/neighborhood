package com.coder.neighborhood.fragment.circle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.circle.PublicCircleActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.circle.MakeFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.circle.CircleBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.circle.MakeFriendsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;

/**
 * @author feng
 * @Date 2018/1/16
 */

public class MakeFriendsFragment extends BaseFragment<MakeFriendsView, CircleModel> {

    private List<TextView> circleViews = new ArrayList<>();
    private MakeFriendsAdapter adapter;
    private int page = 1;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_make_friends;
    }

    @Override
    protected void init() {
        adapter = new MakeFriendsAdapter(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout
                .layout_make_friends_header, null);
//        translate = view.findViewById(R.id.translate);
        circleViews.add(ButterKnife.findById(view, R.id.tv_community));
        circleViews.add(ButterKnife.findById(view, R.id.tv_friends));
        circleViews.add(ButterKnife.findById(view, R.id.tv_customer));
        ScreenUtil.scale(view);
        v.getCrv().setAdapter(adapter);
        v.getCrv().addHeadView(view);


        initIndicator(0);
        initListener();
    }

    private void initListener() {
        for (int i = 0; i < circleViews.size(); i++) {
            TextView circleView = circleViews.get(i);
            int finalI = i;
            circleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initIndicator(finalI);
                }
            });
        }
    }


    @OnClick({R.id.btn_add,R.id.btn_circle})
    public void onMakeFriends(View view){
        switch (view.getId()){
            case R.id.btn_add:
                PublicCircleActivity.startForResult(getPresenterActivity(),0x16);
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
    }

    private void circle(int index) {
        page = 1;
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
                        adapter.addList(circleBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (page == 1) {
                            adapter.getList().clear();
                            adapter.notifyDataSetChanged();
                        }
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
                            adapter.addList(circleBeans);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            if (page == 1) {
                                adapter.getList().clear();
                                adapter.notifyDataSetChanged();
                            }
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
                            adapter.addList(circleBeans);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            if (page == 1) {
                                adapter.getList().clear();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

    }
}
