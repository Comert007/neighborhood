package com.coder.neighborhood.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author feng
 * @date 2018/1/8
 */

public class UrgeOrderActivity extends BaseActivity<VoidView, MallModel> {

    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_content)
    EditText etContent;

    private String orderId;


    public static void start(Activity context, String orderId) {
        Intent intent = new Intent(context, UrgeOrderActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivityForResult(intent, 0x26);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_urge_order;
    }

    @Override
    protected void init() {

        initListener();
        initData();
    }

    private void initData() {
        orderId = getIntent().getStringExtra("orderId");
    }


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initListener() {

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(s.toString().length() + "/200");
            }
        });
    }


    @OnClick({R.id.btn_publish})
    public void onPublish(View view) {
        switch (view.getId()) {
            case R.id.btn_publish:
                commentGoods();
                break;
        }
    }

    private void commentGoods() {
        String content = etContent.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("请输入催单内容");
            return;
        }

        if (content.length() < 5) {
            ToastUtils.showToast("催单内容不得少于5字");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            m.urgeOrder(user.getUserId(), orderId, content, bindUntilEvent(ActivityEvent.DESTROY)

                    , new HttpSubscriber<String>(this, true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
                            Intent intent = getIntent();
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    });
        }


    }

}
