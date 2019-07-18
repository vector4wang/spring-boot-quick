package com.quick.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author vector
 * @date: 2019/7/18 0018 11:12
 */
@Service
@Slf4j
public class ConsumerService {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "#{topicGroupId}")
    void processMessage(ConsumerRecord<Integer, String> record) {
        log.info("kafka process msg start");
        log.info("processMessage, topic = {}, msg={}", record.topic(), record.value());


        log.info("kafka process msg end");
    }

}
