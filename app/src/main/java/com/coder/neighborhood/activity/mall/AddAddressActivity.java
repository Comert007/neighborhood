package com.coder.neighborhood.activity.mall;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.AddressBean;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ToastUtils;
import com.coder.neighborhood.widget.IconFontTextView;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author feng
 * @Date 2018/3/9.
 */

public class AddAddressActivity extends BaseActivity<VoidView, MallModel> {

    @BindView(R.id.et_recipient_name)
    EditText etRecipientName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_details)
    EditText etDetails;
    @BindView(R.id.icon_default)
    IconFontTextView iconDefault;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private boolean isDefault;

    private String province;
    private String city;
    private String area;


    CityPickerView mPicker = new CityPickerView();

    public static void start(Activity context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
        context.startActivityForResult(intent, 0x22);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void init() {
        setTitleRightText("保存");
        mPicker.init(this);
    }

    @Override
    public void onTitleLeft() {
        finish();
    }


    @Override
    public void onTitleRight() {
        addRecipient();
    }


    @OnClick({R.id.icon_default, R.id.ll_address})
    public void onAddressClick(View view) {
        switch (view.getId()) {
            case R.id.icon_default:
                setIconDefault();
                break;
            case R.id.ll_address:
                showAddressSelector();
                break;
            default:
                break;
        }
    }


    private void setIconDefault() {
        isDefault = !isDefault;
        iconDefault.setText(isDefault ? "\ue677" : "\ue678");
        iconDefault.setTextColor(isDefault ? this.getResources().getColor(R.color.color_1d65a2) :
                this.getResources().getColor(R.color.color_aaaaaa));
    }

    private void addRecipient() {
        String name = etRecipientName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String details = etDetails.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("请输入收件人姓名");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(area)) {
            ToastUtils.showToast("请输入省市区");
            return;
        }

        if (TextUtils.isEmpty(details)) {
            ToastUtils.showToast("请输入详细地址");
            return;
        }

        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            AddressBean addressBean = new AddressBean();
            addressBean.setUserId(user.getUserId());
            addressBean.setRecipientName(name);
            addressBean.setPhone(phone);
            addressBean.setProvince(province);
            addressBean.setCity(city);
            addressBean.setArea(area);
            addressBean.setDetails(details);
            addressBean.setDefaultFlag(isDefault ? "1" : "0");
            m.addRecipient(user.getUserId(), name, phone, province, city, area, details,
                    isDefault ? "1" : "0"

                    , bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<String>(this,
                            true) {
                        @Override
                        public void onNext(String s) {
                            ToastUtils.showToast(s, true);
                            Intent intent = new Intent();
                            intent.putExtra("address",addressBean);
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }
                    });
        }

    }

    private void showAddressSelector() {
        hideSoftKeyBord();
        //添加默认的配置，不需要自己定义
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                String address = "";

                //省份
                if (province != null) {
                    AddAddressActivity.this.province = province.getName();
                    address = address + province.getName();
                }

                //城市
                if (city != null) {
                    AddAddressActivity.this.city = city.getName();
                    address = address + city.getName();
                }

                //地区
                if (district != null) {
                    AddAddressActivity.this.area = district.getName();
                    address = address + district.getName();
                }

                tvAddress.setText(address);

            }

            @Override
            public void onCancel() {
                ToastUtils.showToast("已取消");
            }
        });

        //显示
        mPicker.showCityPicker();
    }
}
