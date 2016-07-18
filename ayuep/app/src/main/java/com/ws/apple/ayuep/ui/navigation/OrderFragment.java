package com.ws.apple.ayuep.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.OrderModel;
import com.ws.apple.ayuep.proxy.OrderProxy;
import com.ws.apple.ayuep.ui.order.CommentActivity;
import com.ws.apple.ayuep.util.DateUtil;
import com.ws.apple.ayuep.util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class OrderFragment extends BaseFragment {

    private String mTitle;

    private PtrFrameLayout mPtrFrameLayout;

    private List<OrderModel> mOrders = new ArrayList<>();

    private ListView mOrderListView;

    private View view;

    private BottomNavigationActivity parentActivity;

    public static OrderFragment getInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        showProgressDialog(true, "获取订单中...");
        refreshData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order_navigation, null);
        initView();
        return view;
    }

    private void refreshData() {
        new OrderProxy().getMyOrders(getActivity(), new orderAsyncHttpResponseHandler());
    }

    private void initView() {
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

        mOrderListView = (ListView) view.findViewById(R.id.id_my_order_list_view);
        mOrderListView.setAdapter(new OrderItemAdapter(getActivity(), mOrders));
    }

    private void refreshListView() {
        OrderItemAdapter adapter = (OrderItemAdapter) mOrderListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public void setParentActivity(BottomNavigationActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    private class OrderItemAdapter extends CommonAdapter<OrderModel> {

        public OrderItemAdapter(Context context, List<OrderModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final OrderModel order = getItem(i);
            ProductDBModel product = order.getProduct();

            ViewHolder holder = ViewHolder.get(getActivity(), view, viewGroup, R.layout.list_customer_order_item);

            if (product != null) {
                holder.setText(R.id.id_order_product_name, product.getProductDescription());
                holder.setText(R.id.id_order_product_price, "¥ " + Double.toString(product.getPrice()));
                String[] images = product.getImages().split(",");
                if (images.length >= 1) {
                    ImageView imageView = (ImageView) holder.getView(R.id.id_order_product_image);
                    ImageLoader.getInstance().displayImage(images[0], imageView);
                }
            }

            holder.setText(R.id.id_order_expected_time, DateUtil.getDateTimeString(DateUtil.dateFromServerString(order.getExpectedTime())));
            holder.setText(R.id.id_order_status, StringUtil.getOrderStatusString(order.getStatus()));

            View commentContent = holder.getView(R.id.id_order_comment_content);
            commentContent.setVisibility(order.getStatus().equals("WAITINGFORCOMMENTS") ? View.VISIBLE : View.GONE);
            Button commentbutton = (Button) holder.getView(R.id.id_order_comments_button);
            commentbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CommentActivity.class);
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
            });

            return holder.getConvertView();
        }

    }

    private class orderAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            Log.d("order", "refresh success");
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                List<OrderModel> orders = gson.fromJson(response, new TypeToken<List<OrderModel>>() {
                }.getType());

                mOrders.clear();
                for (OrderModel order : orders) {
                    try {
                        order.setProduct(new ProductDBModelDao(getActivity()).queryByProductId(order.getProductId()));
                    } catch (SQLException e) {
                        Log.d("get product", "failed");
                    }

                    mOrders.add(order);
                }

                refreshListView();
            }
            mPtrFrameLayout.refreshComplete();
            dismissProgressDialog();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
            Log.d("order", "refresh failed");
            super.onFailure(statusCode, headers, responseBytes, throwable);
            dismissProgressDialog();
        }
    }

}
