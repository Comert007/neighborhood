package com.coder.neighborhood.utils;

import android.content.Context;
import android.os.Handler;

import com.coder.neighborhood.widget.LoadingDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Created by feng on 2017/12/21.
 */

public class DialogUtils {

    private DialogUtils() {
        throw new RuntimeException();
    }

    public static final LoadingDialog obtainLoadingDialog(Context context){
        LoadingDialog dialog = new LoadingDialog(context);
        return dialog;
    }


    public static final void showErrorTip(Context context,String info){
        showTip(context,info,QMUITipDialog.Builder.ICON_TYPE_FAIL);
    }

    public static final void showSuccessTip(Context context,String info){
        showTip(context,info,QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
    }

    public static final void showTip(Context context,String info,int type){
        QMUITipDialog.Builder builder = new QMUITipDialog.Builder(context);
        builder.setIconType(type);
        builder.setTipWord(info);
        final QMUITipDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },2000);
    }

}
