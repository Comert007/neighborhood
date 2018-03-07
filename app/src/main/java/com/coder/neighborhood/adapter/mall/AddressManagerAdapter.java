package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.AddressBean;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/3/7.
 */

public class AddressManagerAdapter extends RvAdapter<AddressBean> {

    public AddressManagerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_address_manager;
    }

    @Override
    protected RvViewHolder<AddressBean> getViewHolder(int viewType, View view) {
        return new AddressManagerViewHolder(view);
    }

    class AddressManagerViewHolder extends RvViewHolder<AddressBean> {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public AddressManagerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, AddressBean bean) {
            tvName.setText(bean.getRecipientName());
            tvPhone.setText(bean.getPhone());

            String flag = bean.getDefaultFlag();
            String address = bean.getProvince() + "，" + bean.getCity() + "，" + bean.getArea() +
                    "," + bean.getDetails();
            if (TextUtils.equals("1", flag)) {
                tvAddress.setText(Html.fromHtml("<font color='#ff7f21'>[默认地址]</font>"+address));
            }else {
                tvAddress.setText(address);
            }
        }
    }
}
