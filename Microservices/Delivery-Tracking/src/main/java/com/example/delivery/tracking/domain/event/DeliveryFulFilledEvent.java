package com.example.delivery.tracking.domain.event;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryFulFilledEvent {

    private final OffsetDateTime occurredAt;
    private final UUID deliveryId;
}
