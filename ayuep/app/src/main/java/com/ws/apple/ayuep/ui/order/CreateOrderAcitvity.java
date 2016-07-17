package com.ws.apple.ayuep.ui.order;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.ActionModel;
import com.ws.apple.ayuep.model.CustomerModel;
import com.ws.apple.ayuep.proxy.CustomerProxy;


import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class CreateOrderAcitvity extends AppCompatActivity {

    private List<CustomerModel> mCustomer;
    private final int REQUESTCODE = 13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_create_order);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            Calendar calendar = Calendar.getInstance();
//        }else {
//
//        }
        InitView();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE:
                if(resultCode == RESULT_OK){
                    CustomerModel customerModel = (CustomerModel) data.getSerializableExtra("customer_data");
                    SetViewText(customerModel);
                }

        }
    }
    private void InitView()
    {
         new CustomerProxy().getCustomer(this,new CustomerAsyncHttpResponseHandler());
//
    }

    private void SetViewText(CustomerModel customerModel) {
        TextView customerName = (TextView)findViewById(R.id.id_store_order_customer_name);
        TextView customerAddress = (TextView)findViewById(R.id.id_store_order_customer_address);
        TextView customerPhone = (TextView)findViewById(R.id.id_store_order_customer_phone);
        customerName.setText(customerModel.getCustomerName());
        customerAddress.setText(customerModel.getAddress());
        customerPhone.setText(customerModel.getPhoneNumber());
    }
    private void IsGotoOrderActivity(){
//        new CustomerProxy().getCustomer(this,new CustomerAsyncHttpResponseHandler());

    }
    private class MessageOk implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            Intent intent = new Intent(CreateOrderAcitvity.this,RegisterCustomerActivity.class);
            startActivityForResult(intent,REQUESTCODE);
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
//                    Log.d("createorder",mCustomer.get(0).getCustomerName().toString());
                }
                if(mCustomer.size()<=0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrderAcitvity.this);
                    builder.setTitle("提示" );
                    builder.setMessage("你还没有设置预订人信息，请点击这里设置！" );
                    builder.setPositiveButton("是" ,new MessageOk() );
                    builder.setNegativeButton("否" ,new MessageNo());
                    builder.show();
                }
                else
                {
                    SetViewText(mCustomer.get(0));
                }
            }
            catch (Exception e) {
                Log.d("APIERROR",e.getMessage().toString());
            }
        }


    }

}
