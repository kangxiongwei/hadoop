package com.kxw.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kangxiongwei3 on 2017/5/16 19:30.
 */
public class LockThread {

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static int a = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread1 thread1 = new Thread1();
            Thread2 thread2 = new Thread2();
            new Thread(thread1).start();
            new Thread(thread2).start();
        }
    }

    static class Thread1 implements Runnable {

        @Override
        public void run() {
            lock.lock();
            while (a != 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            a++;
            System.out.println(Thread.currentThread().getName() + "," + a);
            condition.signal();
            lock.unlock();
        }
    }

    static class Thread2 implements Runnable {

        @Override
        public void run() {
            lock.lock();
            while (a == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            a--;
            System.out.println(Thread.currentThread().getName() + "," + a);
            condition.signal();
            lock.unlock();
        }
    }


}
