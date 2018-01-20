package com.coder.neighborhood.bean.mall;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class CartBean {


    /**
     * itemName : 农心 方便面 辛拉面 香菇牛肉口味 120g*5袋 五连包
     * buyCount : 1
     * headImg : http://39.106.109.134:80/linli/userfiles/1/images/photo/2018/01/rongxin001.jpg
     * cartId : ae5d05725ac84ba5a4066aed798bd2f2
     * ItemId : 5a5945e9f01c46fab95643b2fcdeea03
     */

    private String itemName;
    private String buyCount;
    private String headImg;
    private String cartId;
    private String ItemId;

    private boolean isCheck;

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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String ItemId) {
        this.ItemId = ItemId;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
