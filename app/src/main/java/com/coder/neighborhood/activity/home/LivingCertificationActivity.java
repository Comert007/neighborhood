package com.coder.neighborhood.activity.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.OwnerImageLoader;
import com.coder.neighborhood.utils.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.utils.PermissionDispose;

/**
 * @author feng
 * @date 2018/1/8
 */

@SuppressLint("Registered")
public class LivingCertificationActivity extends BaseActivity<VoidView, HomeModel> implements
        PermissionDispose.OnPermissionListener {

    @BindView(R.id.rl_positive_image_show)
    RelativeLayout rlPositiveImageShow;
    @BindView(R.id.rl_opposite_image_show)
    RelativeLayout rlOppositeImageShow;
    @BindView(R.id.iv_positive_image)
    ImageView ivPositiveImage;
    @BindView(R.id.iv_opposite_image)
    ImageView ivOppositeImage;
    @BindView(R.id.ll_positive_add_show)
    LinearLayout llPositiveAddShow;
    @BindView(R.id.ll_opposite_add_show)
    LinearLayout llOppositeAddShow;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id_card)
    EditText etIdCard;

    private final int IMAGE_PICKER = 0x13;

    private PermissionDispose dispose;
    private String positivePath;
    private String oppositePath;
    private int pathType = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, LivingCertificationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_living_certification;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @Override
    protected void init() {
        dispose = PermissionDispose.init(this, this);

        initImagePicker();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onSuccess(int requestCode, Map<String, Integer> successPermissions) {
        startCameraOrPhoto();
    }

    @Override
    public void onFinal(int requestCode, Map<String, Integer> failPermissions) {

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

    @OnClick({R.id.ll_positive_add_show, R.id.ll_opposite_add_show, R.id.if_positive_close, R.id
            .if_opposite_close,R.id.btn_request_certification})
    public void onCertification(View view) {
        switch (view.getId()){
            case R.id.ll_positive_add_show:
                pathType =1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dispose.requestPermission(0x12, Manifest.permission.CAMERA, Manifest
                            .permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    startCameraOrPhoto();
                }
                break;
            case R.id.ll_opposite_add_show:
                pathType =2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dispose.requestPermission(0x12, Manifest.permission.CAMERA, Manifest
                            .permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    startCameraOrPhoto();
                }
                break;
            case R.id.if_positive_close:
                pathType =1;
                ivPositiveImage.setImageResource(R.mipmap.pic_default);
                showImageType(pathType,true);
                break;
            case R.id.if_opposite_close:
                pathType =2;
                ivOppositeImage.setImageResource(R.mipmap.pic_default);
                showImageType(pathType,true);
                break;
            case R.id.btn_request_certification:
                onCertification();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == IMAGE_PICKER) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra
                    (ImagePicker.EXTRA_RESULT_ITEMS);

            if (images != null && images.size() > 0) {
                showImageType(pathType,false);
                positivePath =(pathType==1)? ImageDownloader.Scheme.FILE.wrap(images.get(0).path):"";
                oppositePath = (pathType ==2)?ImageDownloader.Scheme.FILE.wrap(images.get(0).path):"";

                ImageLoader.getInstance().displayImage((pathType==1)? positivePath:oppositePath,
                        (pathType==1)?ivPositiveImage:ivOppositeImage,
                        BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            } else {
                ToastUtils.showToast("未选择图片");
                showImageType(pathType,true);
            }
        }
    }

    private void showImageType(int pathType,boolean isAdd) {
        if (pathType ==1){
            llPositiveAddShow.setVisibility(isAdd ? View.VISIBLE : View.GONE);
            rlPositiveImageShow.setVisibility(!isAdd ? View.VISIBLE : View.GONE);
        }else if (pathType ==2){
            llOppositeAddShow.setVisibility(isAdd ? View.VISIBLE : View.GONE);
            rlOppositeImageShow.setVisibility(!isAdd ? View.VISIBLE : View.GONE);
        }
    }


    private void startCameraOrPhoto() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, IMAGE_PICKER);
    }

    /**
     * 判断是否符合身份证号码的规范
     * @param idCard 身份证号
     * @return
     */
    public static boolean isIDCard(String idCard) {
        if (idCard != null) {
            String idCardRegex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
            return idCard.matches(idCardRegex);
        }
        return false;
    }

    private void onCertification(){
        String name = etName.getText().toString().trim();
        String idCard = etIdCard.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            ToastUtils.showToast("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(idCard)){
            ToastUtils.showToast("请输入身份证号");
            return;
        }

        if (!isIDCard(idCard)){
            ToastUtils.showToast("请输入正确的身份证号");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){

        }


    }

}
