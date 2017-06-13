package com.kxw.rpc;

import java.io.Serializable;

/**
 * Created by kangxiongwei on 2017/5/21.
 */
public interface NameNodeProtocol extends Serializable {

    /**
     * 获取元数据的方法
     *
     * @param path
     * @return
     */
    String getNameNodeMeta(String path);


}
