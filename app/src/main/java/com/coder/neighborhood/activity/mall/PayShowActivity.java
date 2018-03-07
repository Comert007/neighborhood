package com.coder.neighborhood.activity.mall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.pay.alipay.WWAlipay;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.HashMap;

import butterknife.OnClick;
import ww.com.core.Debug;

/**
 * @Author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class PayShowActivity extends BaseActivity<VoidView, MallModel> {

    private HashMap map;

    public static void start(Context context, HashMap map) {
        Intent intent = new Intent(context, PayShowActivity.class);
        intent.putExtra("map", map);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay_show;
    }

    @Override
    protected void init() {
        map = (HashMap) getIntent().getSerializableExtra("map");
    }

    @OnClick({R.id.btn_alipay,R.id.btn_wechat})
    public void onPay(View view){
        switch (view.getId()){
            case R.id.btn_alipay:
                addOrderAlipay();
                break;
            case R.id.btn_wechat:

                break;
            default:
                ToastUtils.showToast("the pay is error");
                break;
        }
    }

    private void addOrderAlipay() {
        String cardIds = (String) map.get("cardIds");
        String recipientId = (String) map.get("recipientId");
        String payment = (String) map.get("payment");
        String postFee = (String) map.get("postFee");
        String buyerMessage = (String) map.get("buyerMessage");

        if (TextUtils.isEmpty(cardIds)) {
            ToastUtils.showToast("暂无可提交商品");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {

            m.addOrderAlipay(user.getUserId(), cardIds, recipientId,
                    payment, postFee, buyerMessage, bindUntilEvent(ActivityEvent.DESTROY),
                    new HttpSubscriber<String>(this, true) {
                        @Override
                        public void onNext(String s) {

                            JSONObject json = JSON.parseObject(s);
                            String orderString  = json.getString("orderString");
                            WWAlipay.cretateAlipay(PayShowActivity.this, new WWAlipay.AliPayListener() {
                                @Override
                                public void onPaySuccess(String info) {
                                    Debug.d("info:"+info);
                                    ToastUtils.showToast("支付成功",true);
                                    finish();
                                }

                                @Override
                                public void onPayFail(String status, String errInfo) {
                                    Debug.d("errInfo:"+errInfo);
                                    ToastUtils.showToast(errInfo,false);
                                }
                            }).pay(orderString);
                        }
                    });

        }
    }
}
