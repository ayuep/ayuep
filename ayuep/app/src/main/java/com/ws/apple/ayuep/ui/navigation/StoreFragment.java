package com.ws.apple.ayuep.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.model.NavigatorType;
import com.ws.apple.ayuep.ui.product.ProductListActivity;

import java.sql.SQLException;
import java.util.List;

public class StoreFragment extends BaseFragment {

    private List<StoreInfoDBModel> mStores;

    private ListView mListView;

    private CommonAdapter<StoreInfoDBModel> mAdapter;

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

        getData();
        initView(v);

        return v;
    }

    private void initView(View v) {

        mListView = (ListView) v.findViewById(R.id.id_store_list_view);
        mAdapter = new StoreItemAdapter(getActivity(), mStores);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                ActionModel action = new ActionModel();
                action.setNavigatorType(NavigatorType.BYSTOREID);
                action.setStoreId(mStores.get(i).getStoreId());
                intent.putExtra("action", action);
                startActivity(intent);
            }
        });
    }

    private void getData() {

        StoreInfoDBModelDao storeInfoDBModelDao = new StoreInfoDBModelDao(getActivity());
        try {
            mStores = storeInfoDBModelDao.query();
        } catch (SQLException e) {

        }
    }

    private class StoreItemAdapter extends CommonAdapter<StoreInfoDBModel> {

        public StoreItemAdapter(Context context, List<StoreInfoDBModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            StoreInfoDBModel store = getItem(i);

            ViewHolder holder = ViewHolder.get(getActivity(), view, viewGroup, R.layout.list_store_item);
            holder.setText(R.id.id_store_name, store.getStoreName());
            holder.setText(R.id.id_store_address, store.getStoreAddress());
            ImageView imageView = (ImageView) holder.getView(R.id.id_store_image);
            ImageLoader.getInstance().displayImage(store.getStoreImage(), imageView);

            return holder.getConvertView();
        }

    }
}
