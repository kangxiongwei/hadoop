package com.kxw.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by kangxiongwei on 2017/4/16.
 */
public class TestThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(4);
        //
        Executors.newCachedThreadPool();
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(4);
        Executors.newSingleThreadScheduledExecutor();

        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<String> future = service.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return Thread.currentThread().getName() + " is working";
                }
            });
            futureList.add(future);
        }

        for (Future<String> future : futureList) {
            System.out.println(future.get());
        }

        service.shutdown();
    }


}
