package com.ws.apple.ayuep.ui.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.proxy.ProductProxy;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import me.iwf.photopicker.PhotoPicker;

public class StoreModify extends BaseActivity {

    private final String ADD_FLAG = "add_icon";
//    private List<String> data = new ArrayList<String>();
    private ProductDBModel mProductDBModel;
    private EditText mDescription;
    private EditText mPrice;
    private TextView mType;
    private GridView mGridView;
    protected ProgressDialog mProgressDialog;
    private List<String> mImageURLS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_modify);
        InitData();
        InitView();
    }
    private void InitData()
    {
        Intent intent = getIntent();
        mProductDBModel = (ProductDBModel)intent.getSerializableExtra("data_product");
    }
    private void InitView(){
        mDescription = (EditText)findViewById(R.id.id_store_description);
        mPrice = (EditText)findViewById(R.id.id_store_price);

        if(mProductDBModel != null) {
            mDescription.setText(mProductDBModel.getProductDescription());
            mPrice.setText(String.valueOf(mProductDBModel.getPrice()));
            if(mProductDBModel.getProductType()!=null) {
                mType.setText(mProductDBModel.getProductType());
            }
            String[] urls = mProductDBModel.getImages().split(",");
            mImageURLS.clear();
            for (String url : urls) {
                mImageURLS.add(url);
            }
            mImageURLS.add(ADD_FLAG);
        } else {
            mImageURLS.add(ADD_FLAG);
        }

        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(new ImageItemAdapter(this, mImageURLS));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mImageURLS.size() - 1) {
                    selectImages();
                }
            }
        });
    }

    public void selectImages() {
        PhotoPicker.builder()
                .setPhotoCount(9 - mImageURLS.size())
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
                mImageURLS.add(ADD_FLAG);

                refreshGridView();
            }
        }
    }

    public void onSubmitClick(View view) {
        if(mPrice.getText().toString()!=null) {
            mProductDBModel.setPrice(Double.valueOf(mPrice.getText().toString()));
        }else {
            Toast.makeText(this,"请输入价格",Toast.LENGTH_SHORT).show();
        }
        if(mDescription.getText().toString()!=null) {
            mProductDBModel.setProductDescription(mDescription.getText().toString());
        }else {
            Toast.makeText(this,"请输入商品描述",Toast.LENGTH_SHORT).show();
        }
        if(mDescription.getText().toString()!=null&&mPrice.getText().toString()!=null) {
            mProductDBModel.setProductType(mType.getText().toString());
            showProgressDialog(false, "更新套系中...");
            new ProductProxy().updataProduction(this, mProductDBModel, new BaseAsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    new ProductDBModelDao(StoreModify.this).update(mProductDBModel);
                    Toast.makeText(StoreModify.this, "更新成功! ", Toast.LENGTH_LONG);
                    dismissProgressDialog();
                    setResult(RESULT_OK, new Intent());
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseBytes, throwable);
                    Toast.makeText(StoreModify.this, "更新失败! ", Toast.LENGTH_LONG);
                    dismissProgressDialog();
                }
            });
        }
    }
    public void showProgressDialog(boolean cancelable, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
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
            if (url.contains("http")) {
                ImageLoader.getInstance().displayImage(url, imageView);
            } else if (url.equals(ADD_FLAG)) {
              imageView.setImageResource(R.mipmap.add);
            } else {
                imageView.setImageBitmap(BitmapFactory.decodeFile(url));
            }

            ImageButton deleteImagebutton = (ImageButton) holder.getView(R.id.delete_markView);
            deleteImagebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mImageURLS.remove(url);
                    refreshGridView();
                }
            });

            return holder.getConvertView();
        }
    }
}

