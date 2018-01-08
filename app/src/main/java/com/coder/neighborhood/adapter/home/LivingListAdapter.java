package com.coder.neighborhood.adapter.home;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.home.LivingDetailActivity;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * Created by feng on 2018/1/8.
 */

public class LivingListAdapter extends RvAdapter<String> {

    public LivingListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_living_list;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new LivingListViewHolder(view);
    }

    class LivingListViewHolder extends RvViewHolder<String>{

        public LivingListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LivingDetailActivity.start(getContext());
                }
            });

        }
    }
}
