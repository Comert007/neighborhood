package com.coder.neighborhood.mvp.aop;

import android.content.Context;

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

    private static final String TAG = "CheckUser";

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
            Context context = (Context) joinPoint.getThis();
            UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
            if (user!=null && "1".equals(user.getUserStatus())) {
                return joinPoint.proceed();
            } else {
                DialogUtils.showDialog(context, "用户认证", "该操作需先进行用户认证", true,
                        "取消", (dialog, which) -> {
                            UserAuthenticationActivity.start(context);
                            dialog.dismiss();
                        }, true, "认证", (dialog, which) -> {
                            dialog.dismiss();
                        }).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
