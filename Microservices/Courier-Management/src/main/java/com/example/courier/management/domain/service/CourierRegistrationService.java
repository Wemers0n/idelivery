package com.example.courier.management.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.courier.management.application.model.CourierInput;
import com.example.courier.management.domain.model.Courier;
import com.example.courier.management.domain.repository.CourierRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {

    private final CourierRepository courierRepository;
    
    public Courier create(CourierInput input) {
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());
        
        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, CourierInput input) {
        Courier courier = courierRepository.findById(courierId).orElseThrow();
        courier.setName(input.getName());
        courier.setPhone(input.getPhone());

        return courierRepository.saveAndFlush(courier);
    }

}
