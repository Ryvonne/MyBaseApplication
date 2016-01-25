package com.base.remiany.mybaseapplication;

import android.os.Bundle;

import com.base.remiany.mybaseapplication.helper.SearchHelper;
import com.base.remiany.remianlibrary.app.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchHelper helper = new SearchHelper(this);


    }

}
