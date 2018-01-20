package com.coder.neighborhood.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @Author feng
 * @Date 2018/1/20
 */

public class IconFontTextView extends AppCompatTextView {

    public IconFontTextView(Context context) {
        super(context);
        if(isInEditMode()) { return; }
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "iconfont.ttf"));
    }

    public IconFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode()) { return; }
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "iconfont.ttf"));
    }

    public IconFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(isInEditMode()) { return; }
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "iconfont.ttf"));
    }


}
