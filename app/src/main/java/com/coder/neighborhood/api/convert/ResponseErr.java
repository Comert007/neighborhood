package com.coder.neighborhood.api.convert;

import ww.com.http.exception.CustomException;

/**
 * Created by feng on 2018/1/4.
 */

public class ResponseErr extends CustomException {

    private String errCode;

    public ResponseErr(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}
