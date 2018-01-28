package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.home.ImageQuestionAdapter;
import com.coder.neighborhood.bean.home.ImageQuestionBean;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ww.com.core.widget.CustomRecyclerView;

/**
 *
 * @author feng
 * @date 2018/1/8
 */

@SuppressLint("Registered")
public class PublishPicQuestionActivity extends BaseActivity<VoidView,VoidModel> {

    @BindView(R.id.crv)
    CustomRecyclerView crv;

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
