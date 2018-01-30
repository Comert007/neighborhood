package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.MainActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.ResponseBean;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.user.UserModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author feng
 * @date 2017/12/23
 */

public class LoginActivity extends BaseActivity<VoidView,UserModel> {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_enter)
    Button btnEnter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btn_enter,R.id.btn_register,R.id.tv_reset_password})
    public void onLoginClick(View v){
        switch (v.getId()){
            case R.id.btn_enter:
                loginup();
                break;
            case R.id.btn_register:
                RegisterActivity.start(this);
                break;

            case R.id.tv_reset_password:
                ModifyPasswordActivity.start(this);
                break;
            default:
                break;
        }
    }

    /**
     * login up to the app.
     */
    private void loginup(){
        String username = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)){
            ToastUtils.showToast("请输入用户名");
            return;
        }

        if (username.length()!=11){
            ToastUtils.showToast("请确认用户名是否正确");
            return;
        }

        if (TextUtils.isEmpty(password)){
            ToastUtils.showToast("请输入密码");
        }

        m.loginUp(username, password, bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<ResponseBean>(this,true) {

            @Override
            public void onNext(ResponseBean responseBean) {
                ToastUtils.showToast(responseBean.getMessage());
                UserBean user = JSON.parseObject(responseBean.getData(),UserBean.class);
                BaseApplication.getInstance().saveUserInfo(user);
                MainActivity.start(LoginActivity.this);
                finish();
            }
        });

    }
}
