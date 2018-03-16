package com.coder.neighborhood.activity.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.circle.MakingFriendsDetailBean;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.home.DetailBannerView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author feng
 * @Date 2018/2/4
 */

@SuppressLint("Registered")
public class MakingFriendsDetailActivity extends BaseActivity<DetailBannerView,CircleModel> {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private String makingFriendsId;
    private List<String> urls;

    public static void start(Context context, String makingFriendsId) {
        Intent intent = new Intent(context, MakingFriendsDetailActivity.class);
        intent.putExtra("makingFriendsId",makingFriendsId);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void init() {
        makingFriendsId = getIntent().getStringExtra("makingFriendsId");
        urls = new ArrayList<>();
        makingFriendsDetail(makingFriendsId);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void showData(MakingFriendsDetailBean makingFriendsDetailBean){
        startBanner(makingFriendsDetailBean.getImgUrls());
        tvContent.setText(makingFriendsDetailBean.getMakingFriendsInfo());
    }

    private void startBanner(List<MakingFriendsDetailBean.ImgUrlsBean> bannerBeans) {
        urls.clear();
        for (MakingFriendsDetailBean.ImgUrlsBean banner : bannerBeans) {
            urls.add(banner.getImgUrl());
        }

        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(position -> {
            if (urls.size() > 0) {

            }
        });
    }


    private void makingFriendsDetail(String eventsId){
        m.makingFriendsDetail(eventsId, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<MakingFriendsDetailBean>(this,true) {
                    @Override
                    public void onNext(MakingFriendsDetailBean makingFriendsDetailBean) {
                        if (makingFriendsDetailBean !=null){
                            showData(makingFriendsDetailBean);
                        }
                    }
                });
    }
}
