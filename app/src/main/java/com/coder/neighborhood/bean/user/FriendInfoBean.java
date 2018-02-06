package com.coder.neighborhood.bean.user;

/**
 * @author feng
 * @Date 2018/2/6.
 */

public class FriendInfoBean {


    /**
     * userInfo : 一别两宽，各生欢喜
     * address :
     * headImgUrl : http://39.106.140.31:80/linli/userfiles/images/20180204/20180204203051_581.jpg
     * phone : 18200131081
     * nickname : 浪潮就积极
     * userId : 6d3a59fd2f354447beb7b5c577ad38aa
     */

    private String userInfo;
    private String address;
    private String headImgUrl;
    private String phone;
    private String nickname;
    private String userId;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FriendInfoBean{" +
                "userInfo='" + userInfo + '\'' +
                ", address='" + address + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
