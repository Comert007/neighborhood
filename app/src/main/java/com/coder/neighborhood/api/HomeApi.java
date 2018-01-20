package com.coder.neighborhood.api;

import com.coder.neighborhood.bean.ResponseBean;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * @author feng
 * @Date 2018/1/5.
 */

public class HomeApi extends BaseApi {

    public static final Observable<ResponseBean> banner(String bannerType) {
        AjaxParams params = getBaseParams();
        params.addParameters("bannerType", bannerType);
        return onPost(getActionUrl("app/getBanner"), params);
    }

    public static final Observable<ResponseBean> travels(String pageNo,String pageSize){
        AjaxParams params = getBaseParams();
        params.addParameters("pageNo", pageNo);
        params.addParameters("pageSize", pageSize);
        return onPost(getActionUrl("app/getTravelList"), params);
    }

    public static final Observable<ResponseBean> travelDetail(String travelId){
        AjaxParams params = getBaseParams();
        params.addParameters("travelId", travelId);
        return onPost(getActionUrl("app/getTravelInfo"), params);
    }
}
