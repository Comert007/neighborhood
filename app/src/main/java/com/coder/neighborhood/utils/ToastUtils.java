package com.coder.neighborhood.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;

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


    public static final void showToast(CharSequence text,boolean isOk){
        //自定义Toast控件
        Context context = BaseApplication.getInstance().getApplicationContext();
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastView = inflate.inflate(R.layout.layout_toast, null);
        TextView textView = toastView.findViewById(R.id.tv_toast_clear);
        ImageView iv = toastView.findViewById(R.id.toast_iv);
        iv.setImageResource(isOk?R.mipmap.toast_ok:R.mipmap.toast_error);
        textView.setText(text);
        Toast result = new Toast(context);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setView(toastView);
        result.show();
    }

}
