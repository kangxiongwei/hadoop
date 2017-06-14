package com.kxw.flume;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件解析的Sink
 * Created by kangxiongwei3 on 2017/6/13 19:57.
 */
public class FileSink extends AbstractSink implements Configurable {

    private static final Logger logger = LoggerFactory.getLogger(FileSink.class);
    private static final String FILE_NAME = "fileName";
    private String fileName;

    @Override
    public void configure(Context context) {
        //从配置文件的fileName属性获取文件
        fileName = context.getString(FILE_NAME);
    }

    @Override
    public Status process() throws EventDeliveryException {
        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        Event event;
        txn.begin();
        while (true) {
            event = ch.take();
            if (event != null) break;
        }
        try {
            String body = new String(event.getBody());
            System.out.println("收集到数据" + body);

            String res = body + ":" + System.currentTimeMillis() + "\r\n";

            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(res.getBytes());

            fos.close();
            txn.commit();
            return Status.READY;
        } catch (Throwable th) {
            txn.rollback();
            logger.error("解析文件出错，错误信息为", th);
        } finally {
            txn.close();
        }
        return null;
    }
}
