package com.coder.neighborhood.api;

import android.text.TextUtils;

import com.coder.neighborhood.bean.ResponseBean;

import java.io.File;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class HomeApi extends BaseApi {

    public static final Observable<ResponseBean> banner(String bannerType) {
        AjaxParams params = getBaseParams();
        params.addParameters("bannerType", bannerType);
        return onPost(getActionUrl("app/getBanner"), params);
    }

    public static final Observable<ResponseBean> travels(String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("pageNo", pageNo);
        params.addParameters("pageSize", pageSize);
        return onPost(getActionUrl("app/getTravelList"), params);
    }

    public static final Observable<ResponseBean> travelDetail(String travelId){
        AjaxParams params = getBaseParams();
        params.addParameters("travelId", travelId);
        return onPost(getActionUrl("app/getTravelInfo"), params);
    }

    public static final Observable<ResponseBean> lostThings(String lostType,
                                                            String pageNo,
                                                            String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("lostType",lostType);
        params.addParameters("pageNo", pageNo);
        params.addParameters("pageSize", pageSize);

        return onPost(getActionUrl("app/getLostAndRecruitList"),params);
    }


    public static final Observable<ResponseBean> addLostThing(String userId,
                                                              String type,
                                                              String phone,
                                                              String questionContent,
                                                              String filePath){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("type",type);
        params.addParameters("phone",phone);
        params.addParameters("questionContent",questionContent);
        if (!TextUtils.isEmpty(filePath)){
            params.addParametersJPG("filePath",new File(filePath));
        }

        return onPost(getActionUrl("app/addLostAndRecruit"),params);
    }


    public static final Observable<ResponseBean> friendQuestions(String userId,
                                                                 String pageNo,
                                                                 String pageSize){

        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getFriendQuestionList"),params);
    }


    public static final Observable<ResponseBean> customerQuestions(String pageNo,
                                                                 String pageSize) {

        AjaxParams params = getBaseParams();
        params.addParameters("pageNo", pageNo);
        params.addParameters("pageSize", pageSize);

        return onPost(getActionUrl("app/getFriendQuestionList"), params);
    }
}
