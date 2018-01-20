package com.coder.neighborhood.adapter.mall;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.CartBean;
import com.coder.neighborhood.mvp.listener.OnActionListener;
import com.coder.neighborhood.mvp.listener.OnItemClickListener;
import com.coder.neighborhood.widget.IconFontTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2018/1/9.
 */

public class CartAdapter extends RvAdapter<CartBean>{

    private OnItemClickListener onItemClickListener;
    private OnActionListener onActionListener;

    public CartAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_cart;
    }

    @Override
    protected RvViewHolder<CartBean> getViewHolder(int viewType, View view) {
        return new CartViewHolder(view);
    }

    class CartViewHolder extends RvViewHolder<CartBean>{

        @BindView(R.id.iv_goods_image)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @BindView(R.id.itv_check)
        IconFontTextView itvCheck;
        @BindView(R.id.ll_check)
        LinearLayout llCheck;
        @BindView(R.id.itv_delete)
        IconFontTextView itvDelete;

        public CartViewHolder(View itemView) {
            super(itemView);
            llCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener !=null){
                        onItemClickListener.onClick(position,v);
                    }
                }
            });

            itvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onActionListener !=null){
                        onActionListener.onAction(position,v);
                    }
                }
            });
        }

        @Override
        public void onBindData(int position, CartBean bean) {

            ImageLoader.getInstance().displayImage(bean.getHeadImg(),ivGoodsImage,
                    BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));

            tvGoodsName.setText(bean.getItemName());
            tvGoodsNum.setText("数量："+bean.getBuyCount());
            if (bean.isCheck()){
                itvCheck.setText("\ue6a5");
                itvCheck.setTextColor(ContextCompat.getColor(getContext(),R.color.color_text));
            }else {
                itvCheck.setText("\ue678");
                itvCheck.setTextColor(ContextCompat.getColor(getContext(),R.color.color_aaaaaa));
            }

        }
    }
}
