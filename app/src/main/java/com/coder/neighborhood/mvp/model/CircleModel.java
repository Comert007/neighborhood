package com.coder.neighborhood.mvp.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.CircleApi;
import com.coder.neighborhood.bean.circle.CircleBean;
import com.coder.neighborhood.bean.circle.DoingsDetailBean;
import com.coder.neighborhood.bean.circle.EventBean;
import com.coder.neighborhood.bean.circle.EventDetailBean;
import com.coder.neighborhood.bean.circle.MakingFriendsBean;
import com.coder.neighborhood.bean.circle.MakingFriendsDetailBean;
import com.coder.neighborhood.bean.circle.TopicBean;
import com.coder.neighborhood.bean.circle.TopicDetailBean;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

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
                .map(responseBean -> JSON.parseArray(responseBean.getData(),CircleBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void communityCircle(String userId,String communityId,
                                String pageNo,String pageSize,
                                HttpSubscriber<List<CircleBean>> httpSubscriber){

        CircleApi.communityCircle(userId,communityId,pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),CircleBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void friendsCircle(String userId,
                                String pageNo,String pageSize,
                                HttpSubscriber<List<CircleBean>> httpSubscriber){

        CircleApi.friendsCircle(userId,pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),CircleBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void addCircle(String userId,
                          List<String> paths,
                          String content,
                          String circleType,
                          String communityId,
                          LifecycleTransformer transformer,
                          HttpSubscriber<String> httpSubscriber){

        CircleApi.addCircle(userId, paths, content, circleType, communityId)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void addCircleComment(String userId,
                                 String circleId,
                                 String comments,
                                 HttpSubscriber<String> httpSubscriber){

        CircleApi.addCircleComment(userId, circleId, comments)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void addCircleLike(String userId,
                                 String circleId,
                                 HttpSubscriber<String> httpSubscriber){

        CircleApi.addCircleLike(userId, circleId)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void events(String userId,String pageNo,String pageSize,HttpSubscriber<List<EventBean>> httpSubscriber){
        CircleApi.events(userId, pageNo, pageSize)
                .map(responseBean -> JSONArray.parseArray(responseBean.getData(),EventBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void topics(String userId,String pageNo,String pageSize,HttpSubscriber<List<TopicBean>> httpSubscriber){
        CircleApi.topics(userId, pageNo, pageSize)
                .map(responseBean -> JSONArray.parseArray(responseBean.getData(),TopicBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void activities(String userId,String pageNo,String pageSize,HttpSubscriber<List<EventBean>> httpSubscriber){
        CircleApi.avtivities(userId, pageNo, pageSize)
                .map(responseBean -> JSONArray.parseArray(responseBean.getData(),EventBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void makingFriends(String userId,String pageNo,String pageSize,HttpSubscriber<List<MakingFriendsBean>> httpSubscriber){
        CircleApi.makingFriends(userId, pageNo, pageSize)
                .map(responseBean -> JSONArray.parseArray(responseBean.getData(),MakingFriendsBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void addMakingFriends(String userId,String content,List<String> paths,LifecycleTransformer transformer,
                         HttpSubscriber<String> httpSubscriber){
        CircleApi.addMakingFriend(userId, content, paths)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void addEvent(String userId,String content,List<String> paths,LifecycleTransformer transformer,
                         HttpSubscriber<String> httpSubscriber){
        CircleApi.addEvent(userId, content, paths)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addTopic(String userId,String content,List<String> paths,LifecycleTransformer transformer,
                         HttpSubscriber<String> httpSubscriber){
        CircleApi.addTopic(userId, content, paths)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addActivity(String userId,String content,List<String> paths,LifecycleTransformer transformer,
                            HttpSubscriber<String> httpSubscriber){
        CircleApi.addActivity(userId, content, paths)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void eventDetail(String eventsId,LifecycleTransformer transformer,
                            HttpSubscriber<EventDetailBean> httpSubscriber){

        CircleApi.eventDetail(eventsId)
                .map(responseBean -> JSON.parseObject(responseBean.getData(),EventDetailBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void topicDetail(String eventsId,LifecycleTransformer transformer,
                            HttpSubscriber<TopicDetailBean> httpSubscriber){

        CircleApi.topicDetail(eventsId)
                .map(responseBean -> JSON.parseObject(responseBean.getData(),TopicDetailBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void activityDetail(String activityId,LifecycleTransformer transformer,
                            HttpSubscriber<DoingsDetailBean> httpSubscriber){

        CircleApi.activityDetail(activityId)
                .map(responseBean -> JSON.parseObject(responseBean.getData(),DoingsDetailBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void makingFriendsDetail(String makingFriendsId,LifecycleTransformer transformer,
                               HttpSubscriber<MakingFriendsDetailBean> httpSubscriber){

        CircleApi.makingFriendsDetail(makingFriendsId)
                .map(responseBean -> JSON.parseObject(responseBean.getData(),MakingFriendsDetailBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }
}
