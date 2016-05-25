package com.base.remiany.mybaseapplication.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 处理退出时操作的方法
 * <p/>
 * 使用方法
 * 在Activity页面重写dispatchKeyEvent方法，return ExitHelper.exit(event);
 *
 * @author remiany
 */
public class ExitHelper {

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    public static boolean exit(Activity context, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(context); // 调用双击退出函数
            return true;
        }
        return false;
    }

    /**
     * 连续按两次才退出的方法
     *
     * @param context
     */
    public static void exitBy2Click(Activity context) {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(context, "连续按两下推出程序",
                    Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            context.finish();
        }
    }


    /**
     * 弹出提示框的方法
     *
     * @param context
     * @param msg
     */
    private void showDialog(final Context context, String msg) {
        Dialog builder = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        })
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).create();

        builder.show();
    }
}
