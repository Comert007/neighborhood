package com.coder.neighborhood.activity.mall;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.OwnerImageLoader;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.CashierInputFilter;
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
public class AddSecondHandActivity extends BaseActivity<VoidView, HomeModel> implements
        PermissionDispose.OnPermissionListener {

    @BindView(R.id.ll_add_show)
    LinearLayout llAddShow;
    @BindView(R.id.rl_image_show)
    RelativeLayout rlImageShow;
    @BindView(R.id.iv_image)
    ImageView ivImage;

    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.et_goods_price)
    EditText etGoodsPrice;
    @BindView(R.id.et_goods_num)
    EditText etGoodsNum;
    @BindView(R.id.et_goods_type)
    EditText etGoodsType;
    @BindView(R.id.et_goods_detail)
    EditText etGoodsDetail;

    private final int IMAGE_PICKER = 0x13;

    private PermissionDispose dispose;
    private String path;

    public static void startForResult(Activity context,int requestCode) {
        Intent intent = new Intent(context, AddSecondHandActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_second_hand;
    }

    @Override
    protected void init() {
        dispose = PermissionDispose.init(this, this);
        initImagePicker();
        initListener();
    }

    private void initListener(){
        InputFilter[] filters={new CashierInputFilter()};
        etGoodsPrice.setFilters(filters);

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

    @OnClick({R.id.ll_add_show, R.id.if_close,R.id.btn_publish_goods})
    public void onAddSecond(View view) {
        switch (view.getId()) {
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
            case R.id.btn_publish_goods:
                publishGoods();
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
                path = ImageDownloader.Scheme.FILE.wrap(images.get(0).path);
                Debug.d("path:"+path);
                ImageLoader.getInstance().displayImage(path, ivImage,
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


    private void publishGoods(){
        String name = etGoodsName.getText().toString().trim();
        String price= etGoodsPrice.getText().toString().trim();
        String num = etGoodsNum.getText().toString().trim();
        String type = etGoodsType.getText().toString().trim();
        String detail = etGoodsDetail.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            ToastUtils.showToast("请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(price)){
            ToastUtils.showToast("请输入商品价格");
            return;
        }

        if (TextUtils.isEmpty(num)){
            ToastUtils.showToast("请输入商品数量");
            return;
        }

        if (TextUtils.isEmpty(type)){
            ToastUtils.showToast("请输入商品类型");
            return;
        }
        if (TextUtils.isEmpty(detail)){
            ToastUtils.showToast("请输入商品详情");
            return;
        }
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user!=null && !TextUtils.isEmpty(user.getUserId())){
            m.addSecondGoods(user.getUserId(), name, price,num , type, detail, path, bindUntilEvent(ActivityEvent.DESTROY)

                    , new HttpSubscriber<String>(this,true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s);
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