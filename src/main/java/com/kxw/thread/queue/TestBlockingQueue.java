package com.kxw.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by kangxiongwei on 2017/4/16.
 */
public class TestBlockingQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        //queue = new ArrayBlockingQueue<String>(10);

        queue.put("abc");
        System.out.println(queue.take());
    }



}
