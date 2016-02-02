package com.base.remiany.remianlibrary.http;


import android.os.Handler;

import com.base.remiany.remianlibrary.utils.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;


public class HttpManger {
    public final static String UTF_8 = "UTF-8";

    public static final int READ_TIMEOUT = 1000 * 10;
    public static final int CONNECT_TIMEOUT = 1000 * 10;

    onRusultListener mListener;
    /**
     * 线程池，只有唯一的实例
     */
    static HttpThreadPool mPool = HttpThreadPool.getInstance();


    public void runByUrl(final String surl) {
        runByUrl(0, surl);
    }

    public void runByUrl(final int type, final String surl) {
        mPool.run(new Runnable() {
            @Override
            public void run() {
                get(type, surl);
            }
        });
    }

    private void get(String surl) {
        get(0, surl);
    }

    private void get(int type, String surl) {
        try {
            URL url = new URL(surl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
//            conn.setRequestMethod("POST");
            byte[] bytes = readStream(conn.getInputStream());
            String str = new String(bytes);
            if (mListener != null) {
                mListener.onRusult(0, str);
            }
            Log.i(surl + "读取完毕");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {//超时异常
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post(int type, String surl) {
        try {
            URL url = new URL(surl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestMethod("POST");
            byte[] bytes = readStream(conn.getInputStream());
            String str = new String(bytes);
            if (mListener != null) {
                mListener.onRusult(0, str);
            }
            Log.i(surl + "读取完毕");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {//超时异常
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void post(String path, String params) {
        try {
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(path);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            // Post请求必须设置允许输出
            urlConn.setDoOutput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            urlConn.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencode");
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                byte[] data = readStream(urlConn.getInputStream());
            } else {
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {//超时异常
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    private static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    private static void writeStream(byte[] bytes, OutputStream outputStream) throws IOException {
        outputStream.write(bytes);
        outputStream.flush();
    }

    public interface onRusultListener {
        void onRusult(int type, String srtjson);
    }

    public onRusultListener getmListener() {
        return mListener;
    }

    public void setmListener(onRusultListener mListener) {
        this.mListener = mListener;
    }
}
