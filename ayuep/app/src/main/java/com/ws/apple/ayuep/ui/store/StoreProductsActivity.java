package com.ws.apple.ayuep.ui.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.model.NavigatorType;
import com.ws.apple.ayuep.ui.product.ProductDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/21.
 */

public class StoreProductsActivity extends BaseActivity {

    private List<ProductDBModel> mProducts = new ArrayList<>();
    final int PRODUCTCODE = 666;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_products);
        setTitle("所有套系");
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitData();
        InitView();
    }

    private void InitData() {
        mProducts = new ProductDBModelDao(this).queryByStoreId(DataCacheManager.getDataCacheManager(this).getStoreId(this));
    }

    private void InitView() {
        ListView listView = (ListView) findViewById(R.id.id_list_view);
        listView.setAdapter(new StoreProductItemAdapter(this, mProducts));
        Button addProducts = (Button)findViewById(R.id.id_store_products_add);
        addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreProductsActivity.this,StoreModify.class);
                startActivity(intent);
            }
        });
    }

    private class StoreProductItemAdapter extends CommonAdapter<ProductDBModel> {


        public StoreProductItemAdapter(Context context, List<ProductDBModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ProductDBModel product = mProducts.get(i);

            ViewHolder holder = ViewHolder.get(StoreProductsActivity.this, view, viewGroup, R.layout.list_my_product_item);
            holder.setText(R.id.id_product_description, product.getProductDescription());
            holder.setText(R.id.id_product_price, "¥ " + Double.toString(product.getPrice()));
            String[] images = product.getImages().split(",");
            if (images.length >= 1) {
                ImageView imageView = (ImageView) holder.getView(R.id.id_product_image);
                ImageLoader.getInstance().displayImage(images[0], imageView);
            }

            Button priviewButton = (Button) holder.getView(R.id.id_priveiw_button);
            priviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StoreProductsActivity.this, ProductDetailActivity.class);
                    ActionModel action = new ActionModel();
                    action.setNavigatorType(NavigatorType.BYPRODUCTID);
                    action.setProductId(product.getProductId());
                    action.setTitle("套系详情");
                    intent.putExtra("action", action);
                    startActivity(intent);
                }
            });

            View content = holder.getView(R.id.id_product_item);
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StoreProductsActivity.this,StoreModify.class);
                    intent.putExtra("data_product",(Serializable)product);
                    startActivity(intent);
                }
            });

            return holder.getConvertView();
        }
    }
}
