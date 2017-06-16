package com.kxw.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
/**
 * Created by shaoxiangfei on 2016/4/11.
 */
public class AbstractWorker {

    protected boolean started = true;

    protected String topicName;

    protected String groupName;

    protected KafkaConsumer<String, String> consumer;

    protected static final Logger logger = LoggerFactory.getLogger(AbstractWorker.class);

    protected AbstractWorker(String topicName, String groupName) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.180.153.155:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1 * 1024 * 10);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
        this.topicName = topicName;
        this.groupName = groupName;
    }

    public static void main(String[] args) {
        AbstractWorker worker = new AbstractWorker("kxw", "grouptest");
        worker.listen();
    }



    public void listen() {
    	
        this.consumer.subscribe(Arrays.asList(this.topicName));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    ConsumerRecords<String, String> records = consumer.poll(5000);
                    try {
                        handleMessage(records);
                        consumer.commitAsync();
                    } catch (Exception e) {
                        //logger.error("consumer error, topic={},groupName={}",topicName,groupName, e);
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        //thread.setDaemon(true);
        thread.setName(this.groupName + "-" + thread.getName());
        thread.start();
        logger.info("topic=" + topicName + ", groupName=" + groupName + " started");
    }

    public void handleMessage(ConsumerRecords<String, String> records) throws Exception {
        System.out.println(records.count());
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
