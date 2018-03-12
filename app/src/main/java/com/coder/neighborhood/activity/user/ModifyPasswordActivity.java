package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
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

public class ModifyPasswordActivity extends BaseActivity<VoidView, UserModel> {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_modify_password)
    Button btnModify;
    @BindView(R.id.btn_code)
    Button btnCode;


    public static void start(Context context) {
        Intent intent = new Intent(context, ModifyPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.btn_modify_password,R.id.btn_code})
    public void onModifyClick(View view){
        switch (view.getId()){
            case R.id.btn_modify_password:
                modify();
                break;
            case R.id.btn_code:
                sendMessage();
                break;
            default:
                break;
        }
    }

    public void modify() {
        String mobile = etMobile.getText().toString().trim().replaceAll(" ","");
        String password = etPassword.getText().toString();
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            ToastUtils.showToast("请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            ToastUtils.showToast("请输入至少6位密码");
            return;
        }
        if (TextUtils.isEmpty(code)){
            ToastUtils.showToast("请输入验证码");
            return;
        }

        m.findPassword(mobile, code,password,bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber
                <String>(this, true) {
            @Override
            public void onNext(String s) {
                ToastUtils.showToast("重置密码成功");
                finish();
            }
        });
    }

    /**
     * get the code.
     */
    private void sendMessage(){
        String mobile = etMobile.getText().toString().trim().replaceAll(" ","");
        if (TextUtils.isEmpty(mobile)){
            ToastUtils.showToast("请输入手机号码");
            return;
        }

        if (mobile.length()!=11){
            ToastUtils.showToast("请验证手机号码是否正确");
            return;
        }

        m.sendMsg(mobile, bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<String>(this,true) {
            @Override
            public void onNext(String s) {
                ToastUtils.showToast("发送成功",true);
                countDown();
            }
        });


    }


    private void countDown(){
        CountDownTimer timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnCode.setTextColor(Color.parseColor("#8C8C8C"));
                btnCode.setEnabled(false);
                btnCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_border_gray_shape));
                btnCode.setText((millisUntilFinished/1000)+"s后重发");
            }

            @Override
            public void onFinish() {
                btnCode.setTextColor(Color.parseColor("#29D829"));
                btnCode.setEnabled(true);
                btnCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_border_green_shape));
                btnCode.setText("重发验证码");
            }
        };
        timer.start();
    }
}
