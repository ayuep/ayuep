package com.ws.apple.ayuep;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by apple on 16/7/10.
 */

public class ViewHolder {

    private SparseArray<View> views = new SparseArray<View>();

    private View convertView;

    public View getConvertView() {
        return convertView;
    }

    private ViewHolder(Context context, int resId) {
        this(context, null, resId);
    }

    private ViewHolder(Context context, ViewGroup parent, int resId) {
        convertView = LayoutInflater.from(context).inflate(resId, parent, false);
        convertView.setTag(this);
    }

    private ViewHolder(Context context, ViewGroup parent, View view) {
        convertView = view;
        convertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, int resId) {
        return get(context, convertView, null, resId);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int resId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, resId);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, View view) {
        if (convertView == null) {
            return new ViewHolder(context, parent, view);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public View getView(int viewId) {
        View view = views.get(viewId);

        if (view == null) {
            view = convertView.findViewById(viewId);
        }

        return view;
    }

    public ViewHolder setText(int viewId, CharSequence text) {
        TextView textView = (TextView) getView(viewId);
        textView.setText(text);

        return this;
    }

}
