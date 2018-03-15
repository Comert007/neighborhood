package com.coder.neighborhood.mvp.aop;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.activity.user.UserAuthenticationActivity;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.utils.DialogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author feng
 * @Date 2018/3/15.
 */

@Aspect
public class CheckUserAspectJ {


    /**
     * 找到处理的切点
     * * *(..)  可以处理CheckUser这个类所有的方法
     */
    @Pointcut("execution(@com.coder.neighborhood.mvp.aop.CheckUser  * *(..))")
    public void executionCheckUser() {

    }

    /**
     * 处理切面
     *
     * @param joinPoint
     * @return
     */
    @Around("executionCheckUser()")
    public Object checkUser(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckUser checkUser = signature.getMethod().getAnnotation(CheckUser.class);
        if (checkUser != null) {
            Object obj = joinPoint.getThis();
             Context context = null;
            if (obj instanceof Activity){
                context = (Context) obj;
            }else if (obj instanceof Fragment){
                context = ((Fragment) obj).getContext();
            }
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user!=null && "1".equals(user.getUserStatus())) {
                return joinPoint.proceed();
            } else {
                Context finalContext = context;
                DialogUtils.showDialog(context, "用户认证", "该操作需先进行用户认证", true,
                        "认证", (dialog, which) -> {
                            UserAuthenticationActivity.start(finalContext);
                            dialog.dismiss();
                        }, true, "取消", (dialog, which) -> {
                            dialog.dismiss();
                        }).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
