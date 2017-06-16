package com.kxw.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * 生产者
 * Created by kangxiongwei3 on 2017/6/15 14:04.
 */
public class KafkaOrderProducer {

    private static Producer<Integer, String> producer;

    public KafkaOrderProducer() {
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        producer = new Producer<Integer, String>(new ProducerConfig(props));
    }

    public static void main(String[] args) {
        KafkaOrderProducer kafkaOrderProducer = new KafkaOrderProducer();
        KeyedMessage<Integer, String> data = new KeyedMessage<Integer, String>("kxw", "Hello My Kafka");
        producer.send(data);
        producer.close();
    }



}
