package com.ws.apple.ayuep.ui.navigation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.proxy.DeviceProxy;

public class BottomNavigationActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    private String TAG = BottomNavigationActivity.class.getSimpleName();

    private BottomNavigationBar mBottomNavigationBar;
    private DashboardFragment mDashboardFragment;
    private StoreFragment mStoreFragment;
    private OrderFragment mOrderFragment;
    private MyInfoFragment mMyInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        initNavigation();

        new DeviceProxy().registerDevice(this);
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
                setTitle(R.string.navigation_dashboard);
                break;
            case 1:
                if (mStoreFragment == null) {
                    mStoreFragment = StoreFragment.getInstance();
                }
                transaction.replace(R.id.tb, mStoreFragment);
                setTitle(R.string.navigation_all_stores);
                break;
            case 2:
                if (mOrderFragment == null) {
                    mOrderFragment = OrderFragment.getInstance();
                }
                transaction.replace(R.id.tb, mOrderFragment);
                setTitle(R.string.navigation_my_order);
                break;
            case 3:
                if (mMyInfoFragment == null) {
                    mMyInfoFragment = MyInfoFragment.getInstance();
                }
                transaction.replace(R.id.tb, mMyInfoFragment);
                setTitle(R.string.navigation_my_info);
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
}
