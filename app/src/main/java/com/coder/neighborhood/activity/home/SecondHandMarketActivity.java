package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.mall.AddSecondHandActivity;
import com.coder.neighborhood.activity.mall.GoodsSearchActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.home.SecondHandMarketAdapter;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.mall.CategoryGoodsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.home.SecondMarketView;
import com.coder.neighborhood.utils.ToastUtils;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @Date 2018/1/5.
 */

@SuppressLint("Registered")
public class SecondHandMarketActivity extends BaseActivity<SecondMarketView, MallModel> {

    @BindView(R.id.et_search)
    EditText etSearch;

    private SecondHandMarketAdapter adapter;
    private CustomSwipeRefreshLayout csr;
    private CustomRecyclerView crv;
    private List<String> urls;

    private int page = 1;
    private int requestCode = 0x13;

    public static void start(Context context) {
        Intent intent = new Intent(context, SecondHandMarketActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_second_hand_market;
    }

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        initData();
    }

    private void initData() {
        adapter = new SecondHandMarketAdapter(this);
        crv = v.getCrv();
        crv.setAdapter(adapter);
        initListener();
        onBanner();
        categoryGoods(false);
    }

    private void initListener() {
        csr = v.getCsr();
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH)
            {

                String searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)){
                    ToastUtils.showToast("输入搜索内容");
                    return false;
                }
                GoodsSearchActivity.start(SecondHandMarketActivity.this,"2",searchContent);
            }
            return false;
        });
    }


    @OnClick({R.id.btn_publish_goods,R.id.iv_search})
    public void onPublish(View view) {
        switch (view.getId()) {
            case R.id.btn_publish_goods:
                AddSecondHandActivity.startForResult(this,requestCode);
                break;
            case R.id.iv_search:
                String searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)){
                    ToastUtils.showToast("输入搜索内容");
                    return;
                }
                GoodsSearchActivity.start(SecondHandMarketActivity.this,"2",searchContent);
                break;
            default:
                break;
        }
    }

    private void onBanner() {
        m.onBanner("2", new HttpSubscriber<List<BannerBean>>(SecondHandMarketActivity.this, true) {
            @Override
            public void onNext(List<BannerBean> bannerBeans) {

                startBanner(bannerBeans);
            }
        });
    }

    private void startBanner(List<BannerBean> bannerBeans) {
        urls.clear();
        for (BannerBean banner : bannerBeans) {
            urls.add(banner.getImgUrl());
        }

        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (urls.size() > 0) {

                }
            }
        });
        v.addBanner();

    }

    private void categoryGoods(boolean showDialog) {
        m.categoryGoods(Constants.TYPE_SECOND_MALL, page + "", new
                HttpSubscriber<List<CategoryGoodsBean>>(this, showDialog) {

                    @Override
                    public void onNext(List<CategoryGoodsBean> categoryGoodsBeans) {
                        adapter.addList(categoryGoodsBeans);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode){
            categoryGoods(false);
        }
    }
}
