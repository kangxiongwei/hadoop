package com.kxw.proxy;

/**
 * Created by kangxiongwei on 2017/4/20.
 */
public class ProductImpl implements Product {
    @Override
    public String getProduct(String name) {
        return "I am " + name;
    }
}
