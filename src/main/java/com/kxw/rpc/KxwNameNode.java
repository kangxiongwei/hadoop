package com.kxw.rpc;

/**
 * Created by kangxiongwei on 2017/5/21.
 */
public class KxwNameNode implements NameNodeProtocol {

    @Override
    public String getNameNodeMeta(String path) {
        return path + "[BLK1, BLK2, ...]";
    }
}
