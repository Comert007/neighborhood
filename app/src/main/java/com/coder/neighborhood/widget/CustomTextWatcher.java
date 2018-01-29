package com.coder.neighborhood.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public abstract class CustomTextWatcher implements TextWatcher {
    public String beforeS;
    public String afterS;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeS = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        afterS = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (beforeS.equals(afterS)) return;
        onChanged();
    }

    public abstract void onChanged();
}
