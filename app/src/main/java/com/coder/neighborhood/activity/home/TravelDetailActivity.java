package com.coder.neighborhood.activity.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.BaseApi;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.home.TravelDetailBean;
import com.coder.neighborhood.mvp.model.home.HomeModel;
import com.coder.neighborhood.mvp.vu.mall.GoodsDetailView;
import com.coder.neighborhood.utils.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author feng
 * @Date 2018/1/9
 *
 */

@SuppressLint("Registered")
public class TravelDetailActivity extends BaseActivity<GoodsDetailView,HomeModel> {

    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_pick_count)
    TextView tvPickCount;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_goods_deal_num)
    TextView tvGoodsDealNum;
    @BindView(R.id.ssw)
    WebView ssw;



    private List<String> urls;
    private  String travelId;


    public static void start(Context context,String travelId) {
        Intent intent = new Intent(context, TravelDetailActivity.class);
        intent.putExtra("travelId",travelId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_travel_detail;
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        travelId = getIntent().getStringExtra("travelId");
        initData();
    }

    private void initData() {
        onGoodsDetail();
    }


    private void startBanner(List<String> bannerBeans) {
        urls.clear();
        urls = bannerBeans;
        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (urls.size() > 0) {

                }
            }
        });
    }

    private void onGoodsDetail(){
        if (TextUtils.isEmpty(travelId)){
            ToastUtils.showToast("当前商品不存在或已下架");
            return;
        }

        m.travelDetail(travelId, bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<TravelDetailBean>(this,true) {
                    @Override
                    public void onNext(TravelDetailBean goodsDetailBean) {
                        String url = BaseApi.HOST_URL+ goodsDetailBean.getImgUrl();
                        List<String> urls = new ArrayList<>();
                        urls.add(url);
                        startBanner(urls);
                        showGoodsDetail(goodsDetailBean);
                    }
                });
    }

    private void showGoodsDetail(TravelDetailBean travelDetailBean){
        tvGoodsName.setText(travelDetailBean.getTravelName());
        tvGoodsPrice.setText(travelDetailBean.getTravelPrice()+"/人");
//        tvPickCount.setText("已拼购："+travelDetailBean.getItemPickingBuyCount()+"人");
//        tvGoodsNum.setText("商品数量："+travelDetailBean.getItemInventoryQuantiry());
//        tvGoodsDealNum.setText("成交量："+travelDetailBean.getItemDealQuantiry());
    }


    @OnClick({R.id.ll_add_cart})
    public void onGoodsClick(View view){
        switch (view.getId()){
            case R.id.ll_add_cart:
                addCart();
                break;
        }
    }

    private void addCart(){
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user==null){
            ToastUtils.showToast("添加购物车失败");
            return;
        }
        String userId = user.getUserId();
        m.addCart(userId, travelId, "1", "2", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<String>(this,true) {
            @Override
            public void onNext(String s) {
                ToastUtils.showToast("添加购物车成功");
            }
        });
    }

}
