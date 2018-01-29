package com.coder.neighborhood.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.neighborhood.R;

import ww.com.core.ScreenUtil;

public class EditDialog extends BaseDialog implements View.OnClickListener {

    Context context;
    private TextView sureBtn;
    private TextView txtNums;
    private EditText editText;
    private TextView contView;
    private int maxlines;
    private int emsNums = 1000;
    private EditDialogClickInterface clickInterface;
    private boolean hasNums;


    public void setNums(boolean hasNums, int emsNums) {
        this.emsNums = emsNums;
        this.hasNums = hasNums;
        if (hasNums) {
            txtNums.setText((emsNums - editText.getText().toString().length()) + "");
        } else {
            txtNums.setVisibility(View.GONE);
        }
        editText.addTextChangedListener(watcher);
    }

    public EditDialog(Context context, int maxlines) {
        super(context, R.style.inputDialog);
        this.context = context;
        this.maxlines = maxlines;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_edit_sure);
        editText = (EditText) mView.findViewById(R.id.dialog_edit_edit);
        txtNums = (TextView) mView.findViewById(R.id.txt_nums);
        editText.setMaxLines(maxlines);
        editText.setMinLines(maxlines);
        sureBtn.setOnClickListener(this);
        super.setContentView(mView);
        getWindow().setWindowAnimations(R.style.timepopwindow_anim_style);
    }

    public EditDialog setParms(TextView contView, String s) {
        this.contView = contView;
        if (contView.getText().toString() != null) {
            editText.setText(contView.getText().toString());
            editText.setSelection(editText.getText().length());
        }
        editText.setHint(s);

        return this;
    }

    public EditDialog setParms(TextView contView, String s, int maxLength) {
        this.contView = contView;
        if (contView.getText().toString() != null) {
            editText.setText(contView.getText().toString());
            editText.setSelection(editText.getText().length());
        }
        editText.setHint(s);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return this;
    }

    public EditDialog setParms(TextView contView, String s, int maxLength, boolean isNumber) {
        this.contView = contView;
        if (contView.getText().toString() != null) {
            if (isNumber)
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            editText.setText(contView.getText().toString());
            editText.setSelection(editText.getText().length());
        }
        editText.setHint(s);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return this;
    }


    public void setEdiClickInterface(EditDialogClickInterface ediClickInterface) {
        this.clickInterface = ediClickInterface;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_edit_sure:
                String editStr = editText.getText().toString();
                if (clickInterface != null) {
                    if (!"".equals(editStr)) {
                        clickInterface.doConfirm(editText.getText().toString());
                    }
                } else {
                    contView.setText(editStr);
                }
                this.cancel();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        setGravity(Gravity.BOTTOM);
        setSize(ScreenUtil.getScreenWidth(getContext()), 0);
    }

    private CustomTextWatcher watcher = new CustomTextWatcher() {
        @Override
        public void onChanged() {
            int i = editText.getText().toString().length();
            if (i <= 0) {
                sureBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_grad_btn));
                if (hasNums)
                    txtNums.setText(emsNums + "");
            } else {
                sureBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_red_buttom));
                if (hasNums)
                    txtNums.setText((emsNums - i) + "");
            }
        }
    };

    public interface EditDialogClickInterface {
        void doConfirm(String str);
    }

    @Override
    public void dismiss() {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        super.dismiss();
    }
}