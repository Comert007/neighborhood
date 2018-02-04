package com.coder.neighborhood.activity.circle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.circle.DoingsDetailBean;
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
public class DoingsDetailActivity extends BaseActivity<DetailBannerView,CircleModel> {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private String activityId;
    private List<String> urls;

    public static void start(Context context, String activityId) {
        Intent intent = new Intent(context, DoingsDetailActivity.class);
        intent.putExtra("activityId",activityId);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_doings_detail;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @Override
    protected void init() {
        activityId = getIntent().getStringExtra("activityId");
        urls = new ArrayList<>();
        actvityDetail(activityId);
    }


    private void showData(DoingsDetailBean doingsDetailBean){
        startBanner(doingsDetailBean.getImgUrl());
        tvContent.setText(doingsDetailBean.getActivityInfo());
    }

    private void startBanner(List<DoingsDetailBean.ImgUrlBean> bannerBeans) {
        urls.clear();
        for (DoingsDetailBean.ImgUrlBean banner : bannerBeans) {
            urls.add(banner.getImgUrl());
        }

        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(position -> {
            if (urls.size() > 0) {

            }
        });
    }


    private void actvityDetail(String activityId){
        m.activityDetail(activityId, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<DoingsDetailBean>(this,true) {
                    @Override
                    public void onNext(DoingsDetailBean doingsDetailBean) {
                        if (doingsDetailBean !=null){
                            showData(doingsDetailBean);
                        }
                    }
                });
    }
}
