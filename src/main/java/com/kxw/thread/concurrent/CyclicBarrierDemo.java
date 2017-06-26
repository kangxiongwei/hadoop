package com.kxw.thread.concurrent;

import java.util.concurrent.*;

/**
 * Created by kangxiongwei3 on 2017/6/16 18:39.
 */
public class CyclicBarrierDemo implements Runnable {

    private CyclicBarrier barrier = new CyclicBarrier(4, this);

    private Executor executor = Executors.newFixedThreadPool(4);

    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();


    public static void main(String[] args) {
        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        demo.count();
    }


    @Override
    public void run() {
        for (String key : map.keySet()) {
            System.out.println("线程执行完成" + key + "," + map.get(key));
        }
        //barrier.reset();
    }

    private void count() {
        for (int i = 0; i < 4; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String thread = Thread.currentThread().getName();
                    map.put(thread, thread);
                    System.out.println(thread + "执行完成");
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
