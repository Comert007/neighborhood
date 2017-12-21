package com.coder.neighborhood.mvp.manager;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.File;

import ww.com.core.utils.ACache;


public class UserInfoDefaultManger<U extends IUserInfo> implements IUserInfoManager {
    private static final String CACHE_DIR = "app_auth";
    private static final String KEY_USER_ACCOUNT = "USER_ACCOUNT";

    ACache cache;
    U userBean;

    public UserInfoDefaultManger(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            throw new RuntimeException();
        }
        cache = ACache.get(new File(cacheDir.getParent(),
                CACHE_DIR));
    }

    @Override
    public void saveUserInfo(IUserInfo userBean) {
        if (userBean == null) {
            cache.remove(KEY_USER_ACCOUNT);
        } else {
            cache.put(KEY_USER_ACCOUNT, userBean);
        }
        this.userBean = (U) userBean;
    }

    @Override
    public void clearUserInfo() {
        saveUserInfo(null);
    }

    @Override
    public U getUserInfo() {
        if (this.userBean != null) {
            return this.userBean;
        }
        this.userBean = cache.getAsObject(KEY_USER_ACCOUNT);
        return this.userBean;
    }

    @Override
    public boolean isLogin() {
        return getUserInfo() != null;
    }

    @Override
    @Nullable
    public String getToken() {
        U userBean = getUserInfo();
        if (userBean != null) {
            return userBean.getToken();
        }
        return null;
    }
}
