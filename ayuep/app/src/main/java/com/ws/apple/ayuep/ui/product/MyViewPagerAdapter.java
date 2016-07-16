package com.ws.apple.ayuep.ui.product;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class MyViewPagerAdapter extends PagerAdapter {

    List<String> imgs;

    Context mContext;

    List<View> mViews;

    public MyViewPagerAdapter(Context context, List<String> imgs) {

        this.mContext = context;
        this.imgs = imgs;
        mViews = new ArrayList<View>();

    }

    @Override
    public int getCount() { // 获得size
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(mViews.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        String imgUrl = imgs.get(position);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.photo_view_page, null);
        PhotoView img = (PhotoView) view.findViewById(R.id.id_photo_view);
        img.setTag(imgUrl);
        ImageLoader.getInstance().displayImage(imgs.get(position), img);

        ((ViewPager) container).addView(view);
        mViews.add(view);
        return view;
    }
}