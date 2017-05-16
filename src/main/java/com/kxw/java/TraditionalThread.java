package com.kxw.java;

/**
 * Created by kangxiongwei3 on 2017/5/16 19:09.
 */
public class TraditionalThread {

    private static int a = 0;
    private final static TraditionalThread traditionalThread = new TraditionalThread();

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
            synchronized (traditionalThread) {
                while (a != 0) {
                    try {
                        traditionalThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                a++;
                System.out.println(Thread.currentThread().getName() + "," + a);
                traditionalThread.notify();
            }

        }
    }

    static class Thread2 implements Runnable {

        @Override
        public void run() {
            synchronized (traditionalThread) {
                while (a == 0) {
                    try {
                        traditionalThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                a--;
                System.out.println(Thread.currentThread().getName() + "," + a);
                traditionalThread.notify();
            }
        }
    }

}
