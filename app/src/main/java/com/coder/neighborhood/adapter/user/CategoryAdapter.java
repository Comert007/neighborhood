package com.coder.neighborhood.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.coder.neighborhood.R;
import com.coder.neighborhood.bean.mall.CategoryBean;
import com.coder.neighborhood.mvp.listener.OnActionListener;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @Date 2018/2/5.
 */

public class CategoryAdapter extends RvAdapter<CategoryBean> {

    private OnActionListener onActionListener;

    public CategoryAdapter(Context context) {
        super(context);
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_community;
    }

    @Override
    protected RvViewHolder<CategoryBean> getViewHolder(int viewType, View view) {
        return new CommunityViewHolder(view);
    }

    class CommunityViewHolder extends RvViewHolder<CategoryBean>{
        @BindView(R.id.tv_community)
        TextView tvCommunity;

        public CommunityViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, CategoryBean bean) {
            tvCommunity.setText(TextUtils.isEmpty(bean.getItemCategoryName())?"":bean.getItemCategoryName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onActionListener!=null){
                        onActionListener.onAction(position,v);
                    }
                }
            });
        }
    }
}
