package com.coder.neighborhood.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.coder.neighborhood.BaseApplication;

/**
 * Created by feng on 2017/12/21.
 */

public class ToastUtils {

    private static Toast sToast;

    public static final void showToast(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (sToast != null) {
            sToast.cancel();
        }
        sToast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), text,
                Toast.LENGTH_SHORT);

        sToast.show();
    }
}
