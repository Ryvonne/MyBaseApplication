package com.base.remiany.remianlibrary.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected Handler mHander = new MessageHandler(BaseActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void sendMsgToast(String str) {
        Message msg = new Message();
        msg.what = MessageHandler.TOAST_MSG;
        msg.obj = str;
        mHander.sendMessage(msg);
    }

    protected void sendMsgDialogShow(String str) {
        sendMsg(str, MessageHandler.DIALOG_SHOW_MSG);
    }

    protected void sendMsgDialogDimiss() {
        sendMsg(null,MessageHandler.DIALOG_DISMISS_MSG);
    }

    protected void sendMsg(String str,int msgType) {
        Message msg = new Message();
        msg.what = msgType;
        msg.obj = str;
        mHander.sendMessage(msg);
    }
}
