package com.coder.neighborhood.bean.mall;

import java.util.List;

/**
 * @author feng
 * @date 2018/1/19
 */

public class CategoryGoodsBean {


    /**
     * item : [{"imgUrl":"http://39.106.109.134:80/linli/userfiles/1/images/photo/2018/01
     * /boss001.jpg","itemId":"a7d5c766c8cf48aeaf242e3d527f299d","itemName":"波司登男装 羽绒服男
     * 2017冬季新款轻薄时尚修身中长款纯色连帽毛领保暖情侣加厚羽绒外套男 墨绿02 175/L","itemPrice":"769"}]
     * itemCategoryName : 服饰类
     * itemCategoryId : 56cbe5593a474c7a8fd2de14951a6c59
     */

    private String itemCategoryName;
    private String itemCategoryId;
    private List<CategoryGoodsItemBean> item;

    public String getItemCategoryName() {
        return itemCategoryName;
    }

    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }

    public String getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(String itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public List<CategoryGoodsItemBean> getItem() {
        return item;
    }

    public void setItem(List<CategoryGoodsItemBean> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "CategoryGoodsBean{" +
                "itemCategoryName='" + itemCategoryName + '\'' +
                ", itemCategoryId='" + itemCategoryId + '\'' +
                ", item=" + item +
                '}';
    }
}
