package com.coder.neighborhood.bean.home;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class TravelDetailBean {


    /**
     * imgUrl : http://39.106.109.134:80/linli/userfiles/1/images/photo/2018/01/chendu0001.jpeg,
     * /linli/userfiles/1/images/photo/2017/12/a008e73915e2eeb3ebf1ddec10d32756.jpg,
     * travelPrice : 888
     * travelId : c1529c41f8774da9b6a3328fe9aa5ee4
     * travelName : 成都31日游
     * travelRouteMap : 武侯祠－天台天－青城山－宽窄巷子－西岭雪山
     * travelPickingPrice : 666
     */

    private String imgUrl;
    private String travelPrice;
    private String travelId;
    private String travelName;
    private String travelRouteMap;
    private String travelPickingPrice;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getTravelRouteMap() {
        return travelRouteMap;
    }

    public void setTravelRouteMap(String travelRouteMap) {
        this.travelRouteMap = travelRouteMap;
    }

    public String getTravelPickingPrice() {
        return travelPickingPrice;
    }

    public void setTravelPickingPrice(String travelPickingPrice) {
        this.travelPickingPrice = travelPickingPrice;
    }
}
