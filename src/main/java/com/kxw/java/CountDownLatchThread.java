package com.kxw.java;

import java.util.concurrent.CountDownLatch;

/**
 * Created by kangxiongwei on 2017/5/16.
 */
public class CountDownLatchThread {

    static final CountDownLatch LATCH = new CountDownLatch(3);


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is working");
                    }  finally {
                        LATCH.countDown();
                    }

                }
            }).start();
        }

        LATCH.await();

        System.out.println("所有线程执行完毕");
    }


}
