package com.coder.neighborhood.bean.home;

/**
 * @author feng
 * @Date 2018/1/28.
 */

public class ImageQuestionBean  {
    private String url;
    private int imageType;

    public ImageQuestionBean(int imageType) {
        this.imageType = imageType;
    }

    public ImageQuestionBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }
}
