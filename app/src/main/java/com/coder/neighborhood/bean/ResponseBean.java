package com.coder.neighborhood.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coder.neighborhood.config.Constants;

/**
 *
 * @author feng
 * @date 2016/10/9
 */
public class ResponseBean {

    private int status;
    private int code;
    private String message;
    private String data;
    private String need_relogin;


    public static ResponseBean parseObject(JSONObject json) {
        ResponseBean bean = new ResponseBean();

        try {
            bean.setStatus(json.getInteger("status"));
        } catch (Exception e) {
            bean.setStatus(Constants.STATUS_ERROR);
            bean.setMessage("数据解析错误!");
        }

        try {
            bean.setCode(json.getInteger("code"));
        } catch (Exception e) {
            bean.setCode(Constants.CODE_ERROR);
        }


        try {
            String msg = json.getString("msg");
            if (!TextUtils.isEmpty(msg)) {
                bean.setMessage(msg);
            }
        } catch (Exception e) {
            bean.setStatus(Constants.STATUS_ERROR);
            bean.setMessage("数据解析错误!");
        }

        try {
            String need_relogin = json.getString("need_relogin");
            if (!TextUtils.isEmpty(need_relogin)) {
                bean.need_relogin = need_relogin;
            }
        } catch (Exception e) {

        }

        try {
            String data = json.getString("data");
            if (!TextUtils.isEmpty(data)) {
                bean.setData(data);
            }

        } catch (Exception e) {
            bean.setStatus(Constants.STATUS_ERROR);
            bean.setMessage("数据解析错误!");
        }

        return bean;
    }

    public JSONObject toJsonObject() {
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        return JSONObject.parseObject(data);
    }

    public JSONArray toJsonArray() {
        if (TextUtils.isEmpty(data)) {
            return null;
        }

        return JSON.parseArray(data);
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public boolean isNeedRelogin() {
        return "1".equals(need_relogin);
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", msg='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
