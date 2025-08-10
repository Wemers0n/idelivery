package com.example.delivery.tracking.infraestructure.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.delivery.tracking.domain.event.DeliveryFulFilledEvent;
import com.example.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.example.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.example.delivery.tracking.infraestructure.kafka.IntegrationEventPublisher;
import com.example.delivery.tracking.infraestructure.kafka.KafkaTopicConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {

    private final IntegrationEventPublisher integrationEventPublisher;

    @EventListener
    public void handlePlacedEvent(DeliveryPlacedEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventTopicName);
    }

    @EventListener
    public void handlePickUpEvent(DeliveryPickUpEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventTopicName);
    }

    @EventListener
    public void handleFulfilledEvent(DeliveryFulFilledEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event, event.getDeliveryId().toString(), KafkaTopicConfig.deliveryEventTopicName);
    }
}
