package com.kxw.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.protocol.NamenodeProtocol;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;

/**
 * RPC服务端
 *
 * Created by kangxiongwei on 2017/5/21.
 */
public class RPCServerService {

    public static void main(String[] args) throws IOException {
        RPC.Builder builder = new RPC.Builder(new Configuration());
        builder.setBindAddress("localhost").setPort(8888).setProtocol(NamenodeProtocol.class)
                .setInstance(new KxwNameNode());
        RPC.Server server = builder.build();
        server.start();
    }



}
