package com.coder.neighborhood.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.R;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

/**
 * @author feng
 * @Date 2017/12/21.
 */

public class LoadingDialog extends Dialog {


    private View loadingView;
    private TextView tvMessage;

    public LoadingDialog(@NonNull Context context) {
        super(context);

        loadingView = LayoutInflater.from(context)
                .inflate(R.layout.dialog_loading, null);
        ScreenUtil.scale(loadingView);
        tvMessage = ButterKnife.findById(loadingView, R.id.tv_message);

        setContentView(loadingView);
    }

    public void setMessage(CharSequence charSequence) {
        tvMessage.setText(charSequence);
    }

}
