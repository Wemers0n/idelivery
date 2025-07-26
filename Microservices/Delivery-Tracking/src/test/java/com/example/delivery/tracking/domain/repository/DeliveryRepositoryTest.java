package com.example.delivery.tracking.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.delivery.tracking.domain.model.ContactPoint;
import com.example.delivery.tracking.domain.model.Delivery;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist(){
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createdValidPreparationDetails());

        delivery.addItem("Computador", 2);
        delivery.addItem("Notebook", 2);

        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());
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
