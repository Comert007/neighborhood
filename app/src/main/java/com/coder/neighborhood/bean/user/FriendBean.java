package com.coder.neighborhood.bean.user;

import java.io.Serializable;

/**
 * @author feng
 * @Date 2018/1/29.
 */

public class FriendBean implements Serializable{

    /**
     * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180124/20180124195959_29.jpg
     * nickName :
     * userId : bae52af65dd74cb7b48278fad6d311c5
     */

    private String imgUrl;
    private String nickName;
    private String userId;
    private String level;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }
}
