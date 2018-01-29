package com.coder.neighborhood.adapter.circle;

import android.content.Context;
import android.view.View;

import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.circle.EventBean;

import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @Author feng
 * @Date 2018/1/29
 */

public class EventAdapter extends RvAdapter<EventBean>{

    public EventAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_event;
    }

    @Override
    protected RvViewHolder<EventBean> getViewHolder(int viewType, View view) {
        return new EventViewHolder(view);
    }

    class EventViewHolder extends RvViewHolder<EventBean>{

        public EventViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, EventBean bean) {

        }
    }
}
