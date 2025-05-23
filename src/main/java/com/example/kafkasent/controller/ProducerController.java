package com.example.kafkasent.controller;

import com.example.kafkasent.model.MyObject;
import com.example.kafkasent.service.MyProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProducerController {

    private final MyProducer producer;

    @Autowired
    public ProducerController(MyProducer producer) {
        this.producer = producer;

    }

    @PostMapping("/send")
    public void send(@RequestBody MyObject myObject) throws JsonProcessingException {

        producer.sendObject(myObject);

    }

    @GetMapping("/all")
    public List<MyObject> getAll() {
        return producer.findAll();
    }
}
