package com.coder.neighborhood.bean.circle;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/2/4
 */

public class DoingsDetailBean {

    /**
     * imgUrl : [{"imgUrl":"http://39.106.140.31:80/linli/userfiles/images/20180204
     * /20180204215711_585.jpg"}]
     * activityId : c2e86303011041c8a37b922a0e4baf2b
     * activityInfo : 想活动问题的时候一点过
     */

    private String activityId;
    private String activityInfo;
    private List<ImgUrlsBean> imgUrls;


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }

    public List<ImgUrlsBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsBean> imgUrls) {
        this.imgUrls = imgUrls;
    }


    public static class ImgUrlsBean {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180209/20180209140750_30.jpg
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
