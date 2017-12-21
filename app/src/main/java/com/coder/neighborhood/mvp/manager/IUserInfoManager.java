package com.coder.neighborhood.mvp.manager;


public interface IUserInfoManager {

    /**
     * 保存用户信息
     *
     * @param userBean
     */
    void saveUserInfo(IUserInfo userBean);

    /**
     * 清空用户信息
     */
    void clearUserInfo();

    /**
     * 获取用户信息
     *
     * @return
     */
    IUserInfo getUserInfo();

    /**
     * 登录状态
     *
     * @return
     */
    boolean isLogin();

    /**
     * 用户token
     *
     * @return
     */
    String getToken();
}
