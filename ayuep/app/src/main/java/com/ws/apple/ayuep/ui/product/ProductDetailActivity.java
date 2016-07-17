package com.ws.apple.ayuep.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.NetworkImageHolderView;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.proxy.ProductProxy;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by apple on 16/7/16.
 */

public class ProductDetailActivity extends BaseActivity {

    private ConvenientBanner mConvenientBanner;

    private ArrayList<String> mNetworkImages = new ArrayList<String>();

    private ProductDBModel mProduct;

    private StoreInfoDBModel mStore;

    private TextView mSalesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initData();
        initView();
    }

    private void initView() {
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);

        mConvenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, mNetworkImages)
                .setPointViewVisible(true)    //设置指示器是否可见
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});

        mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(ProductDetailActivity.this, PhotoViewActivity.class);
                intent.putStringArrayListExtra("imgs", mNetworkImages);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        TextView productDescriptionTextView = (TextView) findViewById(R.id.id_product_description);
        productDescriptionTextView.setText(mProduct.getProductDescription());

        TextView productPriceTextView = (TextView) findViewById(R.id.id_product_price);
        productPriceTextView.setText("¥ " + Double.toString(mProduct.getPrice()));

        mSalesTextView = (TextView) findViewById(R.id.id_order_count);
        mSalesTextView.setText("销售量: " + "0");

        TextView storeNameTextView = (TextView) findViewById(R.id.id_store_name);
        storeNameTextView.setText(mStore.getStoreName());

        TextView storeAddressTextView = (TextView) findViewById(R.id.id_store_address);
        storeAddressTextView.setText(mStore.getStoreAddress());

        TextView storePhoneTextView = (TextView) findViewById(R.id.id_store_phone);
        storePhoneTextView.setText(mStore.getPhoneNumber());
    }

    private void initData() {
        ActionModel action = (ActionModel) getIntent().getSerializableExtra("action");
        setTitle(action.getTitle());

        try {
            mProduct = new ProductDBModelDao(this).queryByProductId(action.getProductId());
            mStore = new StoreInfoDBModelDao(this).queryByStoreId(mProduct.getStoreId());
        } catch (SQLException e) {
            Log.d("Get product failed", e.getMessage());
        }

        String[] images = mProduct.getImages().split(",");
        for (String image : images) {
            mNetworkImages.add(image);
        }

        new ProductProxy().getProductSales(this, mProduct.getProductId(), new ProductAysncResponseHandler());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mConvenientBanner.setcurrentitem(data.getIntExtra("position", 0));
    }

    private class ProductAysncResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                JsonObject result = gson.fromJson(response, new TypeToken<JsonObject>(){}.getType());
                mSalesTextView.setText("销售量: " + result.get("sales"));
            }
        }
    }
}
