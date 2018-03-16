package com.coder.neighborhood.bean.user;

/**
 * @author feng
 * @Date 2018/3/16.
 */

public class OrderComposeBean {

    private String orderPayment;
    private String orderId;
    private String orderStatus;
    private String imgUrl;
    private String itemName;
    private String buyCount;
    private String ItemPrice;
    private String itemGroupUnit;
    private String ItemId;

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

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

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
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

    public void setItemId(String itemId) {
        ItemId = itemId;
    }
}
