package com.ws.apple.ayuep.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ws.apple.ayuep.BaseActivity;
import com.ws.apple.ayuep.CommonAdapter;
import com.ws.apple.ayuep.R;
import com.ws.apple.ayuep.ViewHolder;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.CommentModel;
import com.ws.apple.ayuep.model.OrderModel;
import com.ws.apple.ayuep.proxy.CommentsProxy;
import com.ws.apple.ayuep.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by apple on 16/7/18.
 */

public class CommentsActivity extends BaseActivity {

    private ListView mCommentsListView;
    private List<CommentModel> mComments = new ArrayList<>();
    private String productId;
    private PtrFrameLayout mPtrFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setTitle("所有评价");
        productId = getIntent().getStringExtra("productId");
        Log.d("productId", productId);
        initView();
        showProgressDialog(true, "获取评价中...");
        refreshDate();
    }

    private void refreshListView() {
        CommentAdapter adapter = (CommentAdapter) mCommentsListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void refreshDate() {
        new CommentsProxy().getCommentsByProductId(this, productId, 1, new BaseAsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                if (!TextUtils.isEmpty(response)) {
                    Gson gson = new Gson();

                    List<CommentModel> comments = gson.fromJson(response, new TypeToken<List<CommentModel>>() {
                    }.getType());

                    mComments.clear();
                    for (CommentModel comment : comments) {
                        mComments.add(comment);
                    }
                    refreshListView();
                }

                mPtrFrameLayout.refreshComplete();
                dismissProgressDialog();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
                super.onFailure(statusCode, headers, responseBytes, throwable);
                mPtrFrameLayout.refreshComplete();
                dismissProgressDialog();
            }
        });

    }

    private void initView() {
        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshDate();
            }
        });
        mCommentsListView = (ListView) findViewById(R.id.id_comments_list_view);
        mCommentsListView.setAdapter(new CommentAdapter(this, mComments));
    }

    private class CommentAdapter extends CommonAdapter<CommentModel> {

        public CommentAdapter(Context context, List<CommentModel> datas) {
            super(context, datas);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            CommentModel comment = mComments.get(i);

            ViewHolder holder = ViewHolder.get(CommentsActivity.this, view, viewGroup, R.layout.list_comment_item);
            holder.setText(R.id.id_customer_name, comment.getCustomer().getCustomerName());
            holder.setText(R.id.id_comment_date, DateUtil.getDateString(DateUtil.dateFromServerString(comment.getCreatedTime())));
            holder.setText(R.id.id_comment_content, comment.getComment());
            TextView commentTextView = (TextView) holder.getView(R.id.id_comment_content);
            commentTextView.setVisibility(TextUtils.isEmpty(comment.getComment()) ? View.GONE : View.VISIBLE);
            RatingBar ratingBar = (RatingBar) holder.getView(R.id.id_product_rating);
            ratingBar.setRating(comment.getScore() / 20);

            return holder.getConvertView();
        }
    }
}
