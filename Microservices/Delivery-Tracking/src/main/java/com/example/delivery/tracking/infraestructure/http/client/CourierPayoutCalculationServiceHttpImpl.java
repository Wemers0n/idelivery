package com.example.delivery.tracking.infraestructure.http.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.delivery.tracking.domain.service.CourierPayoutCalculationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService{

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
       var payoutCalculation = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));
       return payoutCalculation.getPayoutFee();
    }

}
