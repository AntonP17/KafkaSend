package com.example.kafkasent;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyConsumer {

    @KafkaListener(topics = "my-topic")
    public void listen(MyObject myObject) {
        System.out.println("Received: " + myObject.getName() + " " + myObject.getCount());
    }

}
