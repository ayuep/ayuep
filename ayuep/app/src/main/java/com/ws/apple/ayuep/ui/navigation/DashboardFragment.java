package com.ws.apple.ayuep.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.NetworkImageHolderView;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.model.ConfigurationModel;
import com.ws.apple.ayuep.model.NavigatorType;
import com.ws.apple.ayuep.model.ProductTypeModel;
import com.ws.apple.ayuep.model.RotationModel;
import com.ws.apple.ayuep.ui.product.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DashboardFragment extends BaseFragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {

    private String mTitle;
    private ConvenientBanner mConvenientBanner;
    private List<String> mNetworkImages = new ArrayList<String>();
    private LinearLayout mContentView;
    private View view;
    private ConfigurationModel mConfiguration;
    private BottomNavigationActivity mParentActivity;
    private PtrFrameLayout mPtrFrameLayout;

    private ArrayList<String> transformerList = new ArrayList<String>();

    public static DashboardFragment getInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard_navigation, null);

        getData();
        initView();
        return view;
    }

    public void initView() {
        if (mConfiguration != null) {
            mNetworkImages.clear();
            for (RotationModel rotation : mConfiguration.getBanners()) {
                mNetworkImages.add(rotation.getImageUrl());
            }
            mConvenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
            mConvenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new NetworkImageHolderView();
                }
            }, mNetworkImages)
                    .setPointViewVisible(true)    //设置指示器是否可见
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
            mContentView = (LinearLayout) view.findViewById(R.id.id_dashboard_content_view);
            mConvenientBanner.startTurning(3000);
            mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), ProductListActivity.class);
                    ActionModel action = new ActionModel();
                    action.setNavigatorType(NavigatorType.BYPRODUCTID);
                    action.setProductId(mConfiguration.getBanners().get(position).getProductId());
                    intent.putExtra("action", action);
                    startActivity(intent);
                }
            });

            for (ProductTypeModel productType: mConfiguration.getProductTypes()) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.type_list_item, null);
                TextView textView = (TextView) view.findViewById(R.id.id_type_name);
                textView.setText(productType.getProductTypeName());
                view.setTag(productType.getProductTypeName());
                view.setClickable(true);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ProductListActivity.class);
                        ActionModel action = new ActionModel();
                        action.setNavigatorType(NavigatorType.BYPRODUCTTYPE);
                        action.setProductType((String) view.getTag());
                        intent.putExtra("action", action);
                        startActivity(intent);
                    }
                });
                mContentView.addView(view);
            }
        }

        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mParentActivity.refresh();
            }
        });


    }

    private void getData() {
        Context context = getActivity();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Toast.makeText(this,"监听到翻到第"+position+"了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemClick(int position) {

    }

    public void setmConfiguration(ConfigurationModel mConfiguration) {
        this.mConfiguration = mConfiguration;
    }

    public BottomNavigationActivity getmParentActivity() {
        return mParentActivity;
    }

    public void setmParentActivity(BottomNavigationActivity mParentActivity) {
        this.mParentActivity = mParentActivity;
    }

    public PtrFrameLayout getmPtrFrameLayout() {
        return mPtrFrameLayout;
    }

    public void setmPtrFrameLayout(PtrFrameLayout mPtrFrameLayout) {
        this.mPtrFrameLayout = mPtrFrameLayout;
    }
}
