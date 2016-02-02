package com.base.remiany.remianlibrary.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.base.remiany.remianlibrary.file.StorageHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadUtil {
    public interface OnCompleteListener {
        void onComplete(File file);
    }

    OnCompleteListener mListener;

    public OnCompleteListener getmListener() {
        return mListener;
    }

    public void setmListener(OnCompleteListener mListener) {
        this.mListener = mListener;
    }

    String TAG = "DownLoadUtil";
    ProgressDialog mProgressDialog;
    int contentLength;
    int curLength = 0;
    boolean needProgress;
    boolean isInstall = false;
    Context mContext;
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int cur = msg.what;
            double cent = (double) cur * 100 / (double) contentLength;
            setProgress((int) cent);
            Log.i(TAG, "cent:" + cent + "%" + " " + (cent >= 100));
            if (cent >= 100) {
                if (isInstall()) {
                    Object obj = msg.obj;
                    if (obj instanceof File) {
                        if (mListener != null) {
                            mListener.onComplete((File) obj);
                        }
                    }
                }

                dimissProgressDialog();
            }

        }
    };

    /**
     * 默认不显示进度条的下载管理
     *
     * @param context
     * @param handler
     */
    public DownLoadUtil(Context context, Handler handler) {
        this(context, false);
    }

    /**
     * @param context
     * @param needProgress 需要进度条
     */
    public DownLoadUtil(Context context, boolean needProgress) {
        this.mContext = context;
        this.needProgress = needProgress;
    }

    public boolean isInstall() {
        return isInstall;
    }

    public void setIsInstall(boolean isInstall) {
        this.isInstall = isInstall;
    }

    /**
     * 开始单线程下载
     *
     * @param url
     * @param filename
     */
    public void startDownload(String url, final String filename) {
        startDownload(url, filename, false);
    }

    /**
     * 开始多线程下载
     *
     * @param url
     * @param filename
     * @param needMulti
     */
    public File startDownload(String url, final String filename, boolean needMulti) {
        File file = null;
        if (needProgress) {
            showProgressDialog();
        }

        if (needMulti) {
            file = multiDownLoad(url, filename);
        } else {
            file = singleDownLoad(url, filename);
        }
        return file;
    }

    //多线程下载
    private File multiDownLoad(String url, final String filename) {
        File file = new File(StorageHelper.getAppDir(), filename);
        new DownloadTask(url, 5, file.getAbsolutePath()).start();
        return file;
    }

    //单线程下载
    private File singleDownLoad(final String surl, final String filename) {
        final File file = new File(StorageHelper.getAppDir(), filename);
        new Thread() {
            @Override
            public void run() {
                //如果目标文件已经存在，则删除。产生覆盖旧文件的效果
                if (file.exists()) {
                    file.delete();
                }
                try {
                    // 构造URL
                    URL url = new URL(surl);
                    // 打开连接
                    URLConnection con = url.openConnection();
                    //获得文件的长度
                    contentLength = con.getContentLength();
                    curLength = 0;
                    System.out.println("长度 :" + contentLength);
                    // 输入流
                    InputStream is = con.getInputStream();
                    // 1K的数据缓冲
                    byte[] bs = new byte[1024];
                    // 读取到的数据长度
                    int len;
                    // 输出的文件流
                    OutputStream os = new FileOutputStream(file);
                    // 开始读取
                    while ((len = is.read(bs)) != -1) {
                        curLength += len;
                        mhandler.sendEmptyMessage(curLength);
                        os.write(bs, 0, len);
                    }
                    // 完毕，关闭所有链接
                    os.close();
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return file;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setTitle("下载更新中……");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
    }

    public void dimissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void setProgress(int val) {
        if (mProgressDialog != null) {
            mProgressDialog.setProgress(val);
        }
    }

    /**
     * 多线程文件下载
     *
     * @author yangxiaolong
     * @2014-8-7
     */
    class DownloadTask extends Thread {
        private String downloadUrl;// 下载链接地址
        private int threadNum;// 开启的线程数
        private String filePath;// 保存文件路径地址
        private int blockSize;// 每一个线程的下载量

        public DownloadTask(String downloadUrl, int threadNum, String fileptah) {
            this.downloadUrl = downloadUrl;
            this.threadNum = threadNum;
            this.filePath = fileptah;
        }

        @Override
        public void run() {
            FileDownloadThread[] threads = new FileDownloadThread[threadNum];
            try {
                URL url = new URL(downloadUrl);
                Log.d(TAG, "download file http path:" + downloadUrl);
                URLConnection conn = url.openConnection();
                // 读取下载文件总大小
                int fileSize = conn.getContentLength();
                if (fileSize <= 0) {
                    System.out.println("读取文件失败");
                    return;
                }

                contentLength = fileSize;
                curLength = 0;

                // 计算每条线程下载的数据长度
                blockSize = (fileSize % threadNum) == 0 ? fileSize / threadNum
                        : fileSize / threadNum + 1;

                Log.d(TAG, "fileSize:" + fileSize + "  blockSize:");

                File file = new File(filePath);
                for (int i = 0; i < threads.length; i++) {
                    // 启动线程，分别下载每个线程需要下载的部分
                    threads[i] = new FileDownloadThread(url, file, blockSize,
                            (i + 1));
                    threads[i].setName("Thread:" + i);
                    threads[i].start();
                }

                boolean isfinished = false;
                int downloadedAllSize = 0;
                while (!isfinished) {
                    isfinished = true;
                    // 当前所有线程下载总量
                    downloadedAllSize = 0;
                    for (int i = 0; i < threads.length; i++) {
                        downloadedAllSize += threads[i].getDownloadLength();
                        if (!threads[i].isCompleted()) {
                            isfinished = false;
                        }
                    }
                    Message msg = new Message();
                    msg.what = downloadedAllSize;
                    msg.obj = file;
                    mhandler.sendMessage(msg);
                    Thread.sleep(1000);// 休息1秒后再读取下载进度
                }
                Log.d(TAG, " all of downloadSize:" + downloadedAllSize);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public class FileDownloadThread extends Thread {

        private final String TAG = FileDownloadThread.class.getSimpleName();

        /**
         * 当前下载是否完成
         */
        private boolean isCompleted = false;
        /**
         * 当前下载文件长度
         */
        private int downloadLength = 0;
        /**
         * 文件保存路径
         */
        private File file;
        /**
         * 文件下载路径
         */
        private URL downloadUrl;
        /**
         * 当前下载线程ID
         */
        private int threadId;
        /**
         * 线程下载数据长度
         */
        private int blockSize;

        /**
         * @param file:文件保存路径
         * @param blocksize:下载数据长度
         * @param threadId:线程ID
         */
        public FileDownloadThread(URL downloadUrl, File file, int blocksize,
                                  int threadId) {
            this.downloadUrl = downloadUrl;
            this.file = file;
            this.threadId = threadId;
            this.blockSize = blocksize;
        }

        @Override
        public void run() {

            BufferedInputStream bis = null;
            RandomAccessFile raf = null;

            try {
                URLConnection conn = downloadUrl.openConnection();
                conn.setAllowUserInteraction(true);

                int startPos = blockSize * (threadId - 1);//开始位置
                int endPos = blockSize * threadId - 1;//结束位置
                //设置当前线程下载的起点、终点
                conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                System.out.println(Thread.currentThread().getName() + "  bytes="
                        + startPos + "-" + endPos);

                byte[] buffer = new byte[1024];
                bis = new BufferedInputStream(conn.getInputStream());

                raf = new RandomAccessFile(file, "rwd");
                raf.seek(startPos);
                int len;
                while ((len = bis.read(buffer, 0, 1024)) != -1) {
                    raf.write(buffer, 0, len);
                    downloadLength += len;
                }
                isCompleted = true;
                Log.d(TAG, "current thread task has finished,all size:"
                        + downloadLength);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 线程文件是否下载完毕
         */
        public boolean isCompleted() {
            return isCompleted;
        }

        /**
         * 线程下载文件长度
         */
        public int getDownloadLength() {
            return downloadLength;
        }

    }
}
