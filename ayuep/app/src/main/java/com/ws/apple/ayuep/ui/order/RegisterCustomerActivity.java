package com.ws.apple.ayuep.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterCustomerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);
        Button button1 = (Button)findViewById(R.id.test);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText customerNameEditText = (EditText) findViewById(R.id.id_customer_name);
                boolean IsPhoneNumber = IsPhoneNumberValid(customerNameEditText.getText().toString());
                String output = "number is "+ IsPhoneNumber;
                Toast.makeText(RegisterCustomerActivity.this,output,Toast.LENGTH_LONG).show();
            }
        });
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
        boolean IsPhoneNumber = IsPhoneNumberValid(customerNameEditText.getText().toString());
        String output = "number is "+ IsPhoneNumber;
        Toast.makeText(RegisterCustomerActivity.this,output,Toast.LENGTH_LONG).show();
    }
//    private class CostomerClickListener extends View.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//            EditText customerNameEditText = (EditText) findViewById(R.id.id_customer_name);
//            boolean IsPhoneNumber = IsPhoneNumberValid(customerNameEditText.getText().toString());
//            String output = "number is "+ IsPhoneNumber;
//            Toast.makeText(RegisterCustomerActivity.this,output,Toast.LENGTH_LONG).show();
//        }
//    }
}
