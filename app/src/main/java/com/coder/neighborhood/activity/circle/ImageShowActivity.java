package com.coder.neighborhood.activity.circle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.coder.neighborhood.R;
import com.coder.neighborhood.activity.BaseActivity;
import com.coder.neighborhood.adapter.circle.PhotoAdapter;
import com.coder.neighborhood.mvp.model.VoidModel;
import com.coder.neighborhood.mvp.vu.VoidView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author feng
 * @Date 2018/2/5.
 */

public class ImageShowActivity extends BaseActivity<VoidView,VoidModel> {

    @BindView(R.id.vp)
    ViewPager vp;

    private PhotoAdapter adapter;
    private ArrayList<String> photos;
    private int index;

    public static void start(Context context, int index, ArrayList<String> photos) {
        Intent starter = new Intent(context, ImageShowActivity.class);
        starter.putExtra("index", index);
        starter.putExtra("photos", photos);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image_show;
    }

    @Override
    protected void init() {
        index = getIntent().getIntExtra("index", 0);
        photos = (ArrayList<String>) getIntent().getSerializableExtra("photos");
        if (photos == null) {
            return;
        }
        adapter = new PhotoAdapter(photos);
        vp.setAdapter(adapter);
        vp.setCurrentItem(index);
    }
}
