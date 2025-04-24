package com.example.kafkasent.config;

import com.example.kafkasent.model.MyObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, MyObject> kafkaTemplate(ProducerFactory<String, MyObject> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, MyObject> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MyObject> myGroupKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MyObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(myGroupConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MyObject> myGroupConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroup");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(MyObject.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MyObject> finalGroupKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MyObject> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(finalGroupConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MyObject> finalGroupConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:29092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "finalGroup");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), new JsonDeserializer<>(MyObject.class));
    }

}
