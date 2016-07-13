package com.ws.apple.ayuep.ui.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.AYuePApplication;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.NetworkImageHolderView;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.StoreModel;
import com.ws.apple.ayuep.proxy.DeviceProxy;
import com.ws.apple.ayuep.proxy.StoreProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Transformer;

import cz.msebera.android.httpclient.Header;

import static com.ws.apple.ayuep.util.ViewFindUtils.getResId;

public class DashboardFragment extends BaseFragment implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener, OnItemClickListener {

    private String mTitle;
    private ConvenientBanner mConvenientBanner;
    private List<String> mNetworkImages;

    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

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
        View v = inflater.inflate(R.layout.fragment_dashboard_navigation, null);

        getData();
        initView(v);
        return v;
    }

    private void initView(View v) {
        mNetworkImages = Arrays.asList(images);
        mConvenientBanner = (ConvenientBanner) v.findViewById(R.id.convenientBanner);
        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, mNetworkImages)
                .setPointViewVisible(true)    //设置指示器是否可见
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
    }

    private void getData() {
        new StoreProxy().getAllStores(getActivity(), new StoreAsyncHttpResponseHandler());
        new DeviceProxy().registerDevice(getActivity(), new DeviceAsyncHttpResponseHandler());
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        mConvenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        mConvenientBanner.stopTurning();
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


    private class StoreAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                List<StoreInfoDBModel> stores = gson.fromJson(response, new TypeToken<List<StoreInfoDBModel>>() {
                }.getType());

                // 存储在本地DB中。
                try {
                    StoreInfoDBModelDao storeInfoDBModelDao = new StoreInfoDBModelDao(mContext);
                    storeInfoDBModelDao.deleteAllStores();
                    storeInfoDBModelDao.insert(stores);

//                    Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();

                    int d = storeInfoDBModelDao.query().size();
                    Log.d("Richard", "total count: " + d);
                } catch (SQLException e) {
                    Log.d("1", e.getMessage());
                }

            }
        }
    }



    private class DeviceAsyncHttpResponseHandler extends  BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            Log.d("Keven", "success");
        }
    }

    private class DashboardAsyncHttpResponseHandler  extends  BaseAsyncHttpResponseHandler{

        @Override
        public void onSuccess(String response) {

        }
    };
}
