package com.coder.neighborhood.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.mall.GoodsSearchActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.adapter.mall.MallAdapter;
import com.coder.neighborhood.bean.home.BannerBean;
import com.coder.neighborhood.bean.mall.CategoryGoodsBean;
import com.coder.neighborhood.config.Constants;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.MallView;
import com.coder.neighborhood.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.CustomRecyclerView;
import ww.com.core.widget.CustomSwipeRefreshLayout;

/**
 * @author feng
 * @date 2017/12/23
 */

public class MallFragment extends BaseFragment<MallView, MallModel> {

    @BindView(R.id.et_search)
    EditText etSearch;

    private MallAdapter adapter;
    private CustomRecyclerView crv;
    private CustomSwipeRefreshLayout csr;
    private List<String> urls;

    private int page = 1;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        initListener();
        initData();
    }

    private void initData() {
        adapter = new MallAdapter(getContext());
        crv = v.getCrv();
        csr = v.getCsr();
        crv.setAdapter(adapter);
        onBanner();
        categoryGoods(false);
    }


    private void initListener() {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)) {
                    ToastUtils.showToast("输入搜索内容");
                    return false;
                }
                GoodsSearchActivity.start(getContext(), "1", searchContent);
            }
            return false;
        });
    }

    private void onBanner() {
        m.onBanner("1", new HttpSubscriber<List<BannerBean>>(getContext(), true) {
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
        v.getBanner().setOnBannerListener(position -> {
            if (urls.size() > 0) {

            }
        });
        v.addBanner();

    }


    @OnClick({R.id.iv_search})
    public void onSearch(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                String searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)) {
                    ToastUtils.showToast("输入搜索内容");
                    return;
                }
                GoodsSearchActivity.start(getContext(), "1", searchContent);
                break;
            default:
                break;
        }
    }


    private void categoryGoods(boolean showDialog) {
        m.categoryGoods(Constants.TYPE_MALL, page + "", new
                HttpSubscriber<List<CategoryGoodsBean>>(getContext(), showDialog) {

                    @Override
                    public void onNext(List<CategoryGoodsBean> categoryGoodsBeans) {
                        adapter.addList(categoryGoodsBeans);
                    }
                });
    }
}
