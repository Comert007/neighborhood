package com.coder.neighborhood.bean.circle;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/2/4
 */

public class EventDetailBean {


    /**
     * eventsId : b1f7998cc3e343588d6c4c04e3b434a7
     * imgUrl : [{"imgUrl":"http://39.106.140.31:80/linli/userfiles/images/20180204
     * /20180204214956_133.jpg"}]
     * eventsInfo : 发布一个大事件进行测试数据是否正常显示
     */

    private String eventsId;
    private String eventsInfo;
    private List<ImgUrlBean> imgUrls;

    public String getEventsId() {
        return eventsId;
    }

    public void setEventsId(String eventsId) {
        this.eventsId = eventsId;
    }

    public String getEventsInfo() {
        return eventsInfo;
    }

    public void setEventsInfo(String eventsInfo) {
        this.eventsInfo = eventsInfo;
    }

    public List<ImgUrlBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlBean> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public static class ImgUrlBean {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180204/20180204214956_133.jpg
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
