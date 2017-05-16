package com.kxw.java;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by kangxiongwei on 2017/5/16.
 */
public class CyclicBarrierThread {

    static final CyclicBarrier BARRIER = new CyclicBarrier(5);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Thread1()).start();
        }
    }


    static class Thread1 implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(1000));
                System.out.println(Thread.currentThread().getName() + " is waiting");
                BARRIER.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is working");
        }
    }


}
