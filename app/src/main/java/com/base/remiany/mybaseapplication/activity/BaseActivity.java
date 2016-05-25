package com.base.remiany.mybaseapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.base.remiany.mybaseapplication.app.MyApplication;
import com.base.remiany.mybaseapplication.view.MessageHandler;

public class BaseActivity extends AppCompatActivity {
    protected Handler mHander = new MessageHandler(BaseActivity.this) {};
    private MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = MyApplication.instance();
        myApplication.addActivity(BaseActivity.this.getClass().getSimpleName(),this);
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
