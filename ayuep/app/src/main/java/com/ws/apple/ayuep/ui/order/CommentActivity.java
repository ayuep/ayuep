package com.ws.apple.ayuep.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ws.apple.ayuep.AYuePApplication;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.CommentModel;
import com.ws.apple.ayuep.model.CustomerModel;
import com.ws.apple.ayuep.model.OrderModel;
import com.ws.apple.ayuep.proxy.CommentsProxy;

import cz.msebera.android.httpclient.Header;

/**
 * Created by apple on 16/7/18.
 */

public class CommentActivity extends BaseActivity {
    private OrderModel order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setTitle("评价");
        order = (OrderModel) getIntent().getSerializableExtra("order");
    }

    public void submitComment(View view) {
        EditText commentEditText = (EditText) findViewById(R.id.id_comment);
        String commentString = commentEditText.getText().toString();
        if (commentString.length() > 200) {
            Toast.makeText(this, "评价必须在200字内", Toast.LENGTH_LONG).show();
            return;
        }

        RatingBar ratingBar = (RatingBar) findViewById(R.id.id_rating);
        CommentModel comment = new CommentModel();
        CustomerModel customer = new CustomerModel();
        customer.setCustomerId(order.getCustomerId());
        comment.setCustomer(customer);
        comment.setComment(commentString);
        comment.setOrderId(order.getOrderId());
        comment.setScore((int) (ratingBar.getRating() * 20));
        comment.setProduct(order.getProduct());
        showProgressDialog(false, "提交评价中...");
        new CommentsProxy().submitComment(this, comment, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                dismissProgressDialog();
                Toast.makeText(AYuePApplication.getmApplicationContext(), "评价成功! ", Toast.LENGTH_LONG);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
                dismissProgressDialog();
            }
        });
    }
}
