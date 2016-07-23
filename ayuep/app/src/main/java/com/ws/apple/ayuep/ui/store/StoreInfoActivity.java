
package com.ws.apple.ayuep.ui.store;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.dao.StoreInfoDBModelDao;
import com.ws.apple.ayuep.entity.StoreInfoDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.proxy.FileUploadProxy;
import com.ws.apple.ayuep.proxy.StoreProxy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.iwf.photopicker.PhotoPicker;

import static com.ws.apple.ayuep.util.StringUtil.isPhoneNumberValid;

public class StoreInfoActivity extends BaseActivity {

    final int REQUEST_CODE_CAPTURE_CAMEIA = 19;
    final int REQUEST_CODE_PICK_IMAGE = 20;

    private String mFilePath;
    private String mStoreName;
    private String mStorePhone;
    private String mStoreAddress;
    private StoreInfoDBModel mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_info);
        setTitle("店铺信息");
        initView();
    }

    private void initView() {
        mStore = new StoreInfoDBModelDao(this).queryByStoreId(DataCacheManager.getDataCacheManager(this).getStoreId(this));
        if (mStore != null) {
            EditText storeNameEdit = ((EditText) findViewById(R.id.id_store_name));
            storeNameEdit.setText(mStore.getStoreName());
            EditText storePhoneEdit = ((EditText) findViewById(R.id.id_store_phone));
            storePhoneEdit.setText(mStore.getPhoneNumber());
            EditText storeAddressEdit = ((EditText) findViewById(R.id.id_store_address));
            storeAddressEdit.setText(mStore.getStoreAddress());
            if (!TextUtils.isEmpty(mStore.getStoreImage())) {
                ImageView image = (ImageView) findViewById(R.id.id_store_image);
                ImageLoader.getInstance().displayImage(mStore.getStoreImage(), image);
            }
        }

        Button submitButton = (Button) findViewById(R.id.id_sunmit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitStoreInfo();
            }
        });
    }

    public void selectStoreImage(View view) {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    public void submitStoreInfo() {
        mStoreName = ((EditText) findViewById(R.id.id_store_name)).getText().toString();
        mStorePhone = ((EditText) findViewById(R.id.id_store_phone)).getText().toString();
        mStoreAddress = ((EditText) findViewById(R.id.id_store_address)).getText().toString();
        if (TextUtils.isEmpty(mStoreName)) {
            Toast.makeText(this, "请输入店铺名! ", Toast.LENGTH_LONG).show();
            return;
        }

        if (!isPhoneNumberValid(mStorePhone)) {
            Toast.makeText(this, "请输入正确的电话号码! ", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(mStoreAddress)) {
            Toast.makeText(this, "请输入地址! ", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(mFilePath) && TextUtils.isEmpty(mStore.getStoreImage())) {
            Toast.makeText(this, "请选择图片! ", Toast.LENGTH_LONG).show();
            return;
        }

        if (!TextUtils.isEmpty(mFilePath)) {
            try {
                showProgressDialog(false, "信息更新中...");
                RequestParams params = new RequestParams();
                params.put("img", new File(mFilePath));
                new FileUploadProxy().upload(this, params, new BaseAsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d("success", response);
                        if (!TextUtils.isEmpty(response)) {
                            Gson gson = new Gson();

                            JsonObject json = gson.fromJson(response, new TypeToken<JsonObject>() {
                            }.getType());

                            updateStoreInfo(BuildConfig.SERVICE_URL + "/images/" + json.get("filename").getAsString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseBytes, throwable);
                        Toast.makeText(StoreInfoActivity.this, "图片上传失败, 请稍后再试! ", Toast.LENGTH_LONG).show();
                        dismissProgressDialog();
                    }
                });
            } catch (IOException e) {
                Log.d("AYueP", e.getMessage());
                dismissProgressDialog();
            }
        } else {
            showProgressDialog(false, "信息更新中...");
            updateStoreInfo(null);
        }
    }

    private void updateStoreInfo (String filePath) {
        mStore.setStoreName(mStoreName);
        mStore.setPhoneNumber(mStorePhone);
        mStore.setStoreAddress(mStoreAddress);
        if (!TextUtils.isEmpty(filePath)) {
            mStore.setStoreImage(filePath);
        }

        new StoreProxy().updateStore(this, mStore, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                new StoreInfoDBModelDao(StoreInfoActivity.this).update(mStore);
                dismissProgressDialog();
                Toast.makeText(StoreInfoActivity.this, "更新成功! ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
                dismissProgressDialog();
                Toast.makeText(StoreInfoActivity.this, "更新失败,请稍后再试! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos.size() >= 1) {
                    mFilePath = photos.get(0);
                    ImageView image = (ImageView) findViewById(R.id.id_store_image);
                    Uri url = new Uri.Builder().build();
                    image.setImageBitmap(BitmapFactory.decodeFile(mFilePath));
                }
            }
        }
    }
}

