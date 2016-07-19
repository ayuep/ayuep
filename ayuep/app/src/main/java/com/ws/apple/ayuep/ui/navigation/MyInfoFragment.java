package com.ws.apple.ayuep.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.BaseFragment;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.dao.SettingModelDao;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.SettingModel;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.StoreAccountModel;
import com.ws.apple.ayuep.proxy.StoreProxy;
import com.ws.apple.ayuep.ui.order.RegisterCustomerActivity;
import com.ws.apple.ayuep.ui.store.StoreActivity;

import cz.msebera.android.httpclient.Header;

import static com.ws.apple.ayuep.Constants.SettingKeyCurrentStoreId;

public class MyInfoFragment extends BaseFragment {

    private String mTitle;

    private EditText mEditView;

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

        View view = v.findViewById(R.id.id_go_to_my_store);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(DataCacheManager.getDataCacheManager(getActivity()).getStoreId(getActivity()))) {
                    mEditView = new EditText(getActivity());
                    new AlertDialog.Builder(getActivity())
                            .setTitle("请输入邀请码")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(mEditView)
                            .setPositiveButton("确定", new MessageOk())
                            .setNegativeButton("取消", new MessageNo())
                            .show();
                } else {
                    Intent intent = new Intent(getActivity().getApplicationContext(),StoreActivity.class);
                    startActivity(intent);
                }
            }
        });

        return v;
    }

    private class MessageOk implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String invitation = mEditView.getText().toString();
            StoreAccountModel storeAccount = new StoreAccountModel();
            storeAccount.setInvitationCode(invitation);
            showProgressDialog(false, "验证邀请码中...");
            new StoreProxy().getMyStoreWithKey(getActivity(), storeAccount, new BaseAsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        Gson gson = new Gson();

                        StoreInfoDBModel store = gson.fromJson(response, new TypeToken<StoreInfoDBModel>() {
                        }.getType());

                        new StoreInfoDBModelDao(getActivity()).insert(store);
                        DataCacheManager.getDataCacheManager(getActivity()).setStoreId(getActivity(), store.getStoreId());

                        Intent intent = new Intent(getActivity().getApplicationContext(),StoreActivity.class);
                        startActivity(intent);
                    }

                    dismissProgressDialog();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseBytes, throwable);
                    dismissProgressDialog();
                    Toast.makeText(getActivity(), "邀请码验证失败！", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private class MessageNo implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            getActivity().finish();
        }
    }
}
