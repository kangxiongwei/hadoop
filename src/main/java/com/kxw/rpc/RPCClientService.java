package com.kxw.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * RPC客户端
 * Created by kangxiongwei on 2017/5/21.
 */
public class RPCClientService {

    public static void main(String[] args) throws IOException {
        NameNodeProtocol protocol = RPC.getProxy(NameNodeProtocol.class, 1L,
                new InetSocketAddress("localhost",8888), new Configuration());
        System.out.println(protocol.getNameNodeMeta("/stream"));
    }




}
