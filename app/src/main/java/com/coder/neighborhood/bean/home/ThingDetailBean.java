package com.coder.neighborhood.bean.home;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class ThingDetailBean {

    /**
     * imgUrl : [{"imgUrl":"http://39.106.140.31:80/linli/userfiles/images/20180124
     * /20180124154124_146.png"}]
     * lostId : 13b63591ad144bd89ce14a0b2378be71
     * lostPhone :
     * questionDate : 1516779684000
     * lostInfo : Hxgdhfuguiihih
     * headerImgUrl : http://39.106.140.31:80/linli/userfiles/images/20180124/20180124200343_237.jpg
     */

    private String lostId;
    private String lostPhone;
    private long questionDate;
    private String lostInfo;
    private String headerImgUrl;

    @Nullable
    private List<ImgUrlBean> imgUrl;

    public String getLostId() {
        return lostId;
    }

    public void setLostId(String lostId) {
        this.lostId = lostId;
    }

    public String getLostPhone() {
        return lostPhone;
    }

    public void setLostPhone(String lostPhone) {
        this.lostPhone = lostPhone;
    }

    public long getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(long questionDate) {
        this.questionDate = questionDate;
    }

    public String getLostInfo() {
        return lostInfo;
    }

    public void setLostInfo(String lostInfo) {
        this.lostInfo = lostInfo;
    }

    public String getHeaderImgUrl() {
        return headerImgUrl;
    }

    public void setHeaderImgUrl(String headerImgUrl) {
        this.headerImgUrl = headerImgUrl;
    }

    public List<ImgUrlBean> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<ImgUrlBean> imgUrl) {
        this.imgUrl = imgUrl;
    }


    public static class ImgUrlBean {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/images/20180124/20180124154124_146.png
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
