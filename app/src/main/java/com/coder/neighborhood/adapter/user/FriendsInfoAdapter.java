package com.coder.neighborhood.adapter.user;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coder.neighborhood.R;

import java.util.Arrays;

import butterknife.BindView;
import ww.com.core.adapter.RvAdapter;
import ww.com.core.adapter.RvViewHolder;

/**
 * @author feng
 * @date 2018/1/9
 */

public class FriendsInfoAdapter extends RvAdapter<String> {

    public FriendsInfoAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutResId(int viewType) {
        return R.layout.item_friends_info;
    }

    @Override
    protected RvViewHolder<String> getViewHolder(int viewType, View view) {
        return new GoodFriendsViewHolder(view);
    }

    class GoodFriendsViewHolder extends RvViewHolder<String>{
        @BindView(R.id.rv_images)
        RecyclerView rvImages;

        public GoodFriendsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData(int position, String bean) {
            ImageAdapter adapter = new ImageAdapter(getContext());
            GridLayoutManager manager = new GridLayoutManager(getContext(),3);
            rvImages.setLayoutManager(manager);
            rvImages.setAdapter(adapter);
            adapter.addList(Arrays.asList("1","2","3","4","5","6"));
        }
    }
}
