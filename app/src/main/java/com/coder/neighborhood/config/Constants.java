package com.coder.neighborhood.config;

/**
 * Created by feng on 2017/12/21.
 */

public class Constants {
    public static final int STATUS_OK = 1;
    public static final int STATUS_ERROR = 0;
    public static final String CODE_ERROR = "000001";
    public static final String CODE_RELOGIN = "000002";
    public static final String CODE_OK = "000000";
    public static final int CONNECT_TIME_OUT = 15;
    public static final int READ_TIME_OUT = 60 * 1000;
    public static final int PAGE_SIZE =10;

    //商场类型（1商城2二手商城）
    public static final String TYPE_MALL = "1";
    public static final String TYPE_SECOND_MALL = "2";
    //热门标记（0正常1热门）
    public static final String TYPE_HOT_FLAG = "1";
    public static final String TYPE_NORMAL_FLAG = "0";
}
