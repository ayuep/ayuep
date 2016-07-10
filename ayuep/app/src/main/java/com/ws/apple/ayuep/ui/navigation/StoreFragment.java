package com.ws.apple.ayuep.ui.navigation;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.proxy.StoreProxy;

import java.sql.SQLException;
import java.util.List;

public class StoreFragment extends BaseFragment {

    public static StoreFragment getInstance() {
        StoreFragment fragment = new StoreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store_navigation, null);
//        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
//        card_title_tv.setText(mTitle);
        getData();
        return v;
    }

    private void getData() {

        new StoreProxy().getAllStores(getActivity(), new StoreAsyncHttpResponseHandler());
    }

    private class StoreAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                List<StoreInfoDBModel> stores = gson.fromJson(response, new TypeToken<List<StoreInfoDBModel>>() {
                }.getType());

                // 存储在本地DB中。
                StoreInfoDBModelDao storeInfoDBModelDao = new StoreInfoDBModelDao(mContext);
                storeInfoDBModelDao.insert(stores);

                // TODO: 测试代码
                Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();

                try {
                    int d = storeInfoDBModelDao.query().size();
                    Log.d("Richard", "total count: " + d);
                } catch (SQLException e) {

                }

            }
        }
    }
}
