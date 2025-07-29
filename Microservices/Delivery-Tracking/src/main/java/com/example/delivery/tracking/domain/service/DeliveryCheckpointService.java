package com.example.delivery.tracking.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.delivery.tracking.domain.exception.DomainException;
import com.example.delivery.tracking.domain.model.Delivery;
import com.example.delivery.tracking.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {

    private final DeliveryRepository deliveryRepository;

    public void place(UUID deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow(()-> new DomainException());

        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickUp(UUID deliveryId, UUID courierId){
        Delivery delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new DomainException());

        delivery.pickUp(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void completion(UUID deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new DomainException());

        delivery.markAsDelived();
        deliveryRepository.saveAndFlush(delivery);
    }
}
