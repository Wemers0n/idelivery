package com.example.courier.management.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class AssingnedDelivery {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private OffsetDateTime assignedAt;

    @ManyToOne(optional = false)
    @Getter(AccessLevel.PRIVATE)
    private Courier courier;

    static AssingnedDelivery pending(UUID deliveryId, Courier courier){
        AssingnedDelivery delivery = new AssingnedDelivery();
        delivery.setId(deliveryId);
        delivery.setAssignedAt(OffsetDateTime.now());
        delivery.setCourier(courier);
        return delivery;
    }
}
