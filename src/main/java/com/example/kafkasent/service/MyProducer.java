package com.example.kafkasent.service;

import com.example.kafkasent.model.MyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyProducer {

    private final KafkaTemplate<String, MyObject> kafkaTemplate;

    @Autowired
    public MyProducer(KafkaTemplate<String, MyObject> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendObject(MyObject myObject) {
        kafkaTemplate.send("my-topic", myObject);
    }

    @KafkaListener(topics = "finalTopic", groupId = "finalGroup", containerFactory = "finalGroupKafkaListenerContainerFactory")
    public void listen(MyObject myObject) {
        System.out.println("first PRODUCER listen change object by CONSUMER " + myObject.getName() + " " + myObject.getCount());
    }
}
