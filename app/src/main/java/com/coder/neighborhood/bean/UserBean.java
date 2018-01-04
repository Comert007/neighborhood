package com.coder.neighborhood.bean;

import com.coder.neighborhood.mvp.manager.IUserInfo;

/**
 * Created by feng on 2018/1/4.
 */

public class UserBean implements IUserInfo{


    /**
     * imgUrl :
     * userInfo :
     * userStatus : 0
     * phone :
     * nickName :
     * idCode :
     * addressDisplayFlag :
     * communityName :
     * userName : 18200131081
     * communityId : 0
     * userId : bae52af65dd74cb7b48278fad6d311c5
     * liveStatus : 0
     */

    private String imgUrl;
    private String userInfo;
    private String userStatus;
    private String phone;
    private String nickName;
    private String idCode;
    private String addressDisplayFlag;
    private String communityName;
    private String userName;
    private String communityId;
    private String userId;
    private String liveStatus;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getAddressDisplayFlag() {
        return addressDisplayFlag;
    }

    public void setAddressDisplayFlag(String addressDisplayFlag) {
        this.addressDisplayFlag = addressDisplayFlag;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", userInfo='" + userInfo + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", phone='" + phone + '\'' +
                ", nickName='" + nickName + '\'' +
                ", idCode='" + idCode + '\'' +
                ", addressDisplayFlag='" + addressDisplayFlag + '\'' +
                ", communityName='" + communityName + '\'' +
                ", userName='" + userName + '\'' +
                ", communityId='" + communityId + '\'' +
                ", userId='" + userId + '\'' +
                ", liveStatus='" + liveStatus + '\'' +
                '}';
    }

    @Override
    public String getToken() {
        return userId;
    }
}
