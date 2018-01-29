package com.coder.neighborhood.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import com.coder.neighborhood.R;

public class BaseDialog extends Dialog {


    public IntCallback callback;

    public BaseDialog(Context context) {
        super(context, R.style.CustomDialog);
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public BaseDialog(Context context, View view) {
        super(context, R.style.CustomDialog);
        this.setContentView(view);
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public BaseDialog(Context context, View view, IntCallback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public BaseDialog(Context context, IntCallback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        if (this.getWindow() != null) {
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * @param gravity 上中下  三个展现位置
     */
    public void setGravity(int gravity) {
        this.getWindow().setGravity(gravity);
    }

    //按照百分比来  0为不设置
    public void setSize(int w, int h) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = w; //设置宽度
        this.getWindow().setAttributes(lp);
    }
}