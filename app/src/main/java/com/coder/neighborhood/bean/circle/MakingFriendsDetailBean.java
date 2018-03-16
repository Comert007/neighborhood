package com.coder.neighborhood.bean.circle;

import java.util.List;

/**
 * @author feng
 * @Date 2018/3/16.
 */

public class MakingFriendsDetailBean {


    /**
     * makingFriendsInfo : 发布一个交友信息测试数据
     * imgUrls : [{"imgUrl":"http://39.106.140.31:80/linli/userfiles/images/20180316
     * /20180316112441_514.jpg"}]
     * makingFriendsId : 4bf5598e570d4c01a029292a9de5bfa6
     */

    private String makingFriendsInfo;
    private String makingFriendsId;
    private List<ImgUrlsBean> imgUrls;

    public String getMakingFriendsInfo() {
        return makingFriendsInfo;
    }

    public void setMakingFriendsInfo(String makingFriendsInfo) {
        this.makingFriendsInfo = makingFriendsInfo;
    }

    public String getMakingFriendsId() {
        return makingFriendsId;
    }

    public void setMakingFriendsId(String makingFriendsId) {
        this.makingFriendsId = makingFriendsId;
    }

    public List<ImgUrlsBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsBean> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public static class ImgUrlsBean {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180316/20180316112441_514.jpg
         */

        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
