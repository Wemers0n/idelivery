package com.example.delivery.tracking.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.delivery.tracking.application.model.ContactPointInput;
import com.example.delivery.tracking.application.model.DeliveryInput;
import com.example.delivery.tracking.application.model.ItemInput;
import com.example.delivery.tracking.domain.exception.DomainException;
import com.example.delivery.tracking.domain.model.ContactPoint;
import com.example.delivery.tracking.domain.model.Delivery;
import com.example.delivery.tracking.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryTimeEstimationService deliveryTimeEstimationService;
    private final CourierPayoutCalculationService courierPayoutCalculationService;

    @Transactional
    public Delivery draft(DeliveryInput input){
        Delivery delivery = Delivery.draft();
        handlePreparation(input, delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }

    @Transactional
    public Delivery edit(UUID deliveryId, DeliveryInput input){
       Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException());
       delivery.removeItems();
       handlePreparation(input, delivery);
       return deliveryRepository.saveAndFlush(delivery);
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery){
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
        .phone(senderInput.getPhone())
        .name(senderInput.getName())
        .complement(senderInput.getComplement())
        .number(senderInput.getNumber())
        .zipCode(senderInput.getZipCode())
        .street(senderInput.getStreet())
        .build();

        ContactPoint recipient = ContactPoint.builder()
        .phone(recipientInput.getPhone())
        .name(recipientInput.getName())
        .complement(recipientInput.getComplement())
        .number(recipientInput.getNumber())
        .zipCode(recipientInput.getZipCode())
        .street(recipientInput.getStreet())
        .build();

        DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
        BigDecimal calculatedPayout = courierPayoutCalculationService.calculatePayout(estimate.getDistanceInKm());

        BigDecimal distanceFee = calculateFee(estimate.getDistanceInKm());

        var preparationDetails = Delivery.PreparationDetails.builder()
            .recipient(recipient)
            .sender(sender)
            .expectedDeliveryTime(estimate.getEstimatedTime())
            .distanceFee(distanceFee)
            .courierPayout(calculatedPayout)
            .build();

        delivery.editPreparationDetails(preparationDetails);

        for(ItemInput itemInput : input.getItems()){
            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }
    }

    private BigDecimal calculateFee(Double distanceInKm){
        return new BigDecimal('3')
                .multiply(new BigDecimal(distanceInKm))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
