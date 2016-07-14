package com.ws.apple.ayuep.ui.navigation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.ConfigurationModel;
import com.ws.apple.ayuep.proxy.ConfigurationProxy;
import com.ws.apple.ayuep.proxy.DeviceProxy;
import com.ws.apple.ayuep.proxy.StoreProxy;

import java.sql.SQLException;
import java.util.List;

public class BottomNavigationActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private String TAG = BottomNavigationActivity.class.getSimpleName();

    private BottomNavigationBar mBottomNavigationBar;
    private DashboardFragment mDashboardFragment;
    private StoreFragment mStoreFragment;
    private OrderFragment mOrderFragment;
    private MyInfoFragment mMyInfoFragment;
    private ConfigurationModel mConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        initNavigation();
        getData();
    }

    private void initNavigation() {
        int lastSelectedPosition = 0;
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_home_unselect, getString(R.string.navigation_dashboard)))
                .addItem(new BottomNavigationItem(R.mipmap.tab_contact_unselect, getString(R.string.navigation_all_stores)))
                .addItem(new BottomNavigationItem(R.mipmap.tab_contact_unselect, getString(R.string.navigation_my_order)))
                .addItem(new BottomNavigationItem(R.mipmap.tab_contact_unselect, getString(R.string.navigation_my_info)))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mDashboardFragment = DashboardFragment.getInstance();
        transaction.replace(R.id.tb, mDashboardFragment);
        setTitle(R.string.navigation_dashboard);
        findViewById(R.id.tb).setBackgroundResource(R.color.colorDashboardBackgroud);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mDashboardFragment == null) {
                    mDashboardFragment = DashboardFragment.getInstance();
                }
                transaction.replace(R.id.tb, mDashboardFragment);
                findViewById(R.id.tb).setBackgroundResource(R.color.colorDashboardBackgroud);
                setTitle(R.string.navigation_dashboard);
                break;
            case 1:
                if (mStoreFragment == null) {
                    mStoreFragment = StoreFragment.getInstance();
                }
                transaction.replace(R.id.tb, mStoreFragment);
                setTitle(R.string.navigation_all_stores);
                findViewById(R.id.tb).setBackgroundResource(R.color.colorWhiteColor);
                break;
            case 2:
                if (mOrderFragment == null) {
                    mOrderFragment = OrderFragment.getInstance();
                }
                transaction.replace(R.id.tb, mOrderFragment);
                setTitle(R.string.navigation_my_order);
                findViewById(R.id.tb).setBackgroundResource(R.color.colorWhiteColor);
                break;
            case 3:
                if (mMyInfoFragment == null) {
                    mMyInfoFragment = MyInfoFragment.getInstance();
                }
                transaction.replace(R.id.tb, mMyInfoFragment);
                setTitle(R.string.navigation_my_info);
                findViewById(R.id.tb).setBackgroundResource(R.color.colorWhiteColor);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        Log.d(TAG, "onTabUnselected() called with: " + "position = [" + position + "]");
    }

    @Override
    public void onTabReselected(int position) {

    }

    public void getData() {
        new StoreProxy().getAllStores(this, new StoreAsyncHttpResponseHandler());
        new DeviceProxy().registerDevice(this, new DeviceAsyncHttpResponseHandler());
        new ConfigurationProxy().getConfiguration(this, new ConfigrationAsyncHttpResponseHandler());
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

    private class ConfigrationAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                mConfiguration = gson.fromJson(response, new TypeToken<ConfigurationModel>(){}.getType());
                mDashboardFragment.setmConfiguration(mConfiguration);
                mDashboardFragment.initView();
            }
        }

    }
}
