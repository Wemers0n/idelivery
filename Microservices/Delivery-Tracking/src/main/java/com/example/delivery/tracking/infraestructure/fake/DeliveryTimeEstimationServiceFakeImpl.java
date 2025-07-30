package com.example.delivery.tracking.infraestructure.fake;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.example.delivery.tracking.domain.model.ContactPoint;
import com.example.delivery.tracking.domain.service.DeliveryEstimate;
import com.example.delivery.tracking.domain.service.DeliveryTimeEstimationService;

@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService{

    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
        return new DeliveryEstimate(Duration.ofHours(3), 3.1);
    }


}
