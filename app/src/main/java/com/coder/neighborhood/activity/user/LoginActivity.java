package com.coder.neighborhood.activity.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.MainActivity;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author feng
 * @date 2017/12/23
 */

public class LoginActivity extends BaseActivity<VoidView,VoidModel> {

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

    @OnClick({R.id.btn_enter})
    public void onLoginClick(View v){
        switch (v.getId()){
            case R.id.btn_enter:
                MainActivity.start(LoginActivity.this);
                finish();
                break;
            default:
                break;
        }
    }
}
