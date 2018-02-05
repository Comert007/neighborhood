package com.coder.neighborhood.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.coder.neighborhood.config.AppConfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信回调页面
 * @author feng
 */

@SuppressLint("Registered")
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, getAppId());
        api.handleIntent(getIntent(), this);
    }

    protected String getAppId(){
        return AppConfig.WECHAT_APP_ID;
    }

    protected  void onSuccess(int code){
        Bundle bundle = new Bundle();
        bundle.putString("action", "wxpay");
        if (BaseResp.ErrCode.ERR_OK == code) {
            bundle.putBoolean("status", true);
        } else {
            bundle.putBoolean("status", false);
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            onSuccess(resp.errCode);
        }
    }
}