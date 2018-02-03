package com.coder.neighborhood.mvp.model;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.CircleApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.circle.CircleBean;

import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class CircleModel extends BaseModel {

    public void customerCircle(String pageNo,
                       String pageSize,
                       HttpSubscriber<List<CircleBean>> httpSubscriber){

        CircleApi.customerCircle(pageNo, pageSize)
                .map(new Func1<ResponseBean, List<CircleBean>>() {
                    @Override
                    public List<CircleBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(),CircleBean.class);
                    }
                }).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void communityCircle(String userId,String communityId,
                                String pageNo,String pageSize,
                                HttpSubscriber<List<CircleBean>> httpSubscriber){

        CircleApi.communityCircle(userId,communityId,pageNo, pageSize)
                .map(new Func1<ResponseBean, List<CircleBean>>() {
                    @Override
                    public List<CircleBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(),CircleBean.class);
                    }
                }).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void friendsCircle(String userId,
                                String pageNo,String pageSize,
                                HttpSubscriber<List<CircleBean>> httpSubscriber){

        CircleApi.friendsCircle(userId,pageNo, pageSize)
                .map(new Func1<ResponseBean, List<CircleBean>>() {
                    @Override
                    public List<CircleBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(),CircleBean.class);
                    }
                }).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void addCircle(){

    }
}
