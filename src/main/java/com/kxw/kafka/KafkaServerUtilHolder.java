package com.kxw.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaServerUtilHolder {


    private static final Logger logger = LoggerFactory.getLogger(KafkaServerUtilHolder.class);
    private static KafkaProducer<String, String> producer;

    static {
        try {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.180.153.155:9092");
            props.put(ProducerConfig.ACKS_CONFIG, "1");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.CLIENT_ID_CONFIG, "test");
            producer = new KafkaProducer<String, String>(props);
        } catch (Exception e) {
            logger.error("kafka config error!", e);
        }
    }

    public static void sendMessage(final String topic, final String key, final String value) {
        try {
            KafkaServerUtilHolder.producer.send(new ProducerRecord<String, String>(topic, key, value));
            logger.info("kafkaProducerStatistic-topic:[{}],key:[{}]", topic, key);//观查一段时间以后会去掉。
            System.out.println("send successfully");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("send kafka message error!", e);
            logger.error(String.format("KafkaServerUtil sendMessage error with producer:%s,topic:%s,key:%s,value:%s", KafkaServerUtilHolder.producer, topic, key, value));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            sendMessage("kxw", "test", "this is test");
            System.out.println("===========================");
            Thread.sleep(2000);
        }
    }




}