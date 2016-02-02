package com.base.remiany.remianlibrary.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.base.remiany.remianlibrary.handler.MsgHandler;

public class BaseActivity extends AppCompatActivity {
    protected MsgHandler mHander = new MsgHandler(BaseActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
