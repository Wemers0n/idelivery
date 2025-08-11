package com.example.courier.management.infraestructure.event;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryFulFilledIntegrationEvent {

    private OffsetDateTime occurredAt;
    private UUID deliveryId;
}
