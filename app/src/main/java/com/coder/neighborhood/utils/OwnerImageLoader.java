package com.coder.neighborhood.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * @author feng
 * @Date 2018/1/28.
 */

public class OwnerImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int
            height) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().
                displayImage(Uri.fromFile(new File(path)).toString(), imageView,
                        BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().
                displayImage(Uri.fromFile(new File(path)).toString(), imageView,
                        BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
    }

    @Override
    public void clearMemoryCache() {

    }
}
