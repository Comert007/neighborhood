package com.coder.neighborhood.api;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;
import com.coder.neighborhood.api.convert.ResponseFunc;
import com.coder.neighborhood.bean.ResponseBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Observable;
import ww.com.http.OkHttpRequest;
import ww.com.http.core.AjaxParams;
import ww.com.http.core.RequestMethod;
import ww.com.http.interfaces.DownloadProgressListener;
import ww.com.http.rx.RxHelper;
import ww.com.http.rx.StringFunc;

/**
 * Created by feng on 2017/12/21.
 */

public class BaseApi {

    private static final String BASE_URL = "http://39.106.109.134/linli/";

    protected static final String getActionUrl(String action) {
        return String.format("%s%s", BASE_URL, action);
    }

    private static Map<String, String> getHeader() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    //jsonObject
    protected static Observable<String> onJson(String url, JSONObject json) {
        AjaxParams params = getBaseParams();
        params.addParametersJson(json);

        return json(url, params)
                .compose(RxHelper.<ResponseBody>cutMain())
                .map(new StringFunc());
    }

    //post
    protected static Observable<ResponseBean> onPost(String url, AjaxParams params) {

        Map<String, String> header = getHeader();
        for (String key : header.keySet()) {
            params.addHeaders(key, header.get(key));
        }

        return post(url, params)
                .map(new StringFunc())
                .map(new ResponseFunc());
    }

    //get
    protected static Observable<ResponseBean> onGet(String url, AjaxParams params) {

        Map<String, String> header = getHeader();
        for (String key : header.keySet()) {
            params.addHeaders(key, header.get(key));
        }

        return get(url, params)
                .map(new StringFunc())
                .map(new ResponseFunc());
    }

    /**
     * @param url
     * @param params
     * @return
     */
    private static Observable<ResponseBody> get(String url, AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param params
     * @param progressListener
     * @return
     */
    private static Observable<ResponseBody> downFile(String url, AjaxParams params,
                                                     @NonNull DownloadProgressListener
                                                             progressListener) {
        params = params.setBaseUrl(url)
                .setDownloadProgressListener(progressListener)
                .setRequestMethod(RequestMethod.GET);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    private static Observable<ResponseBody> post(String url,
                                                 AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.POST);
        return OkHttpRequest.newObservable(params);
    }

    /**
     * @param url    请求的地址
     * @param params 请求的参数
     * @return
     */
    private static Observable<ResponseBody> json(String url,
                                                 AjaxParams params) {
        params = params.setBaseUrl(url)
                .setRequestMethod(RequestMethod.JSON);
        return OkHttpRequest.newObservable(params);
    }

    public static final AjaxParams getBaseParams() {
        AjaxParams params = new AjaxParams();
        Map<String, String> header = getHeader();
        for (String key : header.keySet()) {
            params.addHeaders(key, header.get(key));
        }
        return params;
    }

}
