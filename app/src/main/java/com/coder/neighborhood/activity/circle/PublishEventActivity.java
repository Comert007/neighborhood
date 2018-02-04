package com.coder.neighborhood.activity.circle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
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
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.CircleModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.OwnerImageLoader;
import com.coder.neighborhood.utils.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.utils.PermissionDispose;

/**
 * @author feng
 * @Date 2018/1/16
 */

@SuppressLint("Registered")
public class PublishEventActivity extends BaseActivity<VoidView, CircleModel> implements
        PermissionDispose.OnPermissionListener {

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



    private final int IMAGE_PICKER = 0x13;

    private PermissionDispose dispose;
    private String path;

    public static void startForResult(Activity context,int requestCode) {
        Intent intent = new Intent(context, PublishEventActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_event;
    }

    @Override
    protected void init() {
        setTitleText("发布事件");
        dispose = PermissionDispose.init(this, this);
        initImagePicker();
        initListener();
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
    public void onSuccess(int requestCode, Map<String, Integer> successPermissions) {
        startCameraOrPhoto();
    }

    @Override
    public void onFinal(int requestCode, Map<String, Integer> failPermissions) {

    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @OnClick({R.id.ll_add_show, R.id.if_close,R.id.btn_publish})
    public void onPublish(View view){
        switch (view.getId()){
            case R.id.ll_add_show:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dispose.requestPermission(0x12, Manifest.permission.CAMERA, Manifest
                            .permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    startCameraOrPhoto();
                }
                break;
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

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new OwnerImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(1);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(1000);
        imagePicker.setOutPutY(1000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dispose.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == IMAGE_PICKER) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra
                    (ImagePicker.EXTRA_RESULT_ITEMS);

            if (images != null && images.size() > 0) {
                showImageType(false);
                path = images.get(0).path;
                Debug.d("path:"+path);
                ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(images.get(0).path), ivImage,
                        BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            } else {
                ToastUtils.showToast("未选择图片");
                showImageType(true);
            }
        }
    }


    private void showImageType(boolean isAdd) {
        llAddShow.setVisibility(isAdd ? View.VISIBLE : View.GONE);
        rlImageShow.setVisibility(!isAdd ? View.VISIBLE : View.GONE);

    }


    private void startCameraOrPhoto() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }


    private void addHelpQuestion() {
        String content = etContent.getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("请输入问题");
            return;
        }

        if (content.length() < 5) {
            ToastUtils.showToast("问题不得少于5字描述");
            return;
        }

        //
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null &&!TextUtils.isEmpty(user.getUserId())){
            m.addEvent(user.getUserId(), content, path , bindUntilEvent(ActivityEvent.DESTROY),
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

}
