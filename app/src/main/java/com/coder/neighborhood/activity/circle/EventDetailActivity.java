package com.coder.neighborhood.activity.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.circle.EventDetailBean;
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
public class EventDetailActivity extends BaseActivity<DetailBannerView,CircleModel> {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private String eventsId;
    private List<String> urls;

    public static void start(Context context, String eventsId) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra("eventsId",eventsId);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_event_detail;
    }

    @Override
    protected void init() {
        eventsId = getIntent().getStringExtra("eventsId");
        urls = new ArrayList<>();
        eventDetail(eventsId);
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void showData(EventDetailBean eventDetailBean){
        startBanner(eventDetailBean.getImgUrl());
        tvContent.setText(eventDetailBean.getEventsInfo());
    }

    private void startBanner(List<EventDetailBean.ImgUrlBean> bannerBeans) {
        urls.clear();
        for (EventDetailBean.ImgUrlBean banner : bannerBeans) {
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
        m.eventDetail(eventsId, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<EventDetailBean>(this,true) {
                    @Override
                    public void onNext(EventDetailBean eventDetailBean) {
                        if (eventDetailBean !=null){
                            showData(eventDetailBean);
                        }
                    }
                });
    }
}
