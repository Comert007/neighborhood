package com.coder.neighborhood.bean.mall;

/**
 * @Author feng
 * @Date 2018/1/19
 */

public class CategoryGoodsItemBean {

    /**
     * imgUrl : http://39.106.109.134:80/linli/userfiles/1/images/photo/2018/01/boss001.jpg
     * itemId : a7d5c766c8cf48aeaf242e3d527f299d
     * itemName : 波司登男装 羽绒服男 2017冬季新款轻薄时尚修身中长款纯色连帽毛领保暖情侣加厚羽绒外套男 墨绿02 175/L
     * itemPrice : 769
     */

    private String imgUrl;
    private String itemId;
    private String itemName;
    private String itemPrice;

    /**
     * custom params
     */
    private int categoryType;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "CategoryGoodsItemBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
