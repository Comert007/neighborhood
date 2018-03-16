package com.coder.neighborhood.activity.circle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.ImageQuestionAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.home.ImageQuestionBean;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;

/**
 * @author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class PublishMakingFriendsActivity extends BaseActivity<VoidView, CircleModel>  {

    @BindView(R.id.ll_add_show)
    LinearLayout llAddShow;
    @BindView(R.id.rl_image_show)
    RelativeLayout rlImageShow;
    @BindView(R.id.iv_image)
    ImageView ivImage;

    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_content)
    EditText etContent;


    @BindView(R.id.crv)
    CustomRecyclerView crv;

    private ImageQuestionAdapter adapter;

    public static void startForResult(Activity context,int requestCode) {
        Intent intent = new Intent(context, PublishMakingFriendsActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_making_friends;
    }

    @Override
    protected void init() {
        setTitleText("发布交友");
        initViews();
        initListener();
    }


    private void initViews(){
        adapter = new ImageQuestionAdapter(this);
        crv.setLayoutManager(new GridLayoutManager(this,3));
        List<ImageQuestionBean> images = new ArrayList<>();
        images.add(new ImageQuestionBean(ImageQuestionAdapter.IMAGE_ADD));
        adapter.addList(images);
        crv.setAdapter(adapter);
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


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @OnClick({ R.id.if_close,R.id.btn_publish})
    public void onPublish(View view){
        switch (view.getId()){
            case R.id.if_close:
                ivImage.setImageResource(R.mipmap.pic_default);
                showImageType(true);
                break;
            case R.id.btn_publish:
                addHelpQuestion();
                break;
            default:
                break;
        }
    }



    private void showImageType(boolean isAdd) {
        llAddShow.setVisibility(isAdd ? View.VISIBLE : View.GONE);
        rlImageShow.setVisibility(!isAdd ? View.VISIBLE : View.GONE);
    }


    private void addHelpQuestion() {
        String content = etContent.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("请输入交友信息");
            return;
        }

        if (content.length() < 5) {
            ToastUtils.showToast("交友信息不得少于5字描述");
            return;
        }
        List<ImageQuestionBean> imageQuestionBeans = adapter.getList();
        List<String> paths = new ArrayList<>();
        if (imageQuestionBeans!=null && imageQuestionBeans.size()>0){
            for (ImageQuestionBean imageQuestionBean : imageQuestionBeans) {
                if (imageQuestionBean.getImageType() == ImageQuestionAdapter.IMAGE_SHOW){
                    paths.add(imageQuestionBean.getUrl());
                }
            }
        }
        //
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null &&!TextUtils.isEmpty(user.getUserId())){
            m.addMakingFriends(user.getUserId(), content, paths , bindUntilEvent(ActivityEvent.DESTROY),
                    new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s,true);
                            finishActivity();
                        }
                    });
        }

    }


    private void finishActivity(){
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        adapter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.onActivityResult(requestCode, requestCode, data);
    }

}
