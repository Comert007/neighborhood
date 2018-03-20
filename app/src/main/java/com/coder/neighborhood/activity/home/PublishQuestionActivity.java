package com.coder.neighborhood.activity.home;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
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
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.CashierInputFilter;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

public class PublishQuestionActivity extends BaseActivity<VoidView,HomeModel> {

    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_bounty)
    EditText etBounty;


    private int type = 1;

    public static void start(Context context,int type) {
        Intent intent = new Intent(context, PublishQuestionActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_question;
    }

    @Override
    protected void init() {

        initListener();
        initData();
    }

    private void initData(){
        type = getIntent().getIntExtra("type",type);
    }


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initListener(){
        InputFilter[] filters={new CashierInputFilter()};
        etBounty.setFilters(filters);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNum.setText(s.toString().length()+"/200");
            }
        });
    }


    @OnClick({R.id.btn_publish})
    public void onPublish(View view){
        switch (view.getId()){
            case R.id.btn_publish:
                addHelpQuestion();
                break;
        }
    }

    private void addHelpQuestion(){
        String content = etContent.getText().toString().trim();
        String bounty = etBounty.getText().toString().trim();

        if (TextUtils.isEmpty(content)){
            ToastUtils.showToast("请输入问题");
            return;
        }

        if (content.length()<5){
            ToastUtils.showToast("问题不得少于5字描述");
            return;
        }

        if (TextUtils.isEmpty(bounty)){
            ToastUtils.showToast("请输入酬金");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            m.addHelpQuestion(user.getUserId(), type + "", content, bounty, bindUntilEvent(ActivityEvent.DESTROY)

                    , new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s,true);
                            finish();
                        }
                    });
        }



    }

}
