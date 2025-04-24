package com.example.kafkasent.service;

import com.example.kafkasent.model.MyObject;
import com.example.kafkasent.repository.MyObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyProducer {

    private final KafkaTemplate<String, MyObject> kafkaTemplate;
    private final MyObjectRepository myObjectRepository;

    @Autowired
    public MyProducer(KafkaTemplate<String, MyObject> kafkaTemplate, MyObjectRepository myObjectRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.myObjectRepository = myObjectRepository;
    }

    public void sendObject(MyObject myObject) {

        MyObject saveObject = myObjectRepository.save(myObject);

        kafkaTemplate.send("my-topic", saveObject);
    }

    @KafkaListener(topics = "finalTopic", groupId = "finalGroup", containerFactory = "finalGroupKafkaListenerContainerFactory")
    public void listen(MyObject myObject) {
        System.out.println("first PRODUCER listen change object by CONSUMER " + myObject.getName() + " " + myObject.getCount());

        myObjectRepository.save(myObject);
        System.out.println("Object save to DB");
    }

    public List<MyObject> findAll() {
        return myObjectRepository.findAll(); }
}
