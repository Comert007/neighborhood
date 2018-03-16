package com.coder.neighborhood.api;

import android.text.TextUtils;

import com.coder.neighborhood.bean.ResponseBean;

import java.io.File;
import java.util.List;

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

    public static final Observable<ResponseBean> thingsDetail(String lostId){
        AjaxParams params = getBaseParams();
        params.addParameters("lostId",lostId);

        return onPost(getActionUrl("app/getLostAndRecruitInfo"),params);
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
        params.addParameters("lostContent",questionContent);
        if (!TextUtils.isEmpty(filePath)){
            params.addParametersJPG("file",new File(filePath));
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

    public static final Observable<ResponseBean> addHelpQuestion(String userId,
                                                                 String questionType,
                                                                 String questionContent,
                                                                 String bounty){


        AjaxParams params = getBaseParams();
        params.addParameters("userId", userId);
        params.addParameters("questionType", questionType);
        params.addParameters("questionContent", questionContent);
        if (!TextUtils.isEmpty(bounty)){
            params.addParameters("bounty", bounty);
        }

        return onPost(getActionUrl("app/addQuestion"), params);

    }


    public static final Observable<ResponseBean> addHelpQuestionReply(String userId,
                                                                 String questionId,
                                                                 String comments){


        AjaxParams params = getBaseParams();
        params.addParameters("userId", userId);
        params.addParameters("questionId", questionId);
        params.addParameters("comments", comments);

        return onPost(getActionUrl("app/addQuestionComments"), params);

    }

    public static final Observable<ResponseBean> feedBackQuestion(String userId,
                                                                 String content){


        AjaxParams params = getBaseParams();
        params.addParameters("userId", userId);
        params.addParameters("content", content);

        return onPost(getActionUrl("app/addFeedback"), params);

    }

    public static final Observable<ResponseBean> commentGoods(String userId,String itemId,
                                                                  String comments){
        AjaxParams params = getBaseParams();
        params.addParameters("userId", userId);
        params.addParameters("itemId", itemId);
        params.addParameters("comments", comments);

        return onPost(getActionUrl("app/addItemComments"), params);

    }


    public static final Observable<ResponseBean> questionsComment(String questionId,
                                                                  String pageNo,
                                                                  String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("questionId",questionId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getQuestionCommentsList"),params);
    }


    public static final Observable<ResponseBean> addSecondGoods(String userId,
                                                                String itemName,
                                                                String itemPirce,
                                                                String itemQuantiry,
                                                                String itemType,
                                                                String itemDetails,
                                                                List<String> paths){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("itemName",itemName);
        params.addParameters("itemPirce",itemPirce);
        params.addParameters("itemQuantiry",itemQuantiry);
        params.addParameters("itemType",itemType);
        params.addParameters("itemDetails",itemDetails);
        if (paths!=null && paths.size()>0){
            for (String path : paths) {
                params.addParametersJPG("file",new File(path));
            }

        }
        return onPost(getActionUrl("app/addItem"),params);

    }


    public static final Observable<ResponseBean> certification(String userId,
                                                               String idCodeName,
                                                               String idCode,
                                                               String communityId){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("idCodeName",idCodeName);
        params.addParameters("idCode",idCode);
        params.addParameters("communityId",communityId);

        return onPost(getActionUrl("app/certificationUser"),params);
    }

    public static final Observable<ResponseBean> delivery(String userId,
                                                          String name,
                                                          String expressId,
                                                          String phone,
                                                          String remark){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("name",name);
        params.addParameters("expressId",expressId);
        params.addParameters("phone",phone);
        params.addParameters("remark",remark);

        return onPost(getActionUrl("app/addTakeExpress"),params);
    }

    public static final Observable<ResponseBean> goodsType(){
        //app/getItemCategoryArray
        AjaxParams params = getBaseParams();
        return onPost(getActionUrl("app/getItemCategoryArray"),params);
    }

}
