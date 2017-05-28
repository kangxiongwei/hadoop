package com.kxw.hdfs;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 * HDFS客户端编程
 * Created by kangxiongwei on 2017/5/20.
 */
public class HDFSClient {

    private Configuration config;
    private FileSystem fs = null;

    /**
     * 初始化操作
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Before
    public void setUp() throws IOException, InterruptedException {
        config = new Configuration();
        fs = FileSystem.get(URI.create("hdfs://mini1:9000"), config, "hadoop");
        System.out.println("从集群获取FS成功");
    }

    /**
     * 获取配置信息
     */
    @Test
    public void getConfig() {
        Iterator<Map.Entry<String, String>> iterator = config.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (!"dfs.replication".equals(entry.getKey())) continue;
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * 上传文件
     */
    @Test
    public void upload() throws IOException {
        String path = "/Users/kangxiongwei/hdfsclient.csv";
        Path src = new Path(path);
        Path dest = new Path("/code.csv");
        fs.copyFromLocalFile(src, dest);
    }

    /**
     * 查看文件
     */
    @Test
    public void list() throws IOException {
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(new Path("/"), true);
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            System.out.println("Name: " + fileStatus.getPath().getName());
            System.out.println("Permission: " + fileStatus.getPermission());
            System.out.println("Owner: " + fileStatus.getOwner());
            System.out.println("BlockSize: " + fileStatus.getBlockSize());
            System.out.println("Len: " + fileStatus.getLen());
        }
    }

    /**
     * 通过数据流方式上传文件
     */
    @Test
    public void uploadByStream() throws IOException {
        FSDataOutputStream out = fs.create(new Path("/stream"));
        FileInputStream in = new FileInputStream("/Users/kangxiongwei/hdfsclient.csv");
        IOUtils.copy(in, out);
    }

    /**
     * 通过数据流方式下载文件
     */
    @Test
    public void downloadByStream() throws IOException {
        FSDataInputStream in = fs.open(new Path("/stream"));

    }


    /**
     * 释放资源
     *
     * @throws IOException
     */
    @After
    public void tearDown() throws IOException {
        fs.close();
    }


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        //System.setProperty("HADOOP_USER_NAME", "hadoop");

        Configuration configuration = new Configuration();
        //configuration.set("dfs.defaultFS", "hdfs://mini1:9000");
        //FileSystem fs = FileSystem.get(configuration);
        FileSystem fs = FileSystem.get(URI.create("hdfs://mini1:9000"), configuration, "hadoop");
        System.out.println(fs);

        String path = "/Users/kangxiongwei/hdfsclient.csv";
        Path src = new Path(path);
        Path dest = new Path("/code.csv");

        fs.copyFromLocalFile(src, dest);
        fs.close();
    }

}
