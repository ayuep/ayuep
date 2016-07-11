package com.ws.apple.ayuep.ui.navigation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.AYuePApplication;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.StoreModel;
import com.ws.apple.ayuep.proxy.DeviceProxy;
import com.ws.apple.ayuep.proxy.StoreProxy;
import com.ws.apple.ayuep.util.DeviceUtil;

import java.sql.SQLException;
import java.util.List;

public class DashboardFragment extends BaseFragment {

    private String mTitle;

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
        return v;
    }

    private void getData() {

        new StoreProxy().getAllStores(getActivity(), new StoreAsyncHttpResponseHandler());

//        String deviceIdentity = DeviceUtil.getDeviceIdentity(getActivity());
//        new DeviceProxy().registerDevice(getActivity());
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

    private class DashboardAsyncHttpResponseHandler  extends  BaseAsyncHttpResponseHandler{

        @Override
        public void onSuccess(String response) {

        }
    };
}
