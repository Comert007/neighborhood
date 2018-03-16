package com.coder.neighborhood.activity.mall;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.CommitOrderAdapter;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.AddressBean;
import com.coder.neighborhood.bean.mall.CartFlagBean;
import com.coder.neighborhood.bean.mall.GoodsInfoBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.VoidView;
import com.coder.neighborhood.utils.ArithmeticUtils;
import com.coder.neighborhood.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;

/**
 * @author feng
 * @Date 2018/3/7.
 */

@SuppressLint("Registered")
public class CommitPickOrderActivity extends BaseActivity<VoidView, MallModel> {

    @BindView(R.id.crv)
    CustomRecyclerView crv;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.btn_address_manager)
    Button btnAddressManager;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    private TextView tvPostFee;
    private EditText etMark;

    private CommitOrderAdapter adapter;
    private String recipientId;
    private int page = 1;
    private double price = 0;
    private GoodsInfoBean goodsInfoBean;

    public static void start(Context context, GoodsInfoBean goodsInfoBean) {
        Intent intent = new Intent(context, CommitPickOrderActivity.class);
        intent.putExtra("goodsInfo",goodsInfoBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commit_order;
    }

    @Override
    protected void init() {
        initView();
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initView() {
        crv.setLayoutManager(new LinearLayoutManager(this));
        crv.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommitOrderAdapter(this);

        crv.setAdapter(adapter);

        View view = LayoutInflater.from(this).inflate(R.layout.view_commit_order_info, null);
        tvPostFee = view.findViewById(R.id.tv_post_fee);
        etMark = view.findViewById(R.id.et_mark);
        ScreenUtil.scale(view);
        crv.addFooterView(view);

        initData();
    }

    private void initData(){
        goodsInfoBean = (GoodsInfoBean) getIntent().getSerializableExtra("goodsInfo");
        CartFlagBean bean = new CartFlagBean();
        if (goodsInfoBean.getImgUrls().size()>0){
            bean.setImgUrl(goodsInfoBean.getImgUrls().get(0).getImgUrl());
        }
        bean.setItemName(goodsInfoBean.getItemName());
        bean.setBuyCount("1");
        bean.setPostCost(goodsInfoBean.getPostCost());
        bean.setItemPrice(goodsInfoBean.getItemPrice());
        List<CartFlagBean> cartFlagBeans = new ArrayList<>();
        cartFlagBeans.add(bean);
        adapter.addList(cartFlagBeans);
        price = 0;

        if (cartFlagBeans.size() > 0) {
            for (int i = 0; i < cartFlagBeans.size(); i++) {
                double perPrice = ArithmeticUtils.mul(cartFlagBeans.get(i)
                        .getItemPrice(), cartFlagBeans.get(i).getBuyCount())
                        .doubleValue();
                price = ArithmeticUtils.add(perPrice,price);
            }
        }
        tvPostFee.setText(bean.getPostCost());
        tvPrice.setText("合计：￥"+price);

    }


    @OnClick({R.id.btn_address_manager, R.id.rl_address, R.id.btn_result})
    public void onCommitOrder(View view) {
        switch (view.getId()) {
            case R.id.btn_address_manager:
            case R.id.rl_address:
                AddressManagerActivity.start(this);
                break;
            case R.id.btn_result:
                if (TextUtils.isEmpty(recipientId)){
                    ToastUtils.showToast("请选择联系人");
                    return;
                }

                if (price == 0){
                    ToastUtils.showToast("价格错误");
                    return;
                }

                HashMap map = new HashMap();
                map.put("status","2");
                map.put("itemId",goodsInfoBean.getItemId());
                map.put("itemQuantity","1");
                map.put("recipientId", recipientId);
                map.put("payment",price+"");
                map.put("postFee",TextUtils.isEmpty(tvPostFee.getText().toString())?"0":tvPostFee.getText().toString());
                map.put("buyerMessage",etMark.getText().toString());
                PayShowActivity.start(this, map);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            AddressBean bean = (AddressBean) data.getSerializableExtra("address");
            showAddress(bean);
            recipientId = bean.getRecipientId();
            if (rlAddress.getVisibility() == View.GONE) {
                btnAddressManager.setVisibility(View.GONE);
                rlAddress.setVisibility(View.VISIBLE);
            }
        }
    }

    private void loadGoods() {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            m.cartList(user.getUserId(), "1", page + "", Constants.PAGE_SIZE + "",
                    bindUntilEvent(ActivityEvent.DESTROY), new HttpSubscriber<List<CartFlagBean>>
                            (this, false) {
                        @Override
                        public void onNext(List<CartFlagBean> cartFlagBeans) {
                            adapter.addList(cartFlagBeans);
                            price = 0;

                            if (cartFlagBeans.size() > 0) {
                                for (int i = 0; i < cartFlagBeans.size(); i++) {
                                    double perPrice = ArithmeticUtils.mul(cartFlagBeans.get(i)
                                            .getItemPrice(), cartFlagBeans.get(i).getBuyCount())
                                            .doubleValue();
                                    price = ArithmeticUtils.add(perPrice,price);
                                }
                            }

                            tvPrice.setText("合计：￥"+price);
                        }
                    });
        }

    }

    private void showAddress(AddressBean bean) {
        tvName.setText("收货人：" + bean.getRecipientName());
        tvPhone.setText(bean.getPhone());
        tvAddress.setText(bean.getProvince() + "，" + bean.getCity() + "，" + bean.getArea() + "，" +
                bean.getDetails());
    }
}
