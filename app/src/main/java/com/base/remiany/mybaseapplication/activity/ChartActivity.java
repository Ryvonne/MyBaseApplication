package com.base.remiany.mybaseapplication.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.base.remiany.mybaseapplication.R;
import com.base.remiany.mybaseapplication.chart.ChartGridViewAdapter;
import com.base.remiany.mybaseapplication.view.MBViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {
    GridView mGridView;
    ChartGridViewAdapter<Integer> mChartAdapter;
    List<Integer> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mList.add(1);
        mList.add(2);
        mList.add(3);
        mList.add(4);
        mList.add(5);
        mList.add(6);

        mGridView = (GridView) findViewById(R.id.gv_table);

        mChartAdapter = new ChartGridViewAdapter<Integer>(ChartActivity.this,mList,R.layout.item_drawer_title_list) {
            @Override
            public void converTitle(String title, MBViewHolder holder) {
                holder.setText(R.id.tv_title,title);
            }

            @Override
            public void converColumsName(String title, MBViewHolder holder) {
                holder.setText(R.id.tv_title,title);
            }

            @Override
            public void converValue(Integer info, MBViewHolder holder) {
                holder.setText(R.id.tv_title,""+info);
            }

            @Override
            public void converNoValue(Integer info, MBViewHolder holder) {
                holder.setText(R.id.tv_title,"");
            }
        };
        mChartAdapter.setColumnTitles(new String[] {"","col1","col2","col3"}).setRowTitles(new String[] {"first","second","thrid"}).setGridView(mGridView);
    }

}
