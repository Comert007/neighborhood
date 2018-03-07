package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.mall.CommitOrderAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.ScreenUtil;
import ww.com.core.widget.CustomRecyclerView;

/**
 * @author feng
 * @Date 2018/3/7.
 */

public class CommitOrderActivity extends BaseActivity<VoidView,VoidModel> {

    @BindView(R.id.crv)
    CustomRecyclerView crv;

    private CommitOrderAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CommitOrderActivity.class);
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

    private void initView(){
        crv.setLayoutManager(new LinearLayoutManager(this));
        crv.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommitOrderAdapter(this);

        crv.setAdapter(adapter);

        View view = LayoutInflater.from(this).inflate(R.layout.view_commit_order_info,null);
        ScreenUtil.scale(view);
        crv.addFooterView(view);

    }

    @OnClick({R.id.btn_address_manager})
    public void onCommitOrder(View view){
        switch (view.getId()){
            case R.id.btn_address_manager:
                AddressManagerActivity.start(this);
                break;
            default:
                break;
        }
    }
}
