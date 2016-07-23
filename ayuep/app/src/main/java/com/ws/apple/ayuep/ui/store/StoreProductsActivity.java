package com.ws.apple.ayuep.ui.store;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/21.
 */

public class StoreProductsActivity extends BaseActivity {

    private List<ProductDBModel> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);

        initData();
        initView();
    }

    private void initData() {
        mProducts = new ProductDBModelDao(this).queryByStoreId(DataCacheManager.getDataCacheManager(this).getStoreId(this));
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.id_list_view);
        listView.setAdapter(new StoreProductItemAdapter(this, mProducts));
    }

    private class StoreProductItemAdapter extends CommonAdapter<ProductDBModel> {


        public StoreProductItemAdapter(Context context, List<ProductDBModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ProductDBModel product = mProducts.get(i);

            ViewHolder holder = ViewHolder.get(StoreProductsActivity.this, view, viewGroup, R.layout.list_my_product_item);
            holder.setText(R.id.id_product_description, product.getProductDescription());
            holder.setText(R.id.id_order_product_price, "¥ " + Double.toString(product.getPrice()));
            String[] images = product.getImages().split(",");
            if (images.length >= 1) {
                ImageView imageView = (ImageView) holder.getView(R.id.id_product_image);
                ImageLoader.getInstance().displayImage(images[0], imageView);
            }

            return holder.getConvertView();
        }
    }
}
