package com.ws.apple.ayuep.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ui.order.RegisterCustomerActivity;
import com.ws.apple.ayuep.ui.store.StoreActivity;

public class MyInfoFragment extends BaseFragment {

    private String mTitle;

    public static MyInfoFragment getInstance() {
        MyInfoFragment fragment = new MyInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_my_info_navigation, null);
//        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
//        card_title_tv.setText(mTitle);


        new  AlertDialog.Builder(getActivity().getApplicationContext())
                .setTitle("请输入邀请码" )
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(new EditText(getActivity().getApplicationContext()))
                .setPositiveButton("确定" , new MessageOk())
                .setNegativeButton("取消" , new MessageNo())
                .show();
        return v;
    }
    private class MessageOk implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            Intent intent = new Intent(getActivity().getApplicationContext(),StoreActivity.class);
            startActivity(intent);
        }
    }


    private class MessageNo implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            getActivity().finish();
        }
    }
}
