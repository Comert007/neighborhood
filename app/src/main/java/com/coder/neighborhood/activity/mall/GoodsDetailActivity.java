package com.coder.neighborhood.activity.mall;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.activity.rx.HttpSubscriber;
import com.coder.neighborhood.api.BaseApi;
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.CommentBean;
import com.coder.neighborhood.bean.mall.GoodsDetailBean;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.GoodsDetailView;
import com.coder.neighborhood.utils.ToastUtils;
import com.tencent.smtt.sdk.WebView;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.widget.TranslateTabBar;

/**
 * @Author feng
 * @Date 2018/1/9
 *
 */

public class GoodsDetailActivity extends BaseActivity<GoodsDetailView,MallModel> {

    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_pick_count)
    TextView tvPickCount;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_pick_price)
    TextView tvPickPrice;
    @BindView(R.id.tv_single_price)
    TextView tvSinglePrice;
    @BindView(R.id.tv_goods_deal_num)
    TextView tvGoodsDealNum;
    @BindView(R.id.translate)
    TranslateTabBar translate;
    @BindView(R.id.ssw)
    WebView ssw;
    @BindView(R.id.rl_part_comment)
    RelativeLayout rlPartComment;



    private List<String> urls;
    private  String goodSid;


    public static void start(Context context,String goodSid) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodSid",goodSid);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void init() {
        urls = new ArrayList<>();
        goodSid = getIntent().getStringExtra("goodSid");
        initListener();
        initData();
    }

    private void initData() {
        onGoodsDetail();
        comment();
    }

    private void initListener(){
        translate.setOnTabChangeListener(new TranslateTabBar.OnTabChangeListener() {
            @Override
            public void onChange(int index) {
                showGoodsInfo(index);
            }
        });
    }

    private void showGoodsInfo(int index){
        ssw.setVisibility(index == 0? View.VISIBLE:View.GONE);
        rlPartComment.setVisibility(index == 0?View.GONE:View.VISIBLE);
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
        if (TextUtils.isEmpty(goodSid)){
            ToastUtils.showToast("当前商品不存在或已下架");
            return;
        }

        m.goodsDetail(goodSid, bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<GoodsDetailBean>(this,true) {
                    @Override
                    public void onNext(GoodsDetailBean goodsDetailBean) {
                        String url = BaseApi.HOST_URL+ goodsDetailBean.getImgUrl();
                        List<String> urls = new ArrayList<>();
                        urls.add(url);
                        startBanner(urls);
                        showGoodsDetail(goodsDetailBean);
                    }
                });
    }

    private void showGoodsDetail(GoodsDetailBean goodsDetailBean){
        tvGoodsName.setText(goodsDetailBean.getItemName());
        tvGoodsPrice.setText(goodsDetailBean.getItemPrice()+"/"+goodsDetailBean.getItemUnit());
        tvPickCount.setText("已拼购："+goodsDetailBean.getItemPickingBuyCount()+"人");
        tvGoodsNum.setText("商品数量："+goodsDetailBean.getItemInventoryQuantiry());
        tvSinglePrice.setText(goodsDetailBean.getItemBasePrice());
        tvPickPrice.setText(goodsDetailBean.getItemPickingPrice());
        tvGoodsDealNum.setText("成交量："+goodsDetailBean.getItemDealQuantiry());
    }


    @OnClick({R.id.ll_add_cart,R.id.btn_more_comment})
    public void onGoodsClick(View view){
        switch (view.getId()){
            case R.id.ll_add_cart:
                addCart();
                break;
            case R.id.btn_more_comment:
                CommentDetailActivity.start(this,goodSid);
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
        m.addCart(userId, goodSid, "1", "1", bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<String>(this,true) {
            @Override
            public void onNext(String s) {
                ToastUtils.showToast("添加购物车成功");
            }
        });
    }

    private void comment(){
        m.comments(goodSid, "1", "1", bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<CommentBean>>(this,false) {
                    @Override
                    public void onNext(List<CommentBean> commentBeans) {
                        if (commentBeans!=null && commentBeans.size()>0){
                            rlPartComment.setVisibility(View.VISIBLE);
                        }else {
                            rlPartComment.setVisibility(View.GONE);
                        }
                    }
                });
    }

}
