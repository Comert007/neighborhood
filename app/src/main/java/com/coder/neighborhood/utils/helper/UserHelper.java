package com.coder.neighborhood.utils.helper;

import android.content.Context;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.activity.user.UserAuthenticationActivity;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.utils.DialogUtils;

/**
 * @author feng
 * @Date 2018/3/15.
 */

public class UserHelper {

    private boolean isLogicUser (){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        return "1".equals(user.getUserStatus());
    }


    private void logic(Context context){
        if (!isLogicUser()){
            showLogic(context);
        }
    }


    private void showLogic(Context context){

        DialogUtils.showDialog(context, "用户认证", "该操作需先进行用户认证", true,
                "取消", (dialog, which) -> {
                    UserAuthenticationActivity.start(context);
                    dialog.dismiss();
                }, true, "认证", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }


}
