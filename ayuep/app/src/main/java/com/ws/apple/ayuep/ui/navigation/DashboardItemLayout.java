package com.ws.apple.ayuep.ui.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ws.apple.ayuep.R;

/**
 * Created by apple on 16/7/14.
 */

@SuppressLint("ViewConstructor")
public class DashboardItemLayout extends LinearLayout {

    public DashboardItemLayout(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.type_list_item, null);
        this.addView(view);
    }
}
