package com.kxw.thread.pac;

import java.util.Random;

/**
 * Created by kangxiongwei on 2017/4/22.
 */
public class ConsumerFruitThread implements Runnable {

    private Fruit fruit;

    public ConsumerFruitThread(Fruit fruit) {
        this.fruit = fruit;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(5) * 100);
            Integer remain = fruit.consumerFruit();
            System.out.println("消费者消费，剩余" + remain + "个水果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
