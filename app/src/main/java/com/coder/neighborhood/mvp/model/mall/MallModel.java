package com.coder.neighborhood.mvp.model.mall;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.HomeApi;
import com.coder.neighborhood.api.MallApi;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.home.GoodsBean;
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


}
