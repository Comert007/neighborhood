package com.coder.neighborhood.bean.home;

/**
 * Created by feng on 2018/1/9.
 */

public class GoodsBean {

    /**
     * imgUrl : http://39.106.109.134:80/linli/userfiles/1/images/photo/2017/12
     * /5a22acfaNf7222715.jpg
     * itemName : 一加手机5T （A5010） 6GB＋64GB 星辰黑 全网通
     * itemCategoryName : 日用品
     * itemPrice : 2999
     * itmeId : a7d5c766c8cf48aeaf242e3d527f299d
     * itemPickingBuyCount : 0
     * sellType : 0
     * itemPickingPrice : 2899
     */

    private String imgUrl;
    private String itemName;
    private String itemCategoryName;
    private String itemPrice;
    private String itmeId;
    private String itemPickingBuyCount;
    private String sellType;
    private String itemPickingPrice;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItmeId() {
        return itmeId;
    }

    public void setItmeId(String itmeId) {
        this.itmeId = itmeId;
    }

    public String getItemPickingBuyCount() {
        return itemPickingBuyCount;
    }

    public void setItemPickingBuyCount(String itemPickingBuyCount) {
        this.itemPickingBuyCount = itemPickingBuyCount;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getItemPickingPrice() {
        return itemPickingPrice;
    }

    public void setItemPickingPrice(String itemPickingPrice) {
        this.itemPickingPrice = itemPickingPrice;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemCategoryName='" + itemCategoryName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itmeId='" + itmeId + '\'' +
                ", itemPickingBuyCount='" + itemPickingBuyCount + '\'' +
                ", sellType='" + sellType + '\'' +
                ", itemPickingPrice='" + itemPickingPrice + '\'' +
                '}';
    }
}
