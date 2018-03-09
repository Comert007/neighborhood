package com.coder.neighborhood.api;

import android.text.TextUtils;

import com.coder.neighborhood.bean.ResponseBean;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2018/1/9.
 */

public class MallApi extends BaseApi {

    /**
     * 商品列表
     * @param mallType 商场类型（1商城2二手商城）
     * @param pageNo 当前页码
     * @return
     */
    public static final Observable<ResponseBean> goods(String mallType,
                                                       String itemCategoryId,
                                                       String pageNo,
                                                       String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("mallType",mallType);
        params.addParameters("itemCategoryId",itemCategoryId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getItemList"),params);
    }


    public static final Observable<ResponseBean> goodsSearch(String mallType,
                                                             String itemName,
                                                             String pageNo,
                                                             String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("mallType",mallType);
        params.addParameters("itemName",itemName);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getItemListByName"),params);
    }


    public static final Observable<ResponseBean> categoryGoods(String mallType,
                                                               String pageNo){
        AjaxParams params = getBaseParams();
        params.addParameters("mallType",mallType);
        params.addParameters("pageNo",pageNo);

        return onPost(getActionUrl("app/getItemCategoryList"),params);
    }

    public static final Observable<ResponseBean> goodsDetailBanner(String itemId){
        AjaxParams params = getBaseParams();
        params.addParameters("itemId",itemId);

        return onPost(getActionUrl("app/getItemDetails"),params);
    }

    public static final Observable<ResponseBean> goodsDetail(String itemId){
        AjaxParams params = getBaseParams();
        params.addParameters("itemId",itemId);

        return onPost(getActionUrl("app/getItemDetails"),params);
    }

    public static final Observable<ResponseBean> goodsInfo(String itemId){
        AjaxParams params = getBaseParams();
        params.addParameters("itemId",itemId);

        return onPost(getActionUrl("app/getItemInfo"),params);
    }


    public static final Observable<ResponseBean> comments(String itemId,
                                                          String pageNo,
                                                          String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("itemId",itemId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getItemCommentsList"),params);
    }


    public static final Observable<ResponseBean> addCart(String userId,
                                                         String itemId,
                                                         String quantity){

        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("itemId",itemId);
        params.addParameters("quantity",quantity);

        return onPost(getActionUrl("app/addCart"),params);
    }

    public static final Observable<ResponseBean> deleteCart(String userId,
                                                         String cartId){

        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("cartId",cartId);

        return onPost(getActionUrl("app/deleteCart"),params);
    }
    public static final Observable<ResponseBean> cartGoods(String userId,
                                                           String pageNo,
                                                           String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getCartList"),params);
    }


    public static final Observable<ResponseBean> goodsOrders(String userId,
                                                           String orderStatus,
                                                           String pageNo,
                                                           String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("orderStatus",orderStatus);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);

        return onPost(getActionUrl("app/getOrderList"),params);
    }

    public static final Observable<ResponseBean> addOrderAlipay(String userId,
                                                                String recipientId,
                                                                String payment,
                                                                String postFee,
                                                                String buyerMessage){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("recipientId",recipientId);
        params.addParameters("payment",payment);
        if (!TextUtils.isEmpty(postFee)){
            params.addParameters("postFee",postFee);
        }

        if (!TextUtils.isEmpty(buyerMessage)){
            params.addParameters("buyerMessage",buyerMessage);
        }
        return onPost(getActionUrl("app/addOrder"),params);
    }


    public static final Observable<ResponseBean> getRecipientList(String userId,String pageNo,
                                                                  String pageSize){

        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("cartIds",pageNo);
        params.addParameters("recipientId",pageSize);

        return onPost(getActionUrl("app/getRecipientList"),params);
    }

    public static final Observable<ResponseBean> addRecipient(String userId,
                                                              String name,
                                                              String phone,
                                                              String province,
                                                              String city,
                                                              String area,
                                                              String details,
                                                              String defaultFlag){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("name",name);
        params.addParameters("phone",phone);
        params.addParameters("province",province);
        params.addParameters("city",city);
        params.addParameters("area",area);
        params.addParameters("details",details);
        params.addParameters("defaultFlag",defaultFlag);

        return onPost(getActionUrl("app/addRecipient"),params);
    }

    public static final Observable<ResponseBean> cartList(String userId,
                                                          String selectFlag,
                                                          String pageNo,
                                                          String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("selectFlag",selectFlag);
        params.addParameters("pageNo",pageNo);
        params.addParameters("pageSize",pageSize);
        return onPost(getActionUrl("app/getCartList"),params);
    }

    public static final Observable<ResponseBean> editCart(String userId,
                                                          String cartId,
                                                          String selectFlag){
        AjaxParams params = getBaseParams();
        params.addParameters("userId",userId);
        params.addParameters("cartId",cartId);
        params.addParameters("selectFlag",selectFlag);

        return onPost(getActionUrl("app/editCart"),params);
    }

}
