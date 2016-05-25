package com.base.remiany.mybaseapplication.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务有三种方式
 * <ol>
 * <li>Timer和TimerTask</li>
 * <li>Timer和TimerTask</li>
 * <li>Timer和TimerTask</li>
 * </>
 */
public class ScheduleTask {
    public int PERIOD_TIME = 1000;

    Timer mTimer;
    TimerTask mTask;

    /**
     * ① Timer是利用相对时间判断时间间隔，当系统时间改变，则会影响使用
     * ②单线程的，无法多线程处理任务
     */
    void timerStart() {
        mTimer = new Timer();
        mTask = new TimerTask() {
            int num = 0;

            @Override
            public void run() {
            }
        };
        mTimer.scheduleAtFixedRate(mTask, 0, 1000);
    }

    public ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(1);

    /**
     * 可以多线程处理，此外，可以根据执行方法的不同决定时间是固定时间执行，还是隔段时间执行
     */
    public void scheduExecStart(Runnable task) {
        scheduExec.scheduleWithFixedDelay(task, 0, 5 * PERIOD_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 定时发送系统广播，必须配合广播接受者使用
     *
     * @param context
     */
    private void alarmStart(Context context) {
        Intent intent = new Intent("ELITOR_CLOCK");
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 5 * PERIOD_TIME, pi);
    }
}
