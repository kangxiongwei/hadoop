package com.kxw.thread.pac;

/**
 * 水果，生产者生产水果，消费者消费水果
 * <p>
 * Created by kangxiongwei on 2017/4/22.
 */
public class Fruit {

    private Integer fruit = 0;

    /**
     * 生产水果
     *
     * @return
     */
    public synchronized Integer productFruit() throws InterruptedException {
        while (fruit != 0) {
            this.wait();
        }
        fruit++;
        this.notify();
        return fruit;
    }

    /**
     * 消费水果
     *
     * @return
     * @throws InterruptedException
     */
    public synchronized Integer consumerFruit() throws InterruptedException {
        while (fruit == 0) {
            this.wait();
        }
        fruit--;
        this.notify();
        return fruit;
    }

}
