package com.coder.neighborhood.utils;

import android.content.Context;

import com.coder.neighborhood.widget.LoadingDialog;

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

}
