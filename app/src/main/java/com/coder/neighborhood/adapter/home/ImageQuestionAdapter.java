package com.coder.neighborhood.adapter.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.home.ImageQuestionBean;
import com.coder.neighborhood.utils.OwnerImageLoader;
import com.coder.neighborhood.widget.IconFontTextView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import ww.com.core.Debug;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;
import ww.com.core.utils.PermissionDispose;

/**
 * @author feng
 * @Date 2018/1/28.
 */

public class ImageQuestionAdapter extends RvAdapter<ImageQuestionBean> implements
        PermissionDispose.OnPermissionListener{

    public static final int IMAGE_ADD = 1;
    public static final int IMAGE_SHOW = 2;
    private final int IMAGE_PICKER = 0x13;
    private Activity context;
    private PermissionDispose dispose;


    public ImageQuestionAdapter(Context context) {
        super(context);
        this.context = (Activity) context;
        dispose = PermissionDispose.init(context, this);
        initImagePicker();
    }

    @Override
    public int getItemViewType(int position) {
       return getItem(position).getImageType();
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        if (viewType == IMAGE_ADD) {
            return R.layout.item_question_add;
        } else {
            return R.layout.item_question_image;
        }

    }

    @Override
    protected RvViewHolder<ImageQuestionBean> getViewHolder(int viewType, View view) {
        if (viewType == IMAGE_ADD) {
            return new ImageAddViewHolder(view);
        } else {
            return new ImageQuestionViewHolder(view);
        }
    }

    @Override
    public void onSuccess(int requestCode, Map<String, Integer> successPermissions) {
        startCameraOrPhoto();
    }

    @Override
    public void onFinal(int requestCode, Map<String, Integer> failPermissions) {

    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        dispose.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == IMAGE_PICKER) {
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra
                    (ImagePicker.EXTRA_RESULT_ITEMS);

            int leftSize = 6 - getList().size();
            Debug.d("leftSize:" + leftSize);

            for (int i = 0; i < (leftSize < images.size() ? leftSize : images.size()); i++) {
                ImageQuestionBean image = new ImageQuestionBean(IMAGE_SHOW);
                image.setUrl(images.get(i).path);
                getList().add(getList().size() - 1, image);
            }
            if (getList().size() == 6) {
                getList().remove(5);
            }
            notifyDataSetChanged();
        }

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new OwnerImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setSelectLimit(5);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(1000);
        imagePicker.setOutPutY(1000);
    }

    class ImageQuestionViewHolder extends RvViewHolder<ImageQuestionBean> {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.if_close)
        IconFontTextView close;

        public ImageQuestionViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(final int position, ImageQuestionBean bean) {
            if (!TextUtils.isEmpty(bean.getUrl())) {
                ImageLoader.getInstance()
                        .displayImage(ImageDownloader.Scheme.FILE.wrap(bean.getUrl()) ,ivImage, BaseApplication
                        .getDisplayImageOptions(R.mipmap.pic_default));
            }

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int total = getList().size();
                    if (total == 5 && (getList().get(total-1)).getImageType()!=IMAGE_ADD) {
                        getList().add(new ImageQuestionBean(IMAGE_ADD));
                    }
                    getList().remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    class ImageAddViewHolder extends RvViewHolder<ImageQuestionBean> {
        @BindView(R.id.ll_add)
        LinearLayout llAdd;

        public ImageAddViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, ImageQuestionBean bean) {
            llAdd.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dispose.requestPermission(0x12, Manifest.permission.CAMERA, Manifest
                            .permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    startCameraOrPhoto();
                }
            });
        }
    }

    private void startCameraOrPhoto() {
        Intent intent = new Intent(getContext(), ImageGridActivity.class);
        context.startActivityForResult(intent, IMAGE_PICKER);
    }
}
