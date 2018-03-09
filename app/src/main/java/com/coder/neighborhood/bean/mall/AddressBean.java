package com.coder.neighborhood.bean.mall;

import java.io.Serializable;

/**
 * @author feng
 * @Date 2018/3/7.
 */

public class AddressBean implements Serializable{

    /**
     * area : 金牛区
     * defaultFlag : 1
     * province : 四川
     * phone : 18200131081
     * city : 成都
     * recipientId : 49bf8451286c40b8b998ec924b7985f2
     * recipientName : 李朋峰
     * details : 蜀汉路蜀西环街69号
     */

    private String area;
    private String defaultFlag;
    private String province;
    private String phone;
    private String city;
    private String recipientId;
    private String recipientName;
    private String details;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
