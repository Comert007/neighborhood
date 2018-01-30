package com.coder.neighborhood.mvp.model.user;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.UserApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.user.FriendBean;
import com.coder.neighborhood.mvp.model.IModel;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * Created by feng on 2017/12/23.
 */

public class UserModel implements IModel {

    @Override
    public void onAttach(@NonNull Context context) {

    }

    public void signup(String username,
                       String password,
                       LifecycleTransformer transformer, HttpSubscriber<String> httpSubscriber){

        UserApi.signup(username, password)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getMessage();
                    }
                })
                .compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void findPassword(String username,
                       String password,
                       LifecycleTransformer transformer, HttpSubscriber<String> httpSubscriber){

        UserApi.findPassword(username, password)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getMessage();
                    }
                })
                .compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }



    public void modifyPass(String username,
                       String oldPassword,
                           String newPassword,
                       LifecycleTransformer transformer, HttpSubscriber<String> httpSubscriber){

        UserApi.modifyPassword(username, oldPassword,newPassword)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getMessage();
                    }
                })
                .compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void loginUp(String username,
                        String password,
                        LifecycleTransformer transformer,
                        HttpSubscriber<ResponseBean> httpSubscriber){

        UserApi.loginup(username, password)
                .compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }
    
    
    public void friends(String userId,
                        String pageNo,
                        String pageSize,
                        LifecycleTransformer transformer,
                        HttpSubscriber<List<FriendBean>> httpSubscriber){
        UserApi.friends(userId, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),FriendBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void signIn(String userId,HttpSubscriber<ResponseBean> httpSubscriber){
        UserApi.signIn(userId)
                .compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void querySign(String userId,HttpSubscriber<String> httpSubscriber){
        UserApi.querySign(userId)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getData();
                    }
                }).compose(RxHelper.cutMain())
                .subscribe(httpSubscriber);
    }

    public void addFriend(String userId,String freindId,String easemodUsername,
                          LifecycleTransformer transformer,
                          HttpSubscriber<String> httpSubscriber){

        UserApi.addFriend(userId, freindId, easemodUsername)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getMessage();
                    }
                }).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }
}
