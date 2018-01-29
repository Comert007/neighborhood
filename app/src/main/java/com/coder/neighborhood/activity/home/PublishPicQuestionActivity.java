package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.ImageQuestionAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.home.ImageQuestionBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

@SuppressLint("Registered")
public class PublishPicQuestionActivity extends BaseActivity<VoidView,HomeModel> {

    @BindView(R.id.crv)
    CustomRecyclerView crv;
    @BindView(R.id.et_question)
    EditText etQuestion;

    private ImageQuestionAdapter adapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, PublishPicQuestionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_pic_question;
    }

    @Override
    protected void init() {
        adapter = new ImageQuestionAdapter(this);
        crv.setLayoutManager(new GridLayoutManager(this,3));
        List<ImageQuestionBean> images = new ArrayList<>();
        images.add(new ImageQuestionBean(ImageQuestionAdapter.IMAGE_ADD));
        adapter.addList(images);
        crv.setAdapter(adapter);
    }


    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void publishQuestion(){
        String content = etQuestion.getText().toString().trim();
        if (content.length()<5){
            ToastUtils.showToast("不得少于5个字");
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

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null){
            String userId = user.getUserId();
            String phone = user.getPhone();
            String path ="";
            if (paths !=null && paths.size()>0){
                 path = paths.get(0);
            }
            m.addLostThing(userId, "1", phone, content, path, bindUntilEvent(ActivityEvent.DESTROY)
                    , new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
                        }
                    });
        }

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

    @OnClick({R.id.btn_publish})
    public void onPublish(View view){
        switch (view.getId()){
            case R.id.btn_publish:
                publishQuestion();
                break;
            default:
                break;
        }
    }

}
