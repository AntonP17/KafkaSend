package com.example.kafkasent.service;

import com.example.kafkasent.model.MyObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyConsumer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MyConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "my-topic",groupId = "myGroup", containerFactory = "myGroupKafkaListenerContainerFactory")
    public void listen(String jsonMessage) throws InterruptedException, JsonProcessingException {


        System.out.println("CONSUMER listen object  " + jsonMessage);

        MyObject myObject = objectMapper.readValue(jsonMessage, MyObject.class);

        System.out.println("CONUMER maping object " + myObject.getName() + " " + myObject.getCount());

        Thread.sleep(5000);

        myObject.setName("FINAL");

        System.out.println("CONSUMER change object " + myObject.getName() + " " + myObject.getCount());

        String jsonObject = objectMapper.writeValueAsString(myObject);

        kafkaTemplate.send("finalTopic", jsonObject);
    }

}
