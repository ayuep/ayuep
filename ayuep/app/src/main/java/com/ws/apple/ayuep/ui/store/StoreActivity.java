
package com.ws.apple.ayuep.ui.store;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.ws.apple.ayuep.R;

public class StoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_store_dashboard);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.store :
                intent.setClass(this, StoreInfoActivity.class);
                break;
            case R.id.products :
                intent.setClass(this, StoreProductsActivity.class);
                break;
            case R.id.orders :
                intent.setClass(this, StoreOrdersActivity.class);
                break;
            case R.id.back :
                finish();
                return;
            default:
                break;
        }

        startActivity(intent);
    }
}

