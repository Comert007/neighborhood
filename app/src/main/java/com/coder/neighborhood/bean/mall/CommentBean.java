package com.coder.neighborhood.bean.mall;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class CommentBean {
    private String content;
    private String imgUrl;
    private long created;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
