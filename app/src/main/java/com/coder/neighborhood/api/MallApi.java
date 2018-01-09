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
                                                       String hotFlag,
                                                       String pageNo){
        AjaxParams params = getBaseParams();
        params.addParameters("mallType",mallType);
        params.addParameters("hotFlag",hotFlag);
        params.addParameters("pageNo",pageNo);
        return onPost(getActionUrl("app/getItemList"),params);
    }
}
