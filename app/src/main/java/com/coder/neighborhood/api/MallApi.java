package com.coder.neighborhood.api;

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
     * @param hotFlag 热门标记（0正常1热门）
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


}
