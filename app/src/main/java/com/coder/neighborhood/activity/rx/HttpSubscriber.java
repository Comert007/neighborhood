package com.coder.neighborhood.activity.rx;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.TextUtils;

import com.coder.neighborhood.api.convert.ResponseErr;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.utils.DialogUtils;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.LoadingDialog;

import rx.Subscriber;

/**
 * Created by feng on 2017/12/23.
 */

public abstract class HttpSubscriber<T> extends Subscriber<T> {

    private LoadingDialog dialog;
    private Context context;

    private boolean showDialog;


    public HttpSubscriber(Context context, boolean showDialog) {
        this.context = context;
        this.showDialog = showDialog;
        initDialog();
    }

    private void initDialog() {
        dialog = DialogUtils.obtainLoadingDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("loading...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (!isUnsubscribed()) {
                    unsubscribe();
                    onCancelRequest();
                }
            }
        });
    }

    private void showDialog() {
        dialog.show();
    }

    private void dismissDialog() {
        dialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            showDialog();
        }
    }

    @Override
    public void onCompleted() {
        onEnd();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        try {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                // 不是主线程不坐处理.
                return;
            }
            onEnd();
            onFail(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public void onFail(Throwable e) {
        if (e instanceof ResponseErr) {
            if (TextUtils.equals(Constants.CODE_RELOGIN,((ResponseErr) e).getErrCode())){
              //
            }else {
                ToastUtils.showToast(e.getMessage());
            }
        }else {
            ToastUtils.showToast(e.getMessage());
        }
    }


    public void onEnd() {
        dismissDialog();
    }

    /**
     * 取消请求
     */
    protected void onCancelRequest() {
        dismissDialog();
    }

    /**
     * 刷新请求
     */
    protected void onRefreshRequest() {
        dismissDialog();
    }
}
