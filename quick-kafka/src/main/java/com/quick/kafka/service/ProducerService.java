package com.quick.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author vector
 * @date: 2019/7/18 0018 11:11
 */
@Service
@Slf4j
public class ProducerService {


    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public void sendMessage(String topic, String data) {
        log.info("kafka send msg start");
        ListenableFuture<SendResult<Integer, String>> send = kafkaTemplate.send(topic, data);
        send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("kafka send msg err, ex = {}, topic = {}, data = {}", throwable, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
                log.info("kafka send msg success, topic = {}, data = {}", topic, data);
            }
        });

        log.info("kafka send msg end");
    }
}
