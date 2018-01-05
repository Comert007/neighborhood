package com.coder.neighborhood.mvp.model;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author feng
 * @Date 2017/12/21.
 */
public class BaseModel implements IModel {

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
    }
}
