package com.coder.neighborhood.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.widget.LoadingDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import butterknife.ButterKnife;
import ww.com.core.ScreenUtil;

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

    public static final QMUIDialog showDialog(Context context, String title, String message,
                                        boolean positive, String positiveMessage, DialogInterface.OnClickListener listener1
            , boolean opposite, String oppositeMessage, DialogInterface.OnClickListener listener2){

        QMUIDialog dialog = new QMUIDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show,null);
        ScreenUtil.scale(view);

        dialog.setContentView(view);

        TextView dialogTitle = ButterKnife.findById(view,R.id.dialog_title);
        TextView dialogMessage= ButterKnife.findById(view,R.id.dialog_message);

        Button btnCancel = ButterKnife.findById(view,R.id.btn_cancel);
        Button btnOk = ButterKnife.findById(view,R.id.btn_ok);

        dialogTitle.setText(title);
        dialogMessage.setText(message);
        btnCancel.setText(oppositeMessage);
        btnOk.setText(positiveMessage);

        dialogTitle.setVisibility(TextUtils.isEmpty(title)?View.GONE:View.VISIBLE);
        dialogMessage.setVisibility(TextUtils.isEmpty(message)?View.GONE:View.VISIBLE);

        btnCancel.setVisibility(opposite?View.VISIBLE:View.GONE);
        btnOk.setVisibility(positive?View.VISIBLE:View.GONE);

        btnOk.setOnClickListener(v -> {
            if (listener1!=null){
                listener1.onClick(dialog,0);
            }
        });

        btnCancel.setOnClickListener(v -> {
            if (listener2!=null){
                listener2.onClick(dialog,1);
            }
        });

        return dialog;
    }

}
