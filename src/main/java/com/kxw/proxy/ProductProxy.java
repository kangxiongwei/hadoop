package com.kxw.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by kangxiongwei on 2017/4/20.
 */
public class ProductProxy implements InvocationHandler {

    private Product target;

    public ProductProxy(Product target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("I am proxy!");
        return method.invoke(target, args);
    }

    public Product getProductProxy() {
        ClassLoader loader = this.target.getClass().getClassLoader();
        Class[] interfaces = this.target.getClass().getInterfaces();
        return (Product) Proxy.newProxyInstance(loader, interfaces, this);
    }


}
