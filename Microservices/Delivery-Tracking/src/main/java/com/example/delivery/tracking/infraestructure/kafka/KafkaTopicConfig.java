package com.example.delivery.tracking.infraestructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String deliveryEventTopicName = "deliveries.v1.events";

    @Bean
    public NewTopic deliveryEventTopic(){
        return TopicBuilder
            .name(deliveryEventTopicName)
            .partitions(3)
            .replicas(1)
            .build();
    }
}
