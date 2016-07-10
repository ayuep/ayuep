package com.ws.apple.ayuep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ws.apple.ayuep.ui.navigation.BottomNavigationActivity;

/*
* 设置启动的背景页面。
*/
public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        jumpToNavigation();
    }

    private void jumpToNavigation() {
        Intent intent = new Intent(LauncherActivity.this, BottomNavigationActivity.class);
        startActivity(intent);
    }
}
