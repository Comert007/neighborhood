package com.coder.neighborhood.adapter.circle;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coder.neighborhood.BaseApplication;
import com.coder.neighborhood.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * @author feng
 */
public class PhotoAdapter extends PagerAdapter {

    List<String> photos;

    public PhotoAdapter(List<String> photos) {
        this.photos = photos;
        if (this.photos == null){
            this.photos = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setScaleType(ImageView.ScaleType.CENTER);
        ImageLoader.getInstance().displayImage(photos.get(position),photoView,
                BaseApplication.getDisplayImageOptions(R.mipmap.pic_default));

        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}