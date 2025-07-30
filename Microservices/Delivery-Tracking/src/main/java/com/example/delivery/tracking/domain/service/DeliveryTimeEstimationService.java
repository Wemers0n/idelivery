package com.example.delivery.tracking.domain.service;

import com.example.delivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {

    DeliveryEstimate estimate(ContactPoint sender, ContactPoint recipient);
}
