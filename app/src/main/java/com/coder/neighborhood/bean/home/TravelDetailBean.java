package com.coder.neighborhood.bean.home;

import java.util.List;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class TravelDetailBean {


    /**
     * imgUrl : [{"imgUrl":"http://39.106.140.31:80/linli/userfiles/1/images/photo/2018/01
     * /swiss0001.jpeg"},{"imgUrl":"http://39.106.140.31:80/linli/userfiles/1/images/photo/2017
     * /12/68226b2f4e4fa1bf6770b2dff42891a1.jpg"},{"imgUrl":"http://39.106.140.31:80500000"}]
     * travelPickBuyCount :
     * travelPrice : 30000
     * traveRoute : [{"traveRoute":"http://39.106.140.31:80/linli/userfiles/1/images/photo/2018
     * /01/swiss0001.jpeg"},{"traveRoute":"http://39.106.140.31:80/linli/userfiles/1/images/photo
     * /2017/12/68226b2f4e4fa1bf6770b2dff42891a1.jpg"},
     * {"traveRoute":"http://39.106.140.31:80500000"}]
     * travelId : e2edda77e10d4591b84e3899592b8112
     * itemDealQuantiry :
     * travelName : 欧洲30日游
     * travelPickingPrice : 28888
     * itemInventoryQuantiry : 99
     * sellType : 1
     * travePickingCount : 10
     */

    private String travelPickBuyCount;
    private String travelPrice;
    private String travelId;
    private String itemDealQuantiry;
    private String travelName;
    private String travelPickingPrice;
    private String itemInventoryQuantiry;
    private String sellType;
    private String travePickingCount;
    private List<ImgUrlBean> imgUrls;
    private List<TraveRouteBean> traveRoute;

    public String getTravelPickBuyCount() {
        return travelPickBuyCount;
    }

    public void setTravelPickBuyCount(String travelPickBuyCount) {
        this.travelPickBuyCount = travelPickBuyCount;
    }

    public String getTravelPrice() {
        return travelPrice;
    }

    public void setTravelPrice(String travelPrice) {
        this.travelPrice = travelPrice;
    }

    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public String getItemDealQuantiry() {
        return itemDealQuantiry;
    }

    public void setItemDealQuantiry(String itemDealQuantiry) {
        this.itemDealQuantiry = itemDealQuantiry;
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getTravelPickingPrice() {
        return travelPickingPrice;
    }

    public void setTravelPickingPrice(String travelPickingPrice) {
        this.travelPickingPrice = travelPickingPrice;
    }

    public String getItemInventoryQuantiry() {
        return itemInventoryQuantiry;
    }

    public void setItemInventoryQuantiry(String itemInventoryQuantiry) {
        this.itemInventoryQuantiry = itemInventoryQuantiry;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getTravePickingCount() {
        return travePickingCount;
    }

    public void setTravePickingCount(String travePickingCount) {
        this.travePickingCount = travePickingCount;
    }

    public List<ImgUrlBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlBean> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public List<TraveRouteBean> getTraveRoute() {
        return traveRoute;
    }

    public void setTraveRoute(List<TraveRouteBean> traveRoute) {
        this.traveRoute = traveRoute;
    }

    public static class ImgUrlBean {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/1/images/photo/2018/01/swiss0001.jpeg
         */

        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

    public static class TraveRouteBean {
        /**
         * traveRoute : http://39.106.140.31:80/linli/userfiles/1/images/photo/2018/01/swiss0001.jpeg
         */

        private String traveRoute;

        public String getTraveRoute() {
            return traveRoute;
        }

        public void setTraveRoute(String traveRoute) {
            this.traveRoute = traveRoute;
        }
    }
}
