package com.ws.apple.ayuep.ui.order;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.AYuePApplication;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.model.CustomerModel;
import com.ws.apple.ayuep.model.OrderModel;
import com.ws.apple.ayuep.proxy.CustomerProxy;
import com.ws.apple.ayuep.proxy.OrderProxy;
import com.ws.apple.ayuep.util.DateUtil;


import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

import cz.msebera.android.httpclient.Header;

public class CreateOrderAcitvity extends BaseActivity {

    private List<CustomerModel> mCustomer;
    private final int REQUESTCODE = 13;
    private View mContentView;
    private String mProductId;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        setTitle("创建订单");
        mContentView = findViewById(R.id.id_create_order_content);

        initData();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE:
                if(resultCode == RESULT_OK){
                    CustomerModel customerModel = (CustomerModel) data.getSerializableExtra("customer_data");
                    SetViewText(customerModel);
                } else {
                    finish();
                }
                break;
            default:
                finish();
                break;
        }
    }
    private void initData() {
        showProgressDialog(false, "获取您的信息中...");
        mProductId = getIntent().getStringExtra("productId");
        new CustomerProxy().getCustomer(this,new CustomerAsyncHttpResponseHandler());
    }

    private void SetViewText(CustomerModel customerModel) {
        mContentView.setVisibility(View.VISIBLE);
        TextView customerName = (TextView)findViewById(R.id.id_store_order_customer_name);
        TextView customerAddress = (TextView)findViewById(R.id.id_store_order_customer_address);
        TextView customerPhone = (TextView)findViewById(R.id.id_store_order_customer_phone);
        mDateTextView = (TextView) findViewById(R.id.id_order_date_text);
        mTimeTextView = (TextView) findViewById(R.id.id_order_time_text);
        customerName.setText(customerModel.getCustomerName());
        customerAddress.setText(customerModel.getAddress());
        customerPhone.setText(customerModel.getPhoneNumber());
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
        mDateTextView.setText(DateUtil.getDateString(mCalendar.getTime()));
        mTimeTextView.setText(DateUtil.getTimeString(mCalendar.getTime()));

        View dateEditView = findViewById(R.id.id_order_date);
        dateEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateOrderAcitvity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mCalendar.set(i, i1, i2);
                        mDateTextView.setText(DateUtil.getDateString(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        View timeEditView = findViewById(R.id.id_order_time);
        timeEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(CreateOrderAcitvity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, i);
                        mCalendar.set(Calendar.MINUTE, i1);
                        mTimeTextView.setText(DateUtil.getTimeString(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show();
            }
        });
    }

    public void createOrder(View view) {
        if (mCalendar.compareTo(Calendar.getInstance()) <= 0) {
            Toast.makeText(this, "预定时间必须晚于当前时间! ", Toast.LENGTH_LONG).show();
            return;
        }
        showProgressDialog(false, "创建订单中...");
        OrderModel order = new OrderModel();
        order.setCustomerId(mCustomer.get(0).getCustomerId());
        order.setProductId(mProductId);
        order.setExpectedTime(DateUtil.getJsonDateString(mCalendar.getTime()));
        new OrderProxy().order(this, order, new OrderAsyncHttpResponseHandler());
    }

    private class MessageOk implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            Intent intent = new Intent(CreateOrderAcitvity.this,RegisterCustomerActivity.class);
            startActivityForResult(intent, REQUESTCODE);
        }
    }


    private class MessageNo implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            finish();
        }
    }

    private class CustomerAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {
        @Override
        public void onSuccess(String response) {
            try {
                if(!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();
                    mCustomer = gson.fromJson(response, new TypeToken<List<CustomerModel>>() {
                    }.getType());
                }
                if(mCustomer.size() <= 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrderAcitvity.this);
                    builder.setTitle("提示" );
                    builder.setMessage("你还没有设置预订人信息，请点击这里设置！" );
                    builder.setPositiveButton("是" ,new MessageOk() );
                    builder.setNegativeButton("否" ,new MessageNo());
                    builder.show();
                } else {
                    SetViewText(mCustomer.get(0));
                }
            }
            catch (Exception e) {
                Log.d("APIERROR",e.getMessage().toString());
            }
            dismissProgressDialog();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
            super.onFailure(statusCode, headers, responseBytes, throwable);
            dismissProgressDialog();
        }
    }

    private class OrderAsyncHttpResponseHandler extends BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            dismissProgressDialog();
            Toast.makeText(AYuePApplication.getmApplicationContext(), "预约成功! 祝您生活愉快。", Toast.LENGTH_LONG).show();
            finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
            super.onFailure(statusCode, headers, responseBytes, throwable);
            dismissProgressDialog();
            Log.d("111", "111");
        }
    }

}
