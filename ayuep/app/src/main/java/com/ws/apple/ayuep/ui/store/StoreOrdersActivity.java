package com.ws.apple.ayuep.ui.store;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.OrderModel;
import com.ws.apple.ayuep.model.StoreOrderModel;
import com.ws.apple.ayuep.proxy.OrderProxy;
import com.ws.apple.ayuep.ui.navigation.OrderFragment;
import com.ws.apple.ayuep.util.ContactUtil;
import com.ws.apple.ayuep.util.DateUtil;
import com.ws.apple.ayuep.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by apple on 16/7/23.
 */

public class StoreOrdersActivity extends BaseActivity {

    private List<StoreOrderModel> mStoreOrders = new ArrayList<>();

    private ListView mListView;

    private PtrFrameLayout mPtrFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        initView();

        showProgressDialog(false, "获取数据中...");
        refreshData();
    }

    private void refreshData() {
        new OrderProxy().getStoreOrders(this, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                if (!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();

                    List<StoreOrderModel> orders = gson.fromJson(response, new TypeToken<List<StoreOrderModel>>() {
                    }.getType());

                    mStoreOrders.clear();
                    for (StoreOrderModel order: orders) {
                        mStoreOrders.add(order);
                    }

                    StoreOrderItemAdapter adapter = (StoreOrderItemAdapter) mListView.getAdapter();
                    adapter.notifyDataSetChanged();
                    Log.d("re", "success");
                }

                mPtrFrameLayout.refreshComplete();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);

                mPtrFrameLayout.refreshComplete();
                dismissProgressDialog();
            }
        });
    }

    private void initView() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

        mListView = (ListView) findViewById(R.id.id_store_order_list_view);
        mListView.setAdapter(new StoreOrderItemAdapter(this, mStoreOrders));
    }

    private class StoreOrderItemAdapter extends CommonAdapter<StoreOrderModel> {

        public StoreOrderItemAdapter(Context context, List<StoreOrderModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final StoreOrderModel storeOrder = datas.get(i);
            ViewHolder holder = ViewHolder.get(StoreOrdersActivity.this, view, viewGroup, R.layout.list_store_order_item);
            holder.setText(R.id.id_store_order_product_name, storeOrder.getProduct().getProductDescription())
                    .setText(R.id.id_store_order_product_price, "¥ " + Double.toString(storeOrder.getProduct().getPrice()))
                    .setText(R.id.id_store_order_prodcut_status, StringUtil.getOrderStatusString(storeOrder.getOrder().getStatus()))
                    .setText(R.id.id_store_order_customer_name, storeOrder.getCustomer().getCustomerName())
                    .setText(R.id.id_store_order_customer_phone, storeOrder.getCustomer().getPhoneNumber())
                    .setText(R.id.id_store_order_customer_address, storeOrder.getCustomer().getAddress())
                    .setText(R.id.id_store_order_expect_time, DateUtil.getDateTimeString(DateUtil.dateFromServerString(storeOrder.getOrder().getExpectedTime())));
            String[] images = storeOrder.getProduct().getImages().split(",");
            if (images.length >= 1) {
                ImageView imageView = (ImageView) holder.getView(R.id.id_product_image);
                ImageLoader.getInstance().displayImage(images[0], imageView);
            }

            ImageButton callButton = (ImageButton) holder.getView(R.id.id_call_button);
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactUtil.call(StoreOrdersActivity.this, storeOrder.getCustomer().getPhoneNumber());
                }
            });

            ImageButton SMSButton = (ImageButton) holder.getView(R.id.id_sms_button);
            SMSButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactUtil.sendMessage(StoreOrdersActivity.this, storeOrder.getCustomer().getPhoneNumber());
                }
            });

            Button receiveButton = (Button) holder.getView(R.id.id_receiver_button);
            receiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderModel order = storeOrder.getOrder();
                    order.setStatus("RECEIVED");
                    showProgressDialog(false, "更新订单状态中...");
                    new OrderProxy().updateOrderStatus(StoreOrdersActivity.this, order, new BaseAsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(String response) {
                            refreshData();
                            dismissProgressDialog();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseBytes, throwable);
                            dismissProgressDialog();
                            Toast.makeText(StoreOrdersActivity.this, "更新失败, 请稍后再试。", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            return holder.getConvertView();
        }
    }
}
