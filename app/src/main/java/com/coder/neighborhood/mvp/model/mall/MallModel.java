package com.coder.neighborhood.mvp.model.mall;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.HomeApi;
import com.coder.neighborhood.api.MallApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.mall.AddressBean;
import com.coder.neighborhood.bean.mall.CartBean;
import com.coder.neighborhood.bean.mall.CartFlagBean;
import com.coder.neighborhood.bean.mall.CategoryGoodsBean;
import com.coder.neighborhood.bean.mall.CommentBean;
import com.coder.neighborhood.bean.mall.GoodsDetailBean;
import com.coder.neighborhood.bean.mall.GoodsInfoBean;
import com.coder.neighborhood.bean.mall.GoodsSearchBean;
import com.coder.neighborhood.bean.mall.GoodsTypeBean;
import com.coder.neighborhood.bean.user.OrderBean;
import com.coder.neighborhood.mvp.model.BaseModel;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.util.List;

import rx.functions.Func1;
import ww.com.http.rx.RxHelper;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class MallModel extends BaseModel{

    public void onBanner(String bannerType,
                         HttpSubscriber<List<BannerBean>> httpSubscriber){
        HomeApi.banner(bannerType)
                .map(new Func1<ResponseBean, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> call(ResponseBean responseBean) {
                        List<BannerBean> strings = JSON.parseArray(responseBean.getData(), BannerBean.class);
                        return strings;
                    }
                }).compose(RxHelper.<List<BannerBean>>cutMain())
                .subscribe(httpSubscriber);
    }

    public void onBanner(String bannerType,
                         LifecycleTransformer transformer,
                         HttpSubscriber<List<String>> httpSubscriber){

        HomeApi.banner(bannerType)
                .map(new Func1<ResponseBean, List<String>>() {
                    @Override
                    public List<String> call(ResponseBean responseBean) {
                        List<String> strings = JSON.parseArray(responseBean.getData(), String.class);
                        return strings;
                    }
                }).compose(RxHelper.<List<String>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    /**
     * 商品列表
     * @param mallType 商场类型（1商城2二手商城）
     * @param pageNo 当前页码
     *
     */
    public void goods(String mallType,
                      String itemCategoryId,
                      String pageNo,
                      String pageSize,
                      LifecycleTransformer transformer,
                      HttpSubscriber<List<GoodsTypeBean>> httpSubscriber){

        MallApi.goods(mallType, itemCategoryId, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),GoodsTypeBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void goodsSearch(String mallType,
                            String itemName,
                            String pageNo,
                            String pageSize,
                            LifecycleTransformer transformer,
                            HttpSubscriber<List<GoodsSearchBean>> httpSubscriber){

        MallApi.goodsSearch(mallType, itemName, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),GoodsSearchBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }


    public void categoryGoods(String mallType,
                              String pageNo,
                              HttpSubscriber<List<CategoryGoodsBean>> httpSubscriber){
        MallApi.categoryGoods(mallType, pageNo)
                .map(new Func1<ResponseBean, List<CategoryGoodsBean>>() {
                    @Override
                    public List<CategoryGoodsBean> call(ResponseBean responseBean) {
                        List<CategoryGoodsBean> beans = JSON.parseArray(responseBean.getData(),CategoryGoodsBean.class);
                        return beans;
                    }
                }).compose(RxHelper.<List<CategoryGoodsBean>>cutMain())
                .subscribe(httpSubscriber);
    }


    /**
     * 获取商品详情banner
     */
    public void goodsDetailBanner(String itemId,
                                     LifecycleTransformer transformer,
                                     HttpSubscriber<List<BannerBean>> httpSubscriber){
        MallApi.goodsDetailBanner(itemId)
                .map(new Func1<ResponseBean, List<BannerBean>>() {
                    @Override
                    public List<BannerBean> call(ResponseBean responseBean) {
                        List<BannerBean> strings = JSON.parseArray(responseBean.getData(), BannerBean.class);
                        return strings;
                    }
                }).compose(RxHelper.<List<BannerBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void  goodsDetail(String itemId,
                             LifecycleTransformer transformer,
                             HttpSubscriber<GoodsDetailBean> httpSubscriber){

        MallApi.goodsDetail(itemId)
                .map(responseBean -> JSON.parseObject(responseBean.getData(),GoodsDetailBean.class)).compose(RxHelper.<GoodsDetailBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void  goodsInfo(String itemId,
                             LifecycleTransformer transformer,
                             HttpSubscriber<GoodsInfoBean> httpSubscriber){

        MallApi.goodsInfo(itemId)
                .map(responseBean -> JSON.parseObject(responseBean.getData(),GoodsInfoBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }


    public void comments(String itemId,String pageNo,String pageSize,
                         LifecycleTransformer transformer,
                         HttpSubscriber<List<CommentBean>> httpSubscriber){
        MallApi.comments(itemId, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(), CommentBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addCart(String userId,
                        String itemId,
                        String quantity,
                        LifecycleTransformer transformer,
                        HttpSubscriber<String> httpSubscriber){


        MallApi.addCart(userId, itemId, quantity)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        responseBean.getData();
                        return responseBean.getMessage();
                    }
                }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


    public void deleteCart(String userId,
                        String cartId,
                        LifecycleTransformer transformer,
                        HttpSubscriber<String> httpSubscriber){


        MallApi.deleteCart(userId, cartId)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        responseBean.getData();
                        return responseBean.getData();
                    }
                }).compose(RxHelper.<String>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }




    public void cartGoods(String userId,
                          String pageNo,
                          String pageSize,
                          LifecycleTransformer transformer,
                          HttpSubscriber<List<CartBean>> httpSubscriber){


        MallApi.cartGoods(userId, pageNo, pageSize)
                .map(new Func1<ResponseBean, List<CartBean>>() {
                    @Override
                    public List<CartBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(), CartBean.class);
                    }
                })
                .compose(RxHelper.<List<CartBean>>cutMain())
                .compose(transformer).subscribe(httpSubscriber);
    }


    public void goodsOrders(String userId,
                          String orderStatus,
                          String pageNo,
                          String pageSize,
                          LifecycleTransformer transformer,
                          HttpSubscriber<List<OrderBean>> httpSubscriber){


        MallApi.goodsOrders(userId, orderStatus, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(), OrderBean.class))
                .compose(RxHelper.cutMain())
                .compose(transformer).subscribe(httpSubscriber);
    }


    public void addOrderAlipay(String userId,
                               String recipientId,
                               String payment,
                               String postFee,
                               String buyerMessage,LifecycleTransformer transformer,
                               HttpSubscriber<String> httpSubscriber){

        MallApi.addOrderAlipay(userId,  recipientId, payment, postFee, buyerMessage)
                .map(new Func1<ResponseBean, String>() {
                    @Override
                    public String call(ResponseBean responseBean) {
                        return responseBean.getData();
                    }
                }).compose(RxHelper.cutMain())
                .compose(transformer).subscribe(httpSubscriber);
    }


    public void getRecipientList(String userId,String pageNo,
                                 String pageSize, LifecycleTransformer transformer,
                                 HttpSubscriber<List<AddressBean>> httpSubscriber){
        MallApi.getRecipientList(userId, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),AddressBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void addRecipient(String userId,
                             String name,
                             String phone,
                             String province,
                             String city,
                             String area,
                             String details,
                             String defaultFlag,
                             LifecycleTransformer transformer,
                             HttpSubscriber<String> httpSubscriber){

        MallApi.addRecipient(userId, name, phone, province, city, area, details, defaultFlag)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }

    public void cartList(String userId,
                         String selectFlag,
                         String pageNo,
                         String pageSize,LifecycleTransformer transformer,
                         HttpSubscriber<List<CartFlagBean>> httpSubscriber){
        MallApi.cartList(userId, selectFlag, pageNo, pageSize)
                .map(responseBean -> JSON.parseArray(responseBean.getData(),CartFlagBean.class)).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void editCart(String userId,
                         String cartId,
                         String selectFlag,LifecycleTransformer transformer,
                         HttpSubscriber<String> httpSubscriber){

        MallApi.editCart(userId, cartId, selectFlag)
                .map(responseBean -> responseBean.getMessage()).compose(RxHelper.cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }


}
