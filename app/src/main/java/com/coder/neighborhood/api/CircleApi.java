package com.coder.neighborhood.api;

import android.text.TextUtils;

import com.coder.neighborhood.bean.ResponseBean;

import java.io.File;
import java.util.List;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/12/23.
 */

public class CircleApi extends BaseApi {

    public static final Observable<ResponseBean> customerCircle(String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getCircleList"),params);
    }


    public static final Observable<ResponseBean> communityCircle(String userId,String communityId,
                                                                 String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("communityId",communityId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getCommunityCircleList"),params);
    }



    public static final Observable<ResponseBean> friendsCircle(String userId,String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getFriendCircleList"),params);
    }


    public static final Observable<ResponseBean> addCircle(String userId,
                                                           List<String> paths,
                                                           String content,
                                                           String circleType,
                                                           String communityId){

        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        if (paths!=null && paths.size()>0){
            for (String path : paths) {
                params.addParametersJPG("file",new File(path));
            }
        }
        params.addParameters("content",content);
        params.addParameters("circleType",circleType);
        if (!TextUtils.isEmpty(communityId)){
            params.addParameters("communityId",communityId);
        }

        return onPost(getActionUrl("app/addCircle"),params);
    }

    public static final Observable<ResponseBean> addCircleComment(String userId,
                                                                  String circleId,
                                                                  String comments){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("circleId",circleId);
        params.addParameters("comments",comments);

        return onPost(getActionUrl("app/addCircleComments"),params);
    }


    public static final Observable<ResponseBean> addCircleLike(String userId,
                                                                  String circleId){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("circleId",circleId);

        return onPost(getActionUrl("app/likeCircle"),params);
    }


    public static final Observable<ResponseBean> events(String userId,String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getEventsList"),params);
    }


    public static final Observable<ResponseBean> addEvent(String userId,String content,List<String> paths){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("content",content);
        if (paths!=null && paths.size()>0){
            for (String path : paths) {
                params.addParametersJPG("file",new File(path));
            }
        }
        return onPost(getActionUrl("app/addEvents"),params);
    }

    public static final Observable<ResponseBean> topics(String userId,String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getTopicsList"),params);
    }

    public static final Observable<ResponseBean> addTopic(String userId,String content,List<String> paths){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("content",content);
        if (paths!=null && paths.size()>0){
            for (String path : paths) {
                params.addParametersJPG("file",new File(path));
            }
        }

        return onPost(getActionUrl("app/addTopic"),params);
    }



    public static final Observable<ResponseBean> avtivities(String userId,String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getActiviyList"),params);
    }

    public static final Observable<ResponseBean> addActivity(String userId,String content,List<String> paths){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("content",content);
        if (paths!=null && paths.size()>0){
            for (String path : paths) {
                params.addParametersJPG("file",new File(path));
            }
        }

        return onPost(getActionUrl("app/addActivity"),params);
    }


    public static final Observable<ResponseBean> makingFriends(String userId,String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getMakingFriendsList"),params);
    }


    public static final Observable<ResponseBean> addMakingFriend(String userId,String content,List<String> paths){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("content",content);
        if (paths!=null && paths.size()>0){
            for (String path : paths) {
                params.addParametersJPG("file",new File(path));
            }
        }

        return onPost(getActionUrl("app/addMakingFriend"),params);
    }

    public static final Observable<ResponseBean> makingFriendsDetail(String makingFriendsId){
        AjaxParams params = getBaseParams();
        params.addParameters("makingFriendsId",makingFriendsId);

        return onPost(getActionUrl("app/getMakingFriendsInfo"),params);
    }


    public static final Observable<ResponseBean> eventDetail(String eventsId){
        AjaxParams params = getBaseParams();
        params.addParameters("eventsId",eventsId);

        return onPost(getActionUrl("app/getEventsInfo"),params);
    }

    public static final Observable<ResponseBean> topicDetail(String topicsId){
        AjaxParams params = getBaseParams();
        params.addParameters("topicsId",topicsId);

        return onPost(getActionUrl("app/getTopicsInfo"),params);
    }


    public static final Observable<ResponseBean> activityDetail(String activityId){
        AjaxParams params = getBaseParams();
        params.addParameters("activityId",activityId);

        return onPost(getActionUrl("app/getActivityInfo"),params);
    }
}
