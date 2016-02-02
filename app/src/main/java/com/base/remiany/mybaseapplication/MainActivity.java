package com.base.remiany.mybaseapplication;

import android.os.Bundle;
import android.os.Handler;

import com.base.remiany.mybaseapplication.helper.SearchHelper;
import com.base.remiany.remianlibrary.app.BaseActivity;
import com.base.remiany.remianlibrary.http.HttpManger;
import com.base.remiany.remianlibrary.utils.Log;
import com.base.remiany.remianlibrary.utils.ScheduleTask;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private int num = 1;

    Handler mHandler = new Handler() {
    };
    HttpManger httpManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchHelper helper = new SearchHelper(this);
        httpManger = new HttpManger();
//        httpManger.runByUrl(mUrl);
        httpManger.setmListener(new HttpManger.onRusultListener() {
            @Override
            public void onRusult(int type, String srtjson) {
                Log.i(srtjson);
            }
        });

        ScheduleTask scheduleTask = new ScheduleTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String url = "http://222.76.252.18:99/kawsfw/json/pojos2json?tableName=V_CIQ_TASK_MAIN&pageNum=" + num + "&numPerPage=10&colName=ACCE_DATE&colVal=2016-02-02";
                httpManger.runByUrl(url);
                num++;
            }
        };
        scheduleTask.scheduExecStart(runnable);
    }

}
