package com.coder.neighborhood.fragment;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.model.IModel;
import com.coder.neighborhood.mvp.presenter.PresenterFragment;
import com.coder.neighborhood.mvp.vu.IView;
import com.coder.neighborhood.utils.DialogUtils;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by feng on 2017/12/23.
 */

public abstract class BaseFragment<V extends IView,M extends IModel> extends PresenterFragment<V,M> {

    private LoadingDialog loadingDialog;
    @Nullable
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    public void showDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = DialogUtils.obtainLoadingDialog(getContext());
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();

        if (!TextUtils.isEmpty(message)) {
            loadingDialog.setMessage(message);
        } else {
            loadingDialog.setMessage(getString(R.string.dialog_loding));
        }
    }

    public void dismissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    @Optional
    @OnClick({R.id.btn_title_left, R.id.btn_title_right})
    public void onTitleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_title_left:
                onTitleLeft();
                break;
            case R.id.btn_title_right:
                onTitleRight();
                break;
        }
    }

    @Optional
    public void onTitleLeft() {

    }

    @Optional
    public void onTitleRight() {

    }

    @Optional
    public void setTitleText(String titleText){
        if (tvTitle!=null){
            tvTitle.setText(titleText);
        }
    }


}
