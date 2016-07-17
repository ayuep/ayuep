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

public class RegisterCustomerActivity extends BaseActivity {
    private CustomerModel mCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
        Button button1 = (Button)findViewById(R.id.test);
        button1.setOnClickListener(new CostomerClickListener());

    }

    private boolean IsPhoneNumberValid(String PhoneNumber ){
        boolean flag = false;
        String regex = "[1][358]\\d{9}";
        CharSequence inputStr = PhoneNumber;

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches() ) {
            flag = true;
        }

        return flag;
    }

    public void submitCustomer() {
        EditText customerNameEditText = (EditText) findViewById(R.id.id_customer_name);
        EditText customerPhoneEditText = (EditText) findViewById(R.id.id_customer_name);
        boolean IsPhoneNumber = IsPhoneNumberValid(customerNameEditText.getText().toString());
        String output = "number is "+ IsPhoneNumber;
        Toast.makeText(RegisterCustomerActivity.this,output,Toast.LENGTH_LONG).show();
    }
    private void CheckFormat() {
        EditText customerNameEditText = (EditText) findViewById(R.id.id_customer_name);
        EditText customerPhoneEditText = (EditText) findViewById(R.id.id_customer_phone);
        EditText customerAddressEditText = (EditText) findViewById(R.id.id_customer_address);
        boolean IsPhoneNumber = IsPhoneNumberValid(customerNameEditText.getText().toString());
        String output = "number is "+ IsPhoneNumber;
        Toast.makeText(RegisterCustomerActivity.this,output,Toast.LENGTH_LONG).show();
//        CustomerModel customerModel = new CustomerModel();
        if(mCustomer==null) {
            mCustomer = new CustomerModel();
        }
        mCustomer.setCustomerName(customerNameEditText.getText().toString());
        mCustomer.setPhoneNumber(customerPhoneEditText.getText().toString());
        mCustomer.setAddress(customerAddressEditText.getText().toString());
//        mCustomer.add(customerModel);
    }
    private class CostomerClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            CheckFormat();
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
                if(mCustomer.getCustomerId()!=null) {
                    Intent intent = new Intent();
                    intent.putExtra("customer_data",(Serializable)mCustomer);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        }
    }
}
