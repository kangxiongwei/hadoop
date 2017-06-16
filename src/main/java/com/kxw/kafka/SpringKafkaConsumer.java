package com.kxw.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.listener.MessageListener;

/**
 * Spring实现消费
 * Created by kangxiongwei3 on 2017/6/16 11:02.
 */
public class SpringKafkaConsumer implements MessageListener<Integer, String> {

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record) {
        System.out.println(record);
        String value = record.value();
        System.out.println(value);
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/kafka/kafka-consumer.xml");
        SpringKafkaConsumer consumer = (SpringKafkaConsumer) context.getBean("springKafkaConsumer");
    }


}
