package com.coder.neighborhood.api;

import com.coder.neighborhood.bean.ResponseBean;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/12/23.
 */

public class UserApi extends BaseApi {

    public static final Observable<ResponseBean> signup(String username,String password){
        AjaxParams params = getBaseParams();
        params.addParameters("username",username);
        params.addParameters("password",password);
        return onPost(getActionUrl("app/signup"),params);
    }


    public static final Observable<ResponseBean> loginup(String username,String password){
        AjaxParams params = getBaseParams();
        params.addParameters("username",username);
        params.addParameters("password",password);
        return onPost(getActionUrl("app/signin"),params);
    }

    public static final Observable<ResponseBean> friends(String userId,
                                                         String pageNo,
                                                         String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getFriendList"),params);
    }
}
