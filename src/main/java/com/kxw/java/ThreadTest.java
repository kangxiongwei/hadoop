package com.kxw.java;

import java.util.concurrent.*;

/**
 * Created by kangxiongwei on 2017/5/15.
 */
public class ThreadTest {

    public static void main(String[] args) {
        ExecutorService es1 = Executors.newFixedThreadPool(5);
        ExecutorService es2 = Executors.newSingleThreadExecutor();
        ExecutorService es3 = Executors.newCachedThreadPool();
        ExecutorService es4 = Executors.newScheduledThreadPool(5);
        ExecutorService es5 = Executors.newSingleThreadScheduledExecutor();

        es1.execute(() -> System.out.println("hello thread pool"));




        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }



}
