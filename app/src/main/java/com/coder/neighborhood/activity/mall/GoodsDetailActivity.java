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
import com.coder.neighborhood.bean.UserBean;
import com.coder.neighborhood.bean.mall.CommentBean;
import com.coder.neighborhood.bean.mall.GoodsInfoBean;
import com.coder.neighborhood.mvp.model.mall.MallModel;
import com.coder.neighborhood.mvp.vu.mall.GoodsDetailView;
import com.coder.neighborhood.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.smtt.sdk.WebView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.utils.TimeUtils;
import ww.com.core.widget.RoundImageView;
import ww.com.core.widget.TranslateTabBar;

/**
 * @author feng
 * @date 2018/1/9
 */

public class GoodsDetailActivity extends BaseActivity<GoodsDetailView, MallModel> {

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
    @BindView(R.id.tv_goods_type)
    TextView tvGoodsType;
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
    @BindView(R.id.riv)
    RoundImageView riv;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_no_more_comment)
    TextView tvNoMoreComment;


    private List<String> urls;
    private String goodSid;
    private GoodsInfoBean goodsInfoBean;

    private boolean isComment = false;


    public static void start(Context context, String goodSid) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("goodSid", goodSid);
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

    @Override
    public void onTitleLeft() {
        super.onTitleLeft();
        finish();
    }

    private void initData() {
        onGoodsDetail();
        comment();
    }

    private void initListener() {
        translate.setOnTabChangeListener(index -> showGoodsInfo(index));
    }

    private void showGoodsInfo(int index) {
        ssw.setVisibility(index == 0 ? View.VISIBLE : View.GONE);

        if (isComment){
            rlPartComment.setVisibility(index == 0 ? View.GONE : View.VISIBLE);
            tvNoMoreComment.setVisibility(View.GONE);
        }else {
            rlPartComment.setVisibility(View.GONE);
            tvNoMoreComment.setVisibility(index == 0 ? View.GONE : View.VISIBLE);
        }
    }


    private void startBanner(List<String> bannerBeans) {
        urls.clear();
        urls = bannerBeans;
        v.setUrls(urls);
        v.startBanner();
        v.getBanner().setOnBannerListener(position -> {
            if (urls.size() > 0) {

            }
        });
    }

    private void onGoodsDetail() {
        if (TextUtils.isEmpty(goodSid)) {
            ToastUtils.showToast("当前商品不存在或已下架");
            return;
        }

        m.goodsInfo(goodSid, bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<GoodsInfoBean>(this, true) {
                    @Override
                    public void onNext(GoodsInfoBean goodsDetailBean) {
                        goodsInfoBean = goodsDetailBean;
                        List<String> urls = new ArrayList<>();
                        for (GoodsInfoBean.ImgUrlsBean imgUrlsBean : goodsDetailBean.getImgUrls()) {
                            urls.add(imgUrlsBean.getImgUrl());
                        }
                        startBanner(urls);
                        showGoodsDetail(goodsDetailBean);
                    }
                });
    }

    private void showGoodsDetail(GoodsInfoBean goodsDetailBean) {
        tvGoodsName.setText(goodsDetailBean.getItemName());
        tvGoodsPrice.setText(goodsDetailBean.getItemPrice());
        tvPickCount.setText("已拼购：" + goodsDetailBean.getItemPickingBuyCount() + "人");
        tvGoodsType.setText("商品类型：" + goodsDetailBean.getItemCategoryName());
        tvGoodsNum.setText("商品数量：" + goodsDetailBean.getItemInventoryQuantiry());
        tvSinglePrice.setText(goodsDetailBean.getItemPrice());
        tvPickPrice.setText(goodsDetailBean.getItemPickingPrice());
        tvGoodsDealNum.setText("成交量：" + goodsDetailBean.getItemDealQuantiry());
    }


    @OnClick({R.id.ll_add_cart, R.id.btn_more_comment,R.id.ll_single_cart,R.id.ll_more_cart})
    public void onGoodsClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_cart:
                CartActivity.start(this);
                break;
            case R.id.btn_more_comment:
                CommentDetailActivity.start(this, goodSid);
                break;
            case R.id.ll_single_cart:
                addCart("1");
                break;
            case R.id.ll_more_cart:
                CommitPickOrderActivity.start(this);
                break;
            default:
                break;
        }
    }

    private void addCart(String num) {
        UserBean user = (UserBean) BaseApplication.getInstance().getUserInfo();
        if (user == null) {
            ToastUtils.showToast("添加失败");
            return;
        }
        String userId = user.getUserId();
        m.addCart(userId, goodSid, num, bindUntilEvent(ActivityEvent.DESTROY),
                new HttpSubscriber<String>(this, true) {
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showToast("添加成功",true);
                    }
                });
    }

    private void comment() {
        m.comments(goodSid, "1", "1", bindUntilEvent(ActivityEvent.DESTROY)
                , new HttpSubscriber<List<CommentBean>>(this, false) {
                    @Override
                    public void onNext(List<CommentBean> commentBeans) {
                        Debug.d(commentBeans.size() + "");
                        if (commentBeans != null && commentBeans.size() > 0) {
                            showComment(commentBeans.get(0));
                            isComment = true;
                        } else {
                            isComment = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        isComment = false;
                    }
                });
    }

    private void showComment(CommentBean commentBean) {
        ImageLoader.getInstance().displayImage(commentBean.getImgUrl(), riv, BaseApplication
                .getDisplayImageOptions(R.mipmap.ic_default_avatar));
        tvComment.setText(commentBean.getContent());
        tvTime.setText(TimeUtils.milliseconds2String(commentBean.getCreated(), new SimpleDateFormat("yyyy.MM.dd")));
    }

}
