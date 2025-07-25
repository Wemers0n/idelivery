package com.example.delivery.tracking.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.example.delivery.tracking.domain.exception.DomainException;

public class DeliveryTest {

    @Test
    public void shouldChangeToPlaced(){
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createdValidPreparationDetails());
        delivery.place();

        assertEquals(EDeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlaced(){
        Delivery delivery = Delivery.draft();
        
        assertThrows(DomainException.class, () -> {
            delivery.place();
        });

        assertEquals(EDeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }

    private Delivery.PreparationDetails createdValidPreparationDetails(){
        ContactPoint sender = ContactPoint.builder()
            .zipCode("00000-000")
            .street("Rua São Paulo")
            .number("100")
            .complement("Sala 401")
            .name("João Silva")
            .phone("(11) 90000-1234")
            .build();

        ContactPoint recipient = ContactPoint.builder()
            .zipCode("00000-123")
            .street("Rua Brasil")
            .number("401")
            .complement("Sala 102")
            .name("Paulo Silva")
            .phone("(11) 91234-0000")
            .build();

        return Delivery.PreparationDetails.builder()
            .sender(sender)
            .recipient(recipient)
            .distanceFee(new BigDecimal("15.00"))
            .courierPayout(new BigDecimal("5.00"))
            .expectedDeliveryTime(Duration.ofHours(5))
            .build();
    }

}
