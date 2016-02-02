package com.base.remiany.remianlibrary.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MBViewHolder {
    private static final String TAG = "MBViewHolder";
    Context mContext;
    View mHolderView;
    Map<Integer, View> mViewMap = new HashMap<>();

    public MBViewHolder(View holderView) {
        mHolderView = holderView;
    }

    public MBViewHolder(Context context, View holderView) {
        mContext = context;
        mHolderView = holderView;
    }

    public View findViewById(int rid) {
        View view = mHolderView.findViewById(rid);
        mViewMap.put(rid, view);
        return view;
    }

    public void setText(int rid, String str) {
        View tv = findViewById(rid);
        if (!(tv instanceof TextView)) {
            Log.e(TAG, "The View found by rid isn't TextView");
        }
        ((TextView) tv).setText(str);
    }

    public void setText(int rid, int strId) {
        if (mContext == null) {
            Log.e(TAG, "Context is null.Please confirm Context is not null or use the Construtor has Context param.");
        }
        String str = mContext.getString(strId);
        setText(rid, str);
    }

    public void setImageResource(int rid, int imgId) {
        View tv = findViewById(rid);
        if (!(tv instanceof ImageView)) {
            Log.e(TAG, "The View found by rid isn't ImageView");
        }
        ((ImageView) tv).setImageResource(imgId);
    }

    public void setBackgroundResource(int rid, int imgId) {
        View tv = findViewById(rid);
        tv.setBackgroundResource(imgId);
    }

    public void setBackgroundColor(int rid, int color) {
        View tv = findViewById(rid);
        if (!(tv instanceof ImageView)) {
            Log.e(TAG, "The View found by rid isn't ImageView");
        }
        ((ImageView) tv).setBackgroundColor(color);
    }

    public void setVisibility(int rid, int vis) {
        View tv = findViewById(rid);
        tv.setVisibility(vis);
    }

}
