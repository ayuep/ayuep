package com.ws.apple.ayuep.ui.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.R;

public class StoreFragment extends BaseFragment {

    private String mTitle;

    public static StoreFragment getInstance() {
        StoreFragment mainFragment = new StoreFragment();
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store_navigation, null);
//        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
//        card_title_tv.setText(mTitle);

        return v;
    }

}
