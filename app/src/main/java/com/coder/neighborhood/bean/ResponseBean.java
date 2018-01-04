package com.coder.neighborhood.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.coder.neighborhood.config.Constants;

/**
 *
 * @author feng
 * @date 2016/10/9
 */
public class ResponseBean {

    private String code;
    private String message;
    private String data;


    public static ResponseBean parseObject(JSONObject json) {
        ResponseBean bean = new ResponseBean();

        try {
            bean.setCode(json.getString("code"));
        } catch (Exception e) {
            bean.setCode(Constants.CODE_ERROR);
        }


        try {
            String msg = json.getString("message");
            if (!TextUtils.isEmpty(msg)) {
                bean.setMessage(msg);
            }
        } catch (Exception e) {
            bean.setCode(Constants.CODE_ERROR);
            bean.setMessage("数据解析错误!");
        }

        try {
            String data = json.getString("data");
            if (!TextUtils.isEmpty(data)) {
                bean.setData(data);
            }

        } catch (Exception e) {
            bean.setCode(Constants.CODE_ERROR);
            bean.setMessage("数据解析错误!");
        }

        return bean;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
