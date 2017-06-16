package com.kxw.kafka;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

/**
 * Kafka生产者
 *
 * Created by kangxiongwei3 on 2017/6/16 11:13.
 */
public class SpringKafkaProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/kafka/kafka-producer.xml");
        KafkaTemplate<Integer, String> kafkaTemplate = (KafkaTemplate<Integer, String>) context.getBean("kafkaTemplate");
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.sendDefault("hello world");
        SendResult<Integer, String> result = future.get();
        System.out.println(result.getProducerRecord());
    }



}
