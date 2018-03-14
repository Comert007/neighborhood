package com.coder.neighborhood.bean.mall;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class CommentBean {

    /**
     * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180314/20180314221142_887.jpg
     * commentsContent : 发布商品质量
     * commentsDate : 2018-03-14
     * userName : IOS哟呵哈哈
     * userId : 58e56686b88741f680a116f948e26042
     */

    private String imgUrl;
    private String commentsContent;
    private String commentsDate;
    private String userName;
    private String userId;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCommentsContent() {
        return commentsContent;
    }

    public void setCommentsContent(String commentsContent) {
        this.commentsContent = commentsContent;
    }

    public String getCommentsDate() {
        return commentsDate;
    }

    public void setCommentsDate(String commentsDate) {
        this.commentsDate = commentsDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
