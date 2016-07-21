
package com.ws.apple.ayuep.ui.store;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;

import static com.ws.apple.ayuep.util.StringUtil.isPhoneNumberValid;

public class StoreInfoActivity extends BaseActivity {

    final int REQUEST_CODE_CAPTURE_CAMEIA = 19;
    final int REQUEST_CODE_PICK_IMAGE = 20;

    private String mFilePath;
    private String mStoreName;
    private String mStorePhone;
    private String mStoreAddress;
    private AlertDialog dialog;
    private View dialogView;

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
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.dialog_selected_image, null, false);
    }

    public void selectStoreImage(View view) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("请选择");
            dialog.setIcon(android.R.drawable.ic_dialog_info);
            dialog.setView(dialogView);
        }

        Button fromAlbum = (Button) dialogView.findViewById(R.id.id_dialog_album);
        fromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getImageFromAlbum();
            }
        });

        Button fromCamera = (Button) dialogView.findViewById(R.id.id_dialog_camera);
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getImageFromCamera();
            }
        });
        dialog.show();
    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        }
        else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
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

        if (TextUtils.isEmpty(mFilePath)) {
            Toast.makeText(this, "请选择图片! ", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            showProgressDialog(false, "信息更新中...");
            RequestParams params = new RequestParams();
            params.put("img", new File(mFilePath));
            new FileUploadProxy().upload(this, params, new BaseAsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    if (!TextUtils.isEmpty(response)) {
                        Gson gson = new Gson();

                        JsonObject json = gson.fromJson(response, new TypeToken<JsonObject>() {
                        }.getType());

                        updateStoreInfo(BuildConfig.SERVICE_URL + "/" + json.get("filename"));
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseBytes, throwable);
                    Toast.makeText(StoreInfoActivity.this, "图片上传失败, 请稍后再试! ", Toast.LENGTH_LONG);
                    dismissProgressDialog();
                }
            });
        } catch (IOException e) {
            Log.d("AYueP", e.getMessage());
            dismissProgressDialog();
        }
    }

    private void updateStoreInfo (String filePath) {
        final StoreInfoDBModel storeInfoDBModel = new StoreInfoDBModel();
        storeInfoDBModel.setStoreId(DataCacheManager.getDataCacheManager(this).getStoreId(this));
        storeInfoDBModel.setStoreName(mStoreName);
        storeInfoDBModel.setPhoneNumber(mStorePhone);
        storeInfoDBModel.setStoreAddress(mStoreAddress);
        storeInfoDBModel.setStoreImage(filePath);

        new StoreProxy().updateStore(this, storeInfoDBModel, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                new StoreInfoDBModelDao(StoreInfoActivity.this).update(storeInfoDBModel);
                Toast.makeText(StoreInfoActivity.this, "更新成功! ", Toast.LENGTH_LONG);
                dismissProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
                Toast.makeText(StoreInfoActivity.this, "更新失败,请稍后再试! ", Toast.LENGTH_LONG);
                dismissProgressDialog();
            }
        });
    }

    // 接收选择照片返回的结果，并将他们显示在ImageView里面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            Bitmap bm = null;
            ContentResolver resolver = getContentResolver();
            try {
                bm = MediaStore.Images.Media.getBitmap(resolver, uri);
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                mFilePath = cursor.getString(column_index);
            } catch (IOException e) {
                Log.d("AYueP", e.getMessage());
            }

        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    String spath = UUID.randomUUID().toString();
                    saveImage(photo, spath);
                    mFilePath = spath;
                } else {
                    Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                //to do find the path of pic by uri
            }
        }

        if (mFilePath != null) {
            ImageView image = (ImageView) findViewById(R.id.id_store_image);
            Uri url = new Uri.Builder().build();
            image.setImageBitmap(BitmapFactory.decodeFile(mFilePath));
        }
    }

    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

