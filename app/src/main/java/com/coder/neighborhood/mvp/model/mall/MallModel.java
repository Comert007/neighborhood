package com.coder.neighborhood.mvp.model.mall;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.HomeApi;
import com.coder.neighborhood.api.MallApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.home.GoodsBean;
import com.coder.neighborhood.bean.mall.CartBean;
import com.coder.neighborhood.bean.mall.CategoryGoodsBean;
import com.coder.neighborhood.bean.mall.CommentBean;
import com.coder.neighborhood.bean.mall.GoodsDetailBean;
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
     * @param hotFlag 热门标记（0正常1热门）
     * @param pageNo 当前页码
     *
     */
    public void goods(String mallType,
                      String hotFlag,
                      String pageNo,
                      HttpSubscriber<List<GoodsBean>> httpSubscriber){

        MallApi.goods(mallType, hotFlag, pageNo)
                .map(new Func1<ResponseBean, List<GoodsBean>>() {
                    @Override
                    public List<GoodsBean> call(ResponseBean responseBean) {
                        List<GoodsBean> beans = JSON.parseArray(responseBean.getData(),GoodsBean.class);
                        return beans;
                    }
                }).compose(RxHelper.<List<GoodsBean>>cutMain())
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
                .map(new Func1<ResponseBean, GoodsDetailBean>() {
                    @Override
                    public GoodsDetailBean call(ResponseBean responseBean) {
                        return JSON.parseObject(responseBean.getData(),GoodsDetailBean.class);
                    }
                }).compose(RxHelper.<GoodsDetailBean>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);

    }


    public void comments(String itemId,String pageNo,String pageSize,
                         LifecycleTransformer transformer,
                         HttpSubscriber<List<CommentBean>> httpSubscriber){
        MallApi.comments(itemId, pageNo, pageSize)
                .map(new Func1<ResponseBean, List<CommentBean>>() {
                    @Override
                    public List<CommentBean> call(ResponseBean responseBean) {
                        List<CommentBean> commentBeans = JSON.parseArray(responseBean.getData(), CommentBean.class);
                        return commentBeans;
                    }
                }).compose(RxHelper.<List<CommentBean>>cutMain())
                .compose(transformer)
                .subscribe(httpSubscriber);
    }

    public void addCart(String userId,
                        String itemId,
                        String quantity,
                        String itemType,
                        LifecycleTransformer transformer,
                        HttpSubscriber<String> httpSubscriber){


        MallApi.addCart(userId, itemId, quantity, itemType)
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
                          String selectFlag,
                          String pageNo,
                          String pageSize,
                          LifecycleTransformer transformer,
                          HttpSubscriber<List<CartBean>> httpSubscriber){


        MallApi.cartGoods(userId, selectFlag, pageNo, pageSize)
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
                          HttpSubscriber<List<CartBean>> httpSubscriber){


        MallApi.goodsOrders(userId, orderStatus, pageNo, pageSize)
                .map(new Func1<ResponseBean, List<CartBean>>() {
                    @Override
                    public List<CartBean> call(ResponseBean responseBean) {
                        return JSON.parseArray(responseBean.getData(), CartBean.class);
                    }
                })
                .compose(RxHelper.<List<CartBean>>cutMain())
                .compose(transformer).subscribe(httpSubscriber);
    }


}
