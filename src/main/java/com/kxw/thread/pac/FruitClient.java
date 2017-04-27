package com.kxw.thread.pac;

/**
 * Created by kangxiongwei on 2017/4/22.
 */
public class FruitClient {

    public static void main(String[] args) {
        Fruit fruit = new Fruit();
        for (int i = 0; i< 5; i++) {
            new Thread(new ProductFruitThread(fruit)).start();
            new Thread(new ConsumerFruitThread(fruit)).start();
        }
    }



}
