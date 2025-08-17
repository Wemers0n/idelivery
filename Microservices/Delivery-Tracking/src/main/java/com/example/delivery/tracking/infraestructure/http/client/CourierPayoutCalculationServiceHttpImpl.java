package com.example.delivery.tracking.infraestructure.http.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.example.delivery.tracking.domain.service.CourierPayoutCalculationService;
import com.example.delivery.tracking.infraestructure.http.exception.BadGatewayException;
import com.example.delivery.tracking.infraestructure.http.exception.GatewayTimoutException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService{

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        try{
            var payoutCalculation = courierAPIClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInKm));
            return payoutCalculation.getPayoutFee();
        } catch(ResourceAccessException e) {
            throw new GatewayTimoutException(e);
        } catch (HttpServerErrorException | IllegalArgumentException e){
            throw new BadGatewayException(e);
        }    
    }

}
