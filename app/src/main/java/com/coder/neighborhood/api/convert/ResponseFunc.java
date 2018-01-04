package com.coder.neighborhood.api.convert;

import com.alibaba.fastjson.JSONObject;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.config.Constants;

import rx.functions.Func1;
import ww.com.http.exception.NetworkException;
import ww.com.http.exception.RequestErr;

/**
 * Created by 10142 on 2016/10/10.
 */
public class ResponseFunc implements Func1<String, ResponseBean> {
    @Override
    public ResponseBean call(String string) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        ResponseBean bean = ResponseBean.parseObject(jsonObject);
        if (bean != null) {
            if (Constants.STATUS_OK == bean.getStatus())
                return bean;
            else {
                throw new RequestErr(bean.getMessage(),bean.getCode());
            }
        } else {
            throw new NetworkException(NetworkException.TYPE_DEFAULT);
        }

    }
}