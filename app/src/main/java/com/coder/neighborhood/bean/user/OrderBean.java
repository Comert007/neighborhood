package com.coder.neighborhood.bean.user;

/**
 * @author feng
 * @Date 2018/2/6.
 */

public class OrderBean {

    /**
     * orderPayment : 1000
     * orderId : 79cb8f7bddb44f5892ebdb3a367ec78a
     */

    private String orderPayment;
    private String orderId;
    private int orderStatus;

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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
