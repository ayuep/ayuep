package com.ws.apple.ayuep.ui.product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ws.apple.ayuep.R;

import java.util.List;

/**
 * Created by apple on 16/7/16.
 */

public class PhotoViewActivity extends Activity {

    private ViewPager search_viewpager;

    private List<String> imgs;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_view);

        this.position = getIntent().getIntExtra("position", 0);

        this.imgs = getIntent().getStringArrayListExtra("imgs");

        search_viewpager = (ViewPager) this.findViewById(R.id.imgs_viewpager);

        PagerAdapter adapter = new MyViewPagerAdapter(this, imgs);
        search_viewpager.setAdapter(adapter);
        search_viewpager.setCurrentItem(position);
    }
}
