package com.example.kafkasent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyProducer {

    private final KafkaTemplate<String, MyObject> kafkaTemplate;

    @Autowired
    public MyProducer(KafkaTemplate<String, MyObject> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public void send(@RequestBody MyObject myObject) {
        kafkaTemplate.send("my-topic", myObject);
    }
}
