package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class TakeDeliveryActivity extends BaseActivity<VoidView,HomeModel> {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_express_no)
    EditText etExpressNo;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_remark)
    EditText etRemark;

    public static void start(Context context) {
        Intent intent = new Intent(context, TakeDeliveryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_take_delivery;
    }

    @Override
    protected void init() {

    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @OnClick(R.id.btn_publish)
    public void onPublish(){
        delivery();
    }

    private void delivery(){
        String name = etName.getText().toString().trim();
        String expressNo = etExpressNo.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            ToastUtils.showToast("请输入姓名");
            return;
        }

        if (TextUtils.isEmpty(expressNo)){
            ToastUtils.showToast("请输入快递单号");
            return;
        }

        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast("请输入联系方式");
            return;
        }


        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            m.delivery(user.getUserId(), name, expressNo, phone, remark, bindUntilEvent(ActivityEvent.DESTROY)

                    , new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
                            finish();
                        }
                    });
        }


    }
}
