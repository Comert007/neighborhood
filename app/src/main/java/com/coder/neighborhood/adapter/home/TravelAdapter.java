package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.home.TravelDetailActivity;
import com.coder.neighborhood.bean.home.TravelBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2018/1/8.
 */

public class TravelAdapter extends RvAdapter<TravelBean> {

    public TravelAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_travel;
    }

    @Override
    protected RvViewHolder<TravelBean> getViewHolder(int viewType, View view) {
        return new TravelViewHolder(view);
    }

    class TravelViewHolder extends RvViewHolder<TravelBean>{

        @BindView(R.id.tv_travel_name)
        TextView tvTravelName;
        @BindView(R.id.tv_travel_route)
        TextView tvTravelRoute;
        @BindView(R.id.tv_travel_price)
        TextView tvTravelPrice;
        @BindView(R.id.iv_bg)
        ImageView ivBg;



        public TravelViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBindData(int position, final TravelBean bean) {
            tvTravelName.setText(bean.getTravelName());
            tvTravelRoute.setText(bean.getTraveRoute());
            tvTravelPrice.setText(bean.getTravePrice()+"元/人起");

            ImageLoader.getInstance().displayImage(bean.getImgUrl(),ivBg, BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));

            itemView.setOnClickListener(v -> TravelDetailActivity.start(getContext(),bean.getTravelId()));
        }
    }
}
