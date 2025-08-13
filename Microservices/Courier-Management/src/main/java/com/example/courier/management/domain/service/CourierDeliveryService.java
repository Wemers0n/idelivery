package com.example.courier.management.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.courier.management.domain.model.Courier;
import com.example.courier.management.domain.repository.CourierRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourierDeliveryService {

    private final CourierRepository courierRepository;

    public void assign(UUID deliveryId){
        Courier courier = courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAsc()
            .orElseThrow();

        courier.assign(deliveryId);
        courierRepository.saveAndFlush(courier);

        log.info("Courier {} assinged to delivery {}", courier.getId(), deliveryId);
    }

    public void fulfill(UUID deliveryId){
        Courier courier = courierRepository.findByPendingDeliveries_id(deliveryId)
            .orElseThrow();
            
        courier.fulfill(deliveryId);
        courierRepository.saveAndFlush(courier);

        log.info("Courier {} fulfilled the delivery {}", courier.getId(), deliveryId);
    }
}
