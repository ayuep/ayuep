/*
package com.ws.apple.ayuep.ui.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ws.apple.ayuep.R;

public class StoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_dashboard);
    }

    public void onClick(View v) {
        Intent intent = new Intent();

//        switch (v.getId()) {
//            case R.id.store :
//                navigtor = new StoreActivity();
//                break;
//            case R.id.products :
//                navigtor = new StoreActivity();
//                break;
//            case R.id.orders :
//                navigtor = new StoreActivity();
//                break;
//            case R.id.back :
//                navigtor = new StoreActivity();
//                break;
//            default:
//                navigtor = new StoreActivity();
//                break;
//        }

        intent.setClass(this, StoreInfoActivity.class);
        startActivity(intent);
    }
}
*/
