package com.kxw.proxy;

/**
 * Created by kangxiongwei on 2017/4/20.
 */
public class ProductClient {

    public static void main(String[] args) {
        Product product = new ProductImpl();
        System.out.println(product.getProduct("original product"));
        ProductProxy proxy = new ProductProxy(product);
        Product productProxy = proxy.getProductProxy();
        String name = productProxy.getProduct("proxy product");
        System.out.println(name);
    }


}
