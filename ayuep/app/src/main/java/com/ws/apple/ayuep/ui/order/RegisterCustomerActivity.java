package com.ws.apple.ayuep.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.CommentModel;
import com.ws.apple.ayuep.model.CustomerModel;
import com.ws.apple.ayuep.proxy.CustomerProxy;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ws.apple.ayuep.util.StringUtil.isPhoneNumberValid;

public class RegisterCustomerActivity extends BaseActivity {
    private CustomerModel mCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
    }

    private class CostomerClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText customerPhoneEditText = (EditText) findViewById(R.id.id_customer_phone);
            String phoneNumber = customerPhoneEditText.getText().toString();

            EditText customerNameEditText = (EditText) findViewById(R.id.id_customer_name);
            String customerName = customerNameEditText.getText().toString();

            EditText customerAddressEditText = (EditText) findViewById(R.id.id_customer_address);
            String customerAddress = customerAddressEditText.getText().toString();

            if (TextUtils.isEmpty(customerName)) {
                Toast.makeText(RegisterCustomerActivity.this, "请输入姓名! ", Toast.LENGTH_LONG).show();
                return;
            }

            if (!isPhoneNumberValid(phoneNumber)) {
                Toast.makeText(RegisterCustomerActivity.this, "请输入正确的电话号码! ", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(customerAddress)) {
                Toast.makeText(RegisterCustomerActivity.this, "请输入地址! ", Toast.LENGTH_LONG).show();
                return;
            }


            if(mCustomer==null) {
                mCustomer = new CustomerModel();
            }
            mCustomer.setCustomerName(customerNameEditText.getText().toString());
            mCustomer.setPhoneNumber(phoneNumber);
            mCustomer.setAddress(customerAddressEditText.getText().toString());
            new CustomerProxy().registerCustomer(RegisterCustomerActivity.this,mCustomer,new RegisterAsyncHttpResponseHandler());

        }
    }
    private class RegisterAsyncHttpResponseHandler extends  BaseAsyncHttpResponseHandler {

        @Override
        public void onSuccess(String response) {
            Log.d("customer", "success");
            if(!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();
                mCustomer =gson.fromJson(response, new TypeToken<CustomerModel>() {
                }.getType());
                Log.d("getcustomer","success");
                if(mCustomer.getCustomerId() != null) {
                    Intent intent = new Intent();
                    intent.putExtra("customer_data",(Serializable)mCustomer);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        }
    }
}
