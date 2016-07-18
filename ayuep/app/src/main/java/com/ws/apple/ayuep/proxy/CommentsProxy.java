package com.ws.apple.ayuep.proxy;

import android.content.Context;

import com.google.gson.Gson;
import com.ws.apple.ayuep.BuildConfig;
import com.ws.apple.ayuep.handler.BaseAsyncHttpResponseHandler;
import com.ws.apple.ayuep.model.CommentModel;
import com.ws.apple.ayuep.util.HttpUtil;

/**
 * Created by apple on 16/7/18.
 */

public class CommentsProxy {
    public void submitComment(Context context, CommentModel comment, BaseAsyncHttpResponseHandler handler) {
        String url = BuildConfig.SERVICE_URL + "/api/comments";

        Gson gson = new Gson();

        HttpUtil.post(context, url, gson.toJson(comment), handler);
    }
}
