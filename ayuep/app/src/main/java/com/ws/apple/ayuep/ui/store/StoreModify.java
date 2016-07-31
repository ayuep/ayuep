package com.ws.apple.ayuep.ui.store;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.AYuePApplication;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.DataCacheManager;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.ProductTypeModel;
import com.ws.apple.ayuep.proxy.FileUploadProxy;
import com.ws.apple.ayuep.proxy.ProductProxy;
import com.ws.apple.ayuep.util.ImageUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.iwf.photopicker.PhotoPicker;

public class StoreModify extends BaseActivity {

    private final String ADD_FLAG = "add_icon";
    private ProductDBModel mProductDBModel;
    private EditText mDescription;
    private EditText mPrice;
    private String currentSelectedType;
    private GridView mGridView;
    private List<ProductTypeModel> mProductTypes;
    private List<String> mImageURLS = new ArrayList<>();
    private ListView mDialogView;
    private AlertDialog mDialog;
    private TextView mType;
    private int uploadImageCount = 0;
    private RequestManager mGlide;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_modify);
        mGlide = Glide.with(this);
        InitData();
        InitView();
    }
    private void InitData()
    {
        mProductTypes = DataCacheManager.getDataCacheManager(this).getConfiguration().getProductTypes();
        Intent intent = getIntent();
        mProductDBModel = (ProductDBModel)intent.getSerializableExtra("data_product");
    }
    private void InitView(){
        mSubmitButton = (Button) findViewById(R.id.id_submit);
        mDescription = (EditText)findViewById(R.id.id_store_description);
        mPrice = (EditText)findViewById(R.id.id_store_price);
        mType = (TextView) findViewById(R.id.id_store_type);

        if(mProductDBModel != null) {
            mDescription.setText(mProductDBModel.getProductDescription());
            mPrice.setText(String.valueOf(mProductDBModel.getPrice()));
            if(mProductDBModel.getProductType()!=null) {
                mType.setText(mProductDBModel.getProductType());
                currentSelectedType = mProductDBModel.getProductType();
            }
            String[] urls = mProductDBModel.getImages().split(",");
            mImageURLS.clear();
            for (String url : urls) {
                mImageURLS.add(url);
            }
            addAddItem();
        } else {
            addAddItem();
        }

        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(new ImageItemAdapter(this, mImageURLS));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mImageURLS.get(i).equals(ADD_FLAG)) {
                    selectImages();
                }
            }
        });

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogView = (ListView) inflater.inflate(R.layout.dialog_selected_type, null);
        mDialogView.setAdapter(new ProductTypeItemAdapter(this, mProductTypes));
        mDialogView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentSelectedType = mProductTypes.get(i).getProductTypeName();
                mType.setText(currentSelectedType);
                mDialog.dismiss();
            }
        });
        mDialog = new AlertDialog.Builder(this).create();
        mDialog.setMessage("请选择套系类型");
        mDialog.setView(mDialogView);
    }

    private void addAddItem() {
        if (this.mImageURLS.size() < 9) {
            this.mImageURLS.add(ADD_FLAG);
        }
    }

    public void selectProductType(View view) {
        mDialog.show();
    }

    public void selectImages() {
        PhotoPicker.builder()
                .setPhotoCount(10 - mImageURLS.size())
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
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
                mImageURLS.remove(mImageURLS.size() - 1);
                for (String photo: photos) {
                    mImageURLS.add(photo);
                }
                addAddItem();

                refreshGridView();
            }
        }
    }

    public void onSubmitClick(View view) {
        if (mImageURLS.size() <= 1) {
            Toast.makeText(this,"请选择套系图片",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(mDescription.getText().toString())) {
            Toast.makeText(this,"请输入商品描述",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(mPrice.getText().toString())) {
            Toast.makeText(this,"请输入价格",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mType.getText().toString())) {
            Toast.makeText(this,"请选择套系类型",Toast.LENGTH_SHORT).show();
            return;
        }

        mSubmitButton.setEnabled(false);
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("信息更新中...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        } else {
            mProgressDialog.setMessage("信息更新中...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        if (mProductDBModel == null) {
            mProductDBModel = new ProductDBModel();
        }

        mProductDBModel.setPrice(Double.valueOf(mPrice.getText().toString()));
        mProductDBModel.setProductDescription(mDescription.getText().toString());
        mProductDBModel.setProductType(currentSelectedType);

        if (mImageURLS.get(mImageURLS.size() - 1).equals(ADD_FLAG)) {
            mImageURLS.remove(mImageURLS.size() - 1);
        }

        for (int index = 0; index < mImageURLS.size(); index ++) {
            String imageUrl = mImageURLS.get(index);
            if (!imageUrl.contains("http")) {
                uploadImageCount ++;
                try {
                    File file = getFilesDir();
                    String target = file.getPath() + "compressPic.jpg";
                    ImageUtil.compressBmpToFile(imageUrl, target);
                    RequestParams params = new RequestParams();
                    params.put("img", new File(target));
                    new FileUploadProxy().upload(this, params, new UploadImageResponseHandler(index));
                } catch (IOException e) {
                    Log.d("AYueP", e.getMessage());
                    dismissProgressDialog();
                }
            }
        }
        if (uploadImageCount == 0) {
            updateProduct();
        }
    }

    private void updateProduct() {
        String images = mImageURLS.get(0);
        for (int index = 1; index < mImageURLS.size(); index ++) {
            images = images + "," + mImageURLS.get(index);
        }

        mProductDBModel.setStoreId(DataCacheManager.getDataCacheManager(this).getStoreId(this));
        mProductDBModel.setImages(images);
        new ProductProxy().updataProduction(this, mProductDBModel, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();

                    ProductDBModel product = gson.fromJson(response, new TypeToken<ProductDBModel>(){}.getType());
                    mProductDBModel.setProductId(product.getProductId());
                    mProductDBModel.setProductType(product.getProductType());
                    mProductDBModel.setPrice(product.getPrice());
                    mProductDBModel.setImages(product.getImages());
                    mProductDBModel.setProductDescription(product.getProductDescription());
                    new ProductDBModelDao(StoreModify.this).update(mProductDBModel);
                    Toast.makeText(AYuePApplication.getmApplicationContext(), "更新成功! ", Toast.LENGTH_LONG).show();
                    dismissProgressDialog();
                    setResult(RESULT_OK, new Intent());
                    finish();
                } else {
                    Toast.makeText(StoreModify.this, "更新失败! ", Toast.LENGTH_LONG).show();
                    dismissProgressDialog();
                    mSubmitButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
                Toast.makeText(StoreModify.this, "更新失败! ", Toast.LENGTH_LONG).show();
                dismissProgressDialog();
                mSubmitButton.setEnabled(true);
            }
        });
    }

    private void refreshGridView() {
        ImageItemAdapter adapter = (ImageItemAdapter) mGridView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private class ImageItemAdapter extends CommonAdapter<String> {
        public ImageItemAdapter(Context context, List<String> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = ViewHolder.get(StoreModify.this, view, viewGroup, R.layout.grid_view);
            final String url = datas.get(i);
            ImageView imageView = (ImageView) holder.getView(R.id.img);

            ImageButton deleteImagebutton = (ImageButton) holder.getView(R.id.delete_markView);
            deleteImagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mImageURLS.remove(url);
                    refreshGridView();
                }
            });

            if (url.contains("http")) {
                ImageLoader.getInstance().displayImage(url, imageView);
                deleteImagebutton.setVisibility(View.VISIBLE);
            } else if (url.equals(ADD_FLAG)) {
                imageView.setImageResource(R.mipmap.add);
                deleteImagebutton.setVisibility(View.GONE);
            } else {
                Uri uri = Uri.fromFile(new File(url));
                mGlide.load(uri)
                        .thumbnail(0.1f)
                        .into(imageView);
                deleteImagebutton.setVisibility(View.VISIBLE);
            }

            return holder.getConvertView();
        }
    }

    private class ProductTypeItemAdapter extends CommonAdapter<ProductTypeModel> {

        public ProductTypeItemAdapter(Context context, List<ProductTypeModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ProductTypeModel productType = datas.get(i);
            ViewHolder holder = ViewHolder.get(StoreModify.this, view, viewGroup, R.layout.list_selected_type_item);

            holder.setText(R.id.id_type_name, productType.getProductTypeName());

            return holder.getConvertView();
        }
    }

    private class UploadImageResponseHandler extends BaseAsyncHttpResponseHandler {

        private int indexOfImage;

        public UploadImageResponseHandler(int index) {
            super();
            this.indexOfImage = index;
        }

        @Override
        public void onSuccess(String response) {
            Log.d("success", response);
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();

                JsonObject json = gson.fromJson(response, new TypeToken<JsonObject>() {
                }.getType());

                mImageURLS.set(this.indexOfImage, BuildConfig.SERVICE_URL + "/images/" + json.get("filename").getAsString());
                Log.d("success", Integer.toString(this.indexOfImage));
            }
            uploadImageCount --;
            if (uploadImageCount == 0) {
                updateProduct();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
            super.onFailure(statusCode, headers, responseBytes, throwable);
            uploadImageCount --;
            Toast.makeText(StoreModify.this, "图片上传失败, 请稍后再试! ", Toast.LENGTH_LONG).show();
            dismissProgressDialog();
            mSubmitButton.setEnabled(true);
        }
    }
}

