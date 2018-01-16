package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author feng
 * @date 2017/12/23
 */

public class RegisterActivity extends BaseActivity<VoidView, UserModel> {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_request_register)
    Button btnRegister;


    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.btn_request_register)
    public void signup() {
        String mobile = etMobile.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            ToastUtils.showToast("请输入至少6位密码");
            return;
        }
        m.signup(mobile, password, bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber
                <String>(this, true) {
            @Override
            public void onNext(String s) {
                ToastUtils.showToast("注册成功");
                finish();
            }
        });
    }
}
