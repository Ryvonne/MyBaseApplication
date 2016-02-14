package com.base.remiany.remianlibrary.app.handler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MsgHandler extends Handler {
    Activity mContext;

    public MsgHandler(Activity mContext) {
        this.mContext = mContext;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }


    public void ToastShort(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void ToastLong(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
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
