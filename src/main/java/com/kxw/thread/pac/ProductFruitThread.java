package com.kxw.thread.pac;

import java.util.Random;

/**
 * Created by kangxiongwei on 2017/4/22.
 */
public class ProductFruitThread implements Runnable {

    private Fruit fruit;

    public ProductFruitThread(Fruit fruit) {
        this.fruit = fruit;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10) * 100);
            Integer product = this.fruit.productFruit();
            System.out.println("生产者生产，库存" + product + "个水果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
