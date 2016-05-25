package com.base.remiany.mybaseapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.base.remiany.mybaseapplication.R;
import com.base.remiany.mybaseapplication.model.ActivityInfo;
import com.base.remiany.mybaseapplication.presenter.MainPresenter;
import com.base.remiany.mybaseapplication.view.MBAdapter;
import com.base.remiany.mybaseapplication.view.MBViewHolder;

import java.util.List;

public class MainActivity extends BaseActivity implements IMainView{
    MainPresenter mMainPresenter;
    ListView mLstDrawer;
    MBAdapter<ActivityInfo> mMBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLstDrawer = (ListView) findViewById(R.id.lst_title_list);

        mMainPresenter = new MainPresenter(this);
        mMainPresenter.createDrawer();//create Drawer
    }

    @Override
    public void createDrawer(final List<ActivityInfo> list) {
        mMBAdapter = new MBAdapter<ActivityInfo>(MainActivity.this,list,R.layout.item_drawer_title_list) {
            @Override
            protected void convert(ActivityInfo info, int position, MBViewHolder holder) {
                holder.setText(R.id.tv_title,getResources().getString(info.getSID()));
            }
        };

        mLstDrawer.setAdapter(mMBAdapter);
        mLstDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,list.get(position).getmActicity());
                startActivity(intent);
            }
        });
    }
}
