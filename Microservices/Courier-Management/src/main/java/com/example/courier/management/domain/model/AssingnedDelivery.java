package com.example.courier.management.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class AssingnedDelivery {

    @EqualsAndHashCode.Include
    private UUID id;
    private OffsetDateTime assignedAt;

    static AssingnedDelivery pending(UUID deliveryId){
        AssingnedDelivery delivery = new AssingnedDelivery();
        delivery.setId(deliveryId);
        delivery.setAssignedAt(OffsetDateTime.now());
        return delivery;
    }
}
