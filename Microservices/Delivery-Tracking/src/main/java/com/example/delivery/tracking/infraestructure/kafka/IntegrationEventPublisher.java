package com.example.delivery.tracking.infraestructure.kafka;

public interface IntegrationEventPublisher {

    void publish(Object event, String key, String topic);

}
