package com.base.remiany.remianlibrary.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * APP网络请求公用的线程池，避免同时进行的线程太多拖慢速度
 *
 * 这个网络线程池最大的线程由手机端处理器的核心决定
 * 如果出现线程队列过多，处理速度过慢的情况的话，在初始化的地方可改成一下方法，将任务队列限制在100的数量上
 * <p>
 * <pre>final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
 *      executorService = new ThreadPoolExecutor(n, n,0L, TimeUnit.MILLISECONDS,queue);
 * </pre>
 */
public class HttpThreadPool {
    public final static String THREADPOOL_MANGE = "THREADPOOL_MANGE";
    private int MAX_COUNT = Runtime.getRuntime().availableProcessors();
    private static HttpThreadPool mInstance;
    ExecutorService mExecutorService;

    private int ORDER = 0;//当前生成线程的序号

    private HttpThreadPool() {
        createTreadPool();
    }

    public static HttpThreadPool getInstance() {
        if (mInstance == null) {
            mInstance = new HttpThreadPool();
        }
        return mInstance;
    }

    private void createTreadPool() {
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                return thread;
            }
        };
        mExecutorService = Executors.newFixedThreadPool(MAX_COUNT, factory);
    }

    public void run(Runnable thread) {
        Future future = mExecutorService.submit(thread);
    }
}
