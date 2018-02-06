package com.coder.neighborhood.mvp.model.home;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.HomeApi;
import com.coder.neighborhood.api.MallApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.home.HelpDetailBean;
import com.coder.neighborhood.bean.home.QuestionBean;
import com.coder.neighborhood.bean.home.ThingDetailBean;
import com.coder.neighborhood.bean.home.ThingsBean;
import com.coder.neighborhood.bean.home.TravelBean;
import com.coder.neighborhood.bean.home.TravelDetailBean;
import com.coder.neighborhood.mvp.model.BaseModel;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class HomeModel extends BaseModel {


    public void onBanner(String bannerType,
                         HttpSubscriber<List<BannerBean>> httpSubscriber){
        HomeApi.banner(bannerType)
                .map(new Func1<ResponseBean, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> call(ResponseBean responseBean) {
                        List<BannerBean> strings = JSON.parseArray(responseBean.getData(), BannerBean.class);
                        return strings;
                    }
                }).compose(RxHelper.<List<BannerBean>>cutMain())
                .subscribe(httpSubscriber);
    }

    public void onBanner(String bannerType,
                         LifecycleTransformer transformer,
                         HttpSubscriber<List<String>> httpSubscriber){

        HomeApi.banner(bannerType)
                .map(new Func1<ResponseBean, List<String>>() {
                    @Override
                    public List<String> call(ResponseBean responseBean) {
                        List<String> strings = JSON.parseArray(responseBean.getData(), String.class);
                        return strings;
                    }
                }).compose(RxHelper.<List<String>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }


    public void travels(String pageNo,String pageSize,
                        LifecycleTransformer transformer,
                        HttpSubscriber<List<TravelBean>> httpSubscriber){
        HomeApi.travels(pageNo, pageSize)
                .map(new Func1<ResponseBean, List<TravelBean>>() {
                    @Override
                    public List<TravelBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(),TravelBean.class);
                    }
                }).compose(RxHelper.<List<TravelBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void travelDetail(String travelId, LifecycleTransformer transformer,
                             HttpSubscriber<TravelDetailBean> httpSubscriber){

        HomeApi.travelDetail(travelId)
                .map(new Func1<ResponseBean, TravelDetailBean>() {
                    @Override
                    public TravelDetailBean call(ResponseBean responseBean) {
                        return JSON.parseObject(responseBean.getData(),TravelDetailBean.class);
                    }
                }).compose(RxHelper.<TravelDetailBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addCart(String userId,
                        String itemId,
                        String quantity,
                        LifecycleTransformer transformer,
                        HttpSubscriber<String> httpSubscriber){


        MallApi.addCart(userId, itemId, quantity)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        responseBean.getData();
                        return responseBean.getMessage();
                    }
                }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void lostThings(String lostType,
                           String pageNo,
                           String pageSize,
                           HttpSubscriber<List<ThingsBean>> httpSubscriber){

        HomeApi.lostThings(lostType, pageNo, pageSize)
                .map(new Func1<ResponseBean, List<ThingsBean>>() {
                    @Override
                    public List<ThingsBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(),ThingsBean.class);
                    }
                }).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
//
        
    }
    
    public void thingsDetail(String lostId,LifecycleTransformer transformer,
                             HttpSubscriber<ThingDetailBean> httpSubscriber){
        HomeApi.thingsDetail(lostId)
                .map(new Func1<ResponseBean, ThingDetailBean>() {
                    @Override
                    public ThingDetailBean call(ResponseBean responseBean) {
                        return JSON.parseObject(responseBean.getData(),ThingDetailBean.class);
                    }
                }).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addLostThing(String userId,
                             String type,
                             String phone,
                             String questionContent,
                             String filePath,
                             LifecycleTransformer transformer,
                             HttpSubscriber<String> httpSubscriber){

        HomeApi.addLostThing(userId, type, phone, questionContent, filePath)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void friendQuestions(String userId,
                                String pageNo,
                                String pageSize,HttpSubscriber<List<QuestionBean>> httpSubscriber){


        HomeApi.friendQuestions(userId,pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),QuestionBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);


    }


    public void customQuestions(String pageNo,
                                String pageSize,
                                HttpSubscriber<List<QuestionBean>> httpSubscriber){

        HomeApi.customerQuestions(pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),QuestionBean.class)).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void addHelpQuestion(String userId,
                                String questionType,
                                String questionContent,
                                String bounty,
                                LifecycleTransformer transformer,
                                HttpSubscriber<String> httpSubscriber){
        HomeApi.addHelpQuestion(userId, questionType, questionContent, bounty)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getData();
                    }
                }).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addHelpQuestionReply(String userId,
                                     String questionId,
                                     String comments,
                                     LifecycleTransformer transformer,
                                     HttpSubscriber<String> httpSubscriber){
        HomeApi.addHelpQuestionReply(userId, questionId, comments)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void feedBackQuestion(String userId,
                                String content,
                                LifecycleTransformer transformer,
                                HttpSubscriber<String> httpSubscriber){
        HomeApi.feedBackQuestion(userId, content)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getData();
                    }
                }).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void questionsComment(String questionId,
                                 String pageNo,
                                 String pageSize,LifecycleTransformer transformer,
                                 HttpSubscriber<List<HelpDetailBean>> httpSubscriber){

        HomeApi.questionsComment(questionId, pageNo, pageSize)
                .map(new Func1<ResponseBean, List<HelpDetailBean>>() {
                    @Override
                    public List<HelpDetailBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(),HelpDetailBean.class);
                    }
                }).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addSecondGoods(String userId,
                               String itemName,
                               String itemPirce,
                               String itemQuantiry,
                               String itemType,
                               String itemDetails,
                               String path,
                               LifecycleTransformer transformer,
                               HttpSubscriber<String> httpSubscriber){
        HomeApi.addSecondGoods(userId, itemName, itemPirce, itemQuantiry, itemType, itemDetails, path)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void certification(String userId,
                              String idCodeName,
                              String idCode,
                              LifecycleTransformer transformer,
                              HttpSubscriber<String> httpSubscriber){

    }

    public void delivery(String userId,
                         String name,
                         String expressId,
                         String phone,
                         String remark,
                         LifecycleTransformer transformer,
                         HttpSubscriber<String> httpSubscriber){
        HomeApi.delivery(userId, name, expressId, phone, remark)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

}
