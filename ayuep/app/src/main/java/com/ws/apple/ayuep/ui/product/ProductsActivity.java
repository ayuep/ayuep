package com.ws.apple.ayuep.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.model.NavigatorType;
import com.ws.apple.ayuep.ui.order.CreateOrderAcitvity;

import java.sql.SQLException;
import java.util.List;

public class ProductsActivity extends BaseActivity {

    private List<ProductDBModel> mProducts;

    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        
        initView();
    }

    private void initView() {
        ActionModel action = (ActionModel) getIntent().getSerializableExtra("action");
        try {
            if (action.getNavigatorType() == NavigatorType.BYPRODUCTTYPE) {
                mProducts = new ProductDBModelDao(this).queryByProductType(action.getProductType());
            } else if (action.getNavigatorType() == NavigatorType.BYSTOREID) {
                mProducts = new ProductDBModelDao(this).queryByStoreId(action.getStoreId());
            }
        } catch (SQLException e) {
            Log.d("get product by type", e.getMessage());
        }

        mGridView = (GridView) findViewById(R.id.id_products_gridview);
        mGridView.setAdapter(new ProductItemAdapter(this, mProducts));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ProductsActivity.this, CreateOrderAcitvity.class);
                ActionModel action = new ActionModel();
                action.setNavigatorType(NavigatorType.BYPRODUCTID);
                action.setStoreId(mProducts.get(i).getProductId());
                intent.putExtra("action", action);
                startActivity(intent);
            }
        });
    }

    private class ProductItemAdapter extends CommonAdapter<ProductDBModel> {

        public ProductItemAdapter(Context context, List<ProductDBModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ProductDBModel prodcut = mProducts.get(i);

            ViewHolder holder = ViewHolder.get(ProductsActivity.this, view, viewGroup, R.layout.product_grid_item);
            holder.setText(R.id.id_product_item_price, Double.toString(prodcut.getPrice()));
            String[] images = prodcut.getImages().split(",");

            ImageView imageView = (ImageView) holder.getView(R.id.id_product_image);
            if (images.length > 0) {
                ImageLoader.getInstance().displayImage(images[0], imageView);
            }

            return holder.getConvertView();
        }
    }
}
