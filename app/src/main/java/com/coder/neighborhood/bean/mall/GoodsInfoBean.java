package com.coder.neighborhood.bean.mall;

import java.io.Serializable;
import java.util.List;

/**
 * @author feng
 * @Date 2018/2/6.
 */

public class GoodsInfoBean implements Serializable{


    /**
     * imgUrl : http://39.106.140.31:80/linli/userfiles/1/images/photo/2018/01/rongxin001.jpg
     * itemId : 5a5945e9f01c46fab95643b2fcdeea05
     * itemName : 农心 方便面 辛拉面 香菇牛肉口味 120g*5袋 五连包
     * itemCategoryName : 食品类
     * itemDealQuantiry : 999
     * itemPrice : 22.9
     * itemInventoryQuantiry : 900
     * itemPickingCount : 10
     * itemPickingBuyCount : 0
     * sellType : 1
     * itemPickingPrice : 19.9
     */

    private String itemId;
    private String itemName;
    private String itemCategoryName;
    private String itemDealQuantiry;
    private String itemPrice;
    private String itemInventoryQuantiry;
    private String itemPickingCount;
    private String itemPickingBuyCount;
    private String sellType;
    private String itemPickingPrice;
    private String postCost;
    private List<ImgUrlsBean> imgUrls;


    public String getPostCost() {
        return postCost;
    }

    public void setPostCost(String postCost) {
        this.postCost = postCost;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getItemDealQuantiry() {
        return itemDealQuantiry;
    }

    public void setItemDealQuantiry(String itemDealQuantiry) {
        this.itemDealQuantiry = itemDealQuantiry;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemInventoryQuantiry() {
        return itemInventoryQuantiry;
    }

    public void setItemInventoryQuantiry(String itemInventoryQuantiry) {
        this.itemInventoryQuantiry = itemInventoryQuantiry;
    }

    public String getItemPickingCount() {
        return itemPickingCount;
    }

    public void setItemPickingCount(String itemPickingCount) {
        this.itemPickingCount = itemPickingCount;
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

    public List<ImgUrlsBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsBean> imgUrls) {
        this.imgUrls = imgUrls;
    }


    public static class ImgUrlsBean implements Serializable {
        /**
         * imgUrl : http://39.106.140.31:80/linli/userfiles/1/images/photo/2018/01/boss001.jpg
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
