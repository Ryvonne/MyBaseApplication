package com.base.remiany.remianlibrary.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.base.remiany.remianlibrary.utils.HandlerMessageManger;

public class BaseActivity extends AppCompatActivity {
    protected HandlerMessageManger mHanderManger;

    Handler mBaseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mHanderManger == null) {
                mHanderManger = new HandlerMessageManger(BaseActivity.this);
            }
            mHanderManger.handlerMessage(msg);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
