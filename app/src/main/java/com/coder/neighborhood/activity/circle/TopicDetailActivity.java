package com.coder.neighborhood.activity.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.circle.TopicDetailBean;
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
public class TopicDetailActivity extends BaseActivity<DetailBannerView,CircleModel> {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private String topicsId;
    private List<String> urls;

    public static void start(Context context, String eventsId) {
        Intent intent = new Intent(context, TopicDetailActivity.class);
        intent.putExtra("topicsId",eventsId);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void init() {
        topicsId = getIntent().getStringExtra("topicsId");
        urls = new ArrayList<>();
        eventDetail(topicsId);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void showData(TopicDetailBean topicDetailBean){
        startBanner(topicDetailBean.getImgUrls());
        tvContent.setText(topicDetailBean.getTopicsInfo());
    }

    private void startBanner(List<TopicDetailBean.ImgUrlBean> bannerBeans) {
        urls.clear();
        for (TopicDetailBean.ImgUrlBean banner : bannerBeans) {
            urls.add(banner.getImgUrl());
        }

        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(position -> {
            if (urls.size() > 0) {

            }
        });
    }


    private void eventDetail(String eventsId){
        m.topicDetail(eventsId, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<TopicDetailBean>(this,true) {
                    @Override
                    public void onNext(TopicDetailBean topicDetailBean) {
                        if (topicDetailBean !=null){
                            showData(topicDetailBean);
                        }
                    }
                });
    }
}
