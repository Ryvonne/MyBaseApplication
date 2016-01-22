package com.base.remiany.remianlibrary.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Message;

public class HandlerMessageManger {
    private ProgressDialog mpd;
    OnHanderMessageListener mListener;
    Activity mContext;

    public HandlerMessageManger(Activity mContext) {
        this.mContext = mContext;
    }

    public void handlerMessage(Message msg) {
        switch (msg.what) {
            case 1:
                break;
            default:
                break;
        }

        if (mListener != null) {
            mListener.handlerMsg(msg);
        }
    }

    public void showProgres(String msg, boolean b) {
        if (b) {
            if (msg == null) {
                msg = "正在查询，请稍后...";
            }
            showProgressDialog(msg);
        } else {
            dismissProgressDialog();
        }
    }

    private void showProgressDialog(String msg) {
        buildProgressDialog();
        mpd.setMessage(msg);
        mpd.show();
    }

    private void dismissProgressDialog() {
        if (mpd != null && !mContext.isFinishing()) {
            mpd.dismiss();
        }
    }

    /**
     * 新建Dialog
     */
    private void buildProgressDialog() {
        if (mpd == null) {
            mpd = new ProgressDialog(mContext);
            mpd.setCanceledOnTouchOutside(false);
//            mpd.setCancelable(false);//设置后，按返回无法取消进度
            mpd.setIndeterminate(true);
            mpd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
    }

    public OnHanderMessageListener getOnHanderMessageListener() {
        return mListener;
    }

    public void setOnHanderMessageListener(OnHanderMessageListener mListener) {
        this.mListener = mListener;
    }

    interface OnHanderMessageListener {
        void handlerMsg(Message msg);
    }
}
