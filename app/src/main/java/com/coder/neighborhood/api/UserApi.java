package com.coder.neighborhood.api;

import android.text.TextUtils;

import com.coder.neighborhood.bean.ResponseBean;

import java.io.File;

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

    public static final Observable<ResponseBean> findPassword(String username,String password){
        AjaxParams params = getBaseParams();
        params.addParameters("username",username);
        params.addParameters("password",password);
        return onPost(getActionUrl("app/findPassword"),params);
    }

    public static final Observable<ResponseBean> modifyPassword(String username,String oldPassword,String newPassword){
        AjaxParams params = getBaseParams();
        params.addParameters("username",username);
        params.addParameters("oldPassword",oldPassword);
        params.addParameters("newPassword",newPassword);
        return onPost(getActionUrl("app/modifyPsw"),params);
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

    public static final Observable<ResponseBean> searchFriends(String userName,
                                                         String pageNo,
                                                         String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userName",userName);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getUserList"),params);
    }



    public static final Observable<ResponseBean> signIn(String userId){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);

        return onPost(getActionUrl("app/doClockIn"),params);
    }

    public static final Observable<ResponseBean> querySign(String userId){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);

        return onPost(getActionUrl("app/getClockIn"),params);
    }

    public static final Observable<ResponseBean> addFriend(String userId,String friendId,String easemodUsername){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("friendId",friendId);
        params.addParameters("easemodUsername",easemodUsername);

        return onPost(getActionUrl("app/addFriend"),params);

    }

    public static final Observable<ResponseBean> friendInfo(String userId){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);

        return onPost(getActionUrl("app/getFriendInfo"),params);
    }

    public static final Observable<ResponseBean> profileCircles(String userId, String pageNo, String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getMeCircleList"),params);
    }



    public static final Observable<ResponseBean> modifyUserInfo(String userId,
                                                                String nickName,
                                                                String phone,
                                                                String addressDisplayFlag,
                                                                String userInfo){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        if (!TextUtils.isEmpty(nickName)){
            params.addParameters("nickName",nickName);
        }
        if (!TextUtils.isEmpty(phone)) {
            params.addParameters("phone", phone);
        }
        if (!TextUtils.isEmpty(addressDisplayFlag)) {
            params.addParameters("addressDisplayFlag", addressDisplayFlag);
        }
        if (!TextUtils.isEmpty(userInfo)) {
            params.addParameters("userInfo", userInfo);
        }

        return onPost(getActionUrl("app/modifyUserInfo"),params);

    }

    public static final Observable<ResponseBean> modifyAvatar(String userId,String path){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParametersJPG("file",new File(path));

        return onPost(getActionUrl("app/editImg"),params);
    }

    public static final Observable<ResponseBean> communities(){
        AjaxParams params = getBaseParams();
        return onPost(getActionUrl("app/getCommunityList"),params);
    }
}
