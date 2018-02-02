package com.coder.neighborhood.fragment.circle;

import android.view.LayoutInflater;
import android.view.View;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.circle.MakeFriendsAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.circle.CircleBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.fragment.BaseFragment;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.circle.MakeFriendsView;

import java.util.List;

import ww.com.core.ScreenUtil;
import ww.com.core.widget.TranslateTabBar;

/**
 * @author feng
 * @Date 2018/1/16
 */

public class MakeFriendsFragment extends BaseFragment<MakeFriendsView,CircleModel> {

    TranslateTabBar translate;

    private MakeFriendsAdapter adapter;
    private int page =1;



    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_make_friends;
    }

    @Override
    protected void init() {
        adapter = new MakeFriendsAdapter(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_make_friends_header,null);
        translate = view.findViewById(R.id.translate);
        ScreenUtil.scale(view);
        v.getCrv().setAdapter(adapter);
        v.getCrv().addHeadView(view);

        circle(0);

        translate.setOnTabChangeListener(new TranslateTabBar.OnTabChangeListener() {
            @Override
            public void onChange(int index) {
                circle(index);
            }
        });
    }

    private void circle(int index){
        page = 1;
        switch (index){
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

    private void customerCircle(){

        m.customerCircle(page+"", Constants.PAGE_SIZE + "",
                new HttpSubscriber<List<CircleBean>>(getContext(),false) {
            @Override
            public void onNext(List<CircleBean> circleBeans) {
                adapter.addList(circleBeans);
            }
        });
    }

    private void communityCircle(){

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null ){
            m.communityCircle(user.getUserId(),user.getCommunityId(),page+"", Constants.PAGE_SIZE + "",
                    new HttpSubscriber<List<CircleBean>>(getContext(),false) {
                        @Override
                        public void onNext(List<CircleBean> circleBeans) {
                            adapter.addList(circleBeans);
                        }
                    });
        }

    }


    private void friendsCircle(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null ){
            m.friendsCircle(user.getUserId(),page+"", Constants.PAGE_SIZE + "",
                    new HttpSubscriber<List<CircleBean>>(getContext(),false) {
                        @Override
                        public void onNext(List<CircleBean> circleBeans) {
                            adapter.addList(circleBeans);
                        }
                    });
        }

    }
}
