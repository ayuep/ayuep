package com.ws.apple.ayuep.ui.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.dao.ProductDBModelDao;
import com.ws.apple.ayuep.entity.ProductDBModel;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.proxy.ProductProxy;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class StoreModify extends AppCompatActivity {

    private List<String> data = new ArrayList<String>();
    private ProductDBModel mProductDBModel;
    private EditText mDescription;
    private EditText mPrice;
    private ListView mTypeList;
    private TextView mType;
    protected ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_modify);
        InitView();
        InitData();
    }
    private void InitData()
    {
        data.add("儿童摄影");
        data.add("婚纱摄影");
        data.add("个人写真");
        data.add("其他");
        Intent intent = getIntent();
        mProductDBModel = (ProductDBModel)intent.getSerializableExtra("data_product");
        if(mProductDBModel != null)
        {
            mDescription.setText(mProductDBModel.getProductDescription());
            mPrice.setText(String.valueOf(mProductDBModel.getPrice()));
        }
    }
    private void InitView(){
        mDescription = (EditText)findViewById(R.id.id_store_description);
        mPrice = (EditText)findViewById(R.id.id_store_price);
        TypeAdapter adapter = new TypeAdapter(StoreModify.this,R.layout.listitem,data);
        mTypeList = (ListView) findViewById(R.id.id_store_list_view);
        mTypeList.setAdapter(adapter);
        mTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mType = (TextView)findViewById(R.id.id_store_type);
                mType.setText(data.get(i));
            }
        });
    }
    public void onSubmitClick(View view) {
        if(mPrice.getText().toString()!=null) {
            mProductDBModel.setPrice(Double.valueOf(mPrice.getText().toString()));
        }else {
            Toast.makeText(this,"请输入价格",Toast.LENGTH_SHORT).show();
        }
        if(mDescription.getText().toString()!=null) {
            mProductDBModel.setPrice(Double.valueOf(mDescription.getText().toString()));
        }else {
            Toast.makeText(this,"请输入商品描述",Toast.LENGTH_SHORT).show();
        }
        mProductDBModel.setProductType(mType.getText().toString());
        showProgressDialog(false, "更新订单状态中...");
        new ProductProxy().updataProduction(this, mProductDBModel, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                new ProductDBModelDao(StoreModify.this).updata(mProductDBModel);
                Toast.makeText(StoreModify.this, "更新成功! ", Toast.LENGTH_LONG);
                dismissProgressDialog();
                setResult(RESULT_OK,new Intent());
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
}
 class TypeAdapter extends ArrayAdapter<String>{
     private int resourcesId;
     public TypeAdapter(Context context, int textViewResourceId, List<String> data)
     {
         super(context,textViewResourceId,data);
         resourcesId =textViewResourceId;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         String type = getItem(position);
         View view ;
         if(convertView==null) {
             view =  LayoutInflater.from(getContext()).inflate(resourcesId, null);
         }else{
             view =convertView;
         }
         TextView textView = (TextView)view.findViewById(R.id.title_text);
         textView.setText(type);
         return view;
     }
 }
