package com.coder.neighborhood.bean.circle;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class CircleBean {

    /**
     * imgUrl :
     * circleLike :
     * headImg : http://39.106.140.31:80/linli/userfiles/images/20180124/20180124200343_237.jpg
     * nickName : IOS
     * circleInfo : 游客圈动态添加测试
     * circleId : 0b0d79d04e4e4a10921d34fd3d38d1c8
     * circleDate : 1517218078000
     */

    private String circleLike;
    private String headImg;
    private String userlevel;
    private String nickName;
    private String circleInfo;
    private String circleId;
    private String circleAuthor;
    private String circleDate;
    private String userId;
    private List<CommentsBean> comments;
    private List<ImgUrlsBean> imgUrls;


    public String getCircleLike() {
        return circleLike;
    }

    public void setCircleLike(String circleLike) {
        this.circleLike = circleLike;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(String circleInfo) {
        this.circleInfo = circleInfo;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleId(String circleId) {
        this.circleId = circleId;
    }

    public String getCircleDate() {
        return circleDate;
    }

    public void setCircleDate(String circleDate) {
        this.circleDate = circleDate;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public String getCircleAuthor() {
        return circleAuthor;
    }

    public void setCircleAuthor(String circleAuthor) {
        this.circleAuthor = circleAuthor;
    }

    public List<ImgUrlsBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsBean> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public static class CommentsBean {
        /**
         * commentsContent : 真不知道GOGOGOGOGOGo
         * nickname : IOS
         */

        private String commentsContent;
        private String nickname;
        private String commentsDate;
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCommentsContent() {
            return commentsContent;
        }

        public void setCommentsContent(String commentsContent) {
            this.commentsContent = commentsContent;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCommentsDate() {
            return commentsDate;
        }

        public void setCommentsDate(String commentsDate) {
            this.commentsDate = commentsDate;
        }

    }


    public static class ImgUrlsBean {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180206/20180206171549_551.png
         */

        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public String getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
