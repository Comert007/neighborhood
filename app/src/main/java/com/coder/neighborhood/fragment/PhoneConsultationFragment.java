package com.coder.neighborhood.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.DialogUtils;

import butterknife.BindView;

/**
 * @Author feng
 * @Date 2018/1/16
 */

public class PhoneConsultationFragment extends BaseFragment<VoidView, VoidModel> {

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private String phone = "15735655474";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_phone_consultation;
    }

    @Override
    protected void init() {
        tvPhone.setText(Html.fromHtml("联系电话：<font color='#e72f35'>" + phone + "</font>"));
        tvPhone.setOnClickListener(v -> {
            DialogUtils.showDialog(getContext(), "提示", "是否进行电话咨询？",
                    true, "咨询", (dialog, which) -> {
                        call(phone);
                        dialog.dismiss();
                    },
                    true, "取消", (dialog, which) -> dialog.dismiss()).show();
        });
    }

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
