package com.coder.neighborhood.api;

import android.text.TextUtils;

import com.coder.neighborhood.bean.ResponseBean;

import java.io.File;

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
                                                           String path,
                                                           String content,
                                                           String circleType,
                                                           String communityId){

        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParametersJPG("file",new File(path));
        params.addParameters("content",content);
        params.addParameters("circleType",circleType);
        if (!TextUtils.isEmpty(communityId)){
            params.addParameters("communityId",communityId);
        }

        return onPost(getActionUrl("app/addCircle"),params);
    }
}
