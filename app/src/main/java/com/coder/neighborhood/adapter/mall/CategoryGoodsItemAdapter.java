package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.CategoryGoodsItemBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @Author feng
 * @Date 2018/1/19
 */

public class CategoryGoodsItemAdapter extends RvAdapter<CategoryGoodsItemBean> {

    public CategoryGoodsItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_mall_content;
    }

    @Override
    protected RvViewHolder<CategoryGoodsItemBean> getViewHolder(int viewType, View view) {
        return new CategoryGoodsItemViewHolder(view);
    }


    class CategoryGoodsItemViewHolder extends RvViewHolder<CategoryGoodsItemBean> {
        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_title)
        TextView tvGoodsTitle;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_type)
        TextView tvGoodsType;
        @BindView(R.id.tv_goods_use)
        TextView tvGoodsUse;
        public CategoryGoodsItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }

        @Override
        public void onBindData(int position, CategoryGoodsItemBean bean) {
            ImageLoader.getInstance().displayImage(bean.getImgUrl(), ivGoodsImage,
                    BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));
            tvGoodsTitle.setText(TextUtils.isEmpty(bean.getItemName())?"":bean.getItemName());
            tvGoodsPrice.setText(TextUtils.isEmpty(bean.getItemPrice())?"":bean.getItemPrice());
//            tvGoodsType.setText(TextUtils.isEmpty(bean.getItemCategoryName())?"":bean.getItemCategoryName());
//            tvGoodsUse.setText(TextUtils.isEmpty(bean.getItemPickingPrice())?"":bean.getItemPickingPrice());
        }
    }
}
