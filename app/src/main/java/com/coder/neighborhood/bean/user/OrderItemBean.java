package com.coder.neighborhood.bean.user;

/**
 * @Author feng
 * @Date 2018/3/17
 */

public class OrderItemBean {
    /**
     * imgUrl : http://39.106.140.31:80/linli/userfiles/1/images/photo/2018/01/boss001.jpg
     * itemName : 波司登男装 羽绒服男 2017冬季新款轻薄时尚修身中长款纯色连帽毛领保暖情侣加厚羽绒外套男 墨绿02 175/L
     * buyCount : 2
     * ItemPrice : 769
     * itemGroupUnit :
     * ItemId : a7d5c766c8cf48aeaf242e3d527f29991
     */

    private String imgUrl;
    private String itemName;
    private String buyCount;
    private String ItemPrice;
    private String itemGroupUnit;
    private String ItemId;

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

    public String getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(String buyCount) {
        this.buyCount = buyCount;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String ItemPrice) {
        this.ItemPrice = ItemPrice;
    }

    public String getItemGroupUnit() {
        return itemGroupUnit;
    }

    public void setItemGroupUnit(String itemGroupUnit) {
        this.itemGroupUnit = itemGroupUnit;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String ItemId) {
        this.ItemId = ItemId;
    }
}
