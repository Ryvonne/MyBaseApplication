package com.base.remiany.mybaseapplication.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.base.remiany.mybaseapplication.R;
import com.base.remiany.mybaseapplication.presenter.MainPresenter;
import com.base.remiany.remianlibrary.view.BaseActivity;

public class MainActivity extends BaseActivity implements IMainView,View.OnClickListener{
    MainPresenter mMainPresenter;
    Button btnAdd,btnDel;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainPresenter = new MainPresenter(this);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDel = (Button) findViewById(R.id.btn_del);

        tvInfo = (TextView) findViewById(R.id.tv_info);

        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                mMainPresenter.add();
                break;
            case  R.id.btn_del:
                break;
        }
    }

    @Override
    public void add(String msg) {
        sendMsgToast(msg);
    }
}
