package com.coder.neighborhood.activity.home;

import android.content.Context;
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
import com.coder.neighborhood.mvp.aop.CheckUser;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

public class FeedBackQuestionActivity extends BaseActivity<VoidView,HomeModel> {

    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_content)
    EditText etContent;


    public static void start(Context context) {
        Intent intent = new Intent(context, FeedBackQuestionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feedback_question;
    }

    @Override
    protected void init() {

        initListener();
        initData();
    }

    private void initData(){
    }


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initListener(){

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



    @CheckUser
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

        if (TextUtils.isEmpty(content)){
            ToastUtils.showToast("请输入问题");
            return;
        }

        if (content.length()<5){
            ToastUtils.showToast("问题不得少于5字描述");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            m.feedBackQuestion(user.getUserId(), content, bindUntilEvent(ActivityEvent.DESTROY)

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
