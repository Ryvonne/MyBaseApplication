package com.base.remiany.remianlibrary.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MessageHandler extends Handler {
    public static final int TOAST_MSG = 921;
    public static final int DIALOG_SHOW_MSG = 922;
    public static final int DIALOG_DISMISS_MSG = 923;
    private ProgressDialog mpd;
    Activity mContext;

    public MessageHandler(Activity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void handleMessage(Message msg) {
        Object om = msg.obj;
        switch (msg.what) {
            case TOAST_MSG:
                if(om instanceof String) {
                    Toast.makeText(mContext,(String)om,Toast.LENGTH_SHORT).show();
                }
                break;
            case DIALOG_SHOW_MSG:
                if(om instanceof String) {
                    showProgres((String) om,true);
                }
                break;
            case DIALOG_DISMISS_MSG:
                showProgres(null,false);
                break;
            default:
                break;
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
        if (mpd != null && !isFinish()) {
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

    /**
     * If Activity is finish,can't run UI Thread
     *
     * @return
     */
    private boolean isFinish() {
        return mContext.isFinishing();
    }
}
