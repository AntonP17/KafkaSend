package com.example.kafkasent.service;

import com.example.kafkasent.model.MyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyConsumer {

    private final KafkaTemplate<String, MyObject> kafkaTemplate;

    @Autowired
    public MyConsumer(KafkaTemplate<String, MyObject> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "my-topic",groupId = "myGroup", containerFactory = "myGroupKafkaListenerContainerFactory")
    public void listen(MyObject myObject) throws InterruptedException {


        System.out.println("CONSUMER listen object  " + myObject.getName() + " " + myObject.getCount());

        Thread.sleep(5000);

        myObject.setName("FINAL");

        kafkaTemplate.send("finalTopic", myObject);
    }

}
