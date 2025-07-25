package com.example.courier.management.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Courier {

    @EqualsAndHashCode.Include
    private UUID id;

    @Setter(AccessLevel.PUBLIC)
    private String name;

    @Setter(AccessLevel.PUBLIC)
    private String phone;

    private Integer fulfilledDeliveriesQuantity;
    private Integer pendingDeliveriesQuantity;

    private OffsetDateTime lastFulfilledDeliveryAt;

    private List<AssingnedDelivery> pendingDeliveries = new ArrayList<>();

    public List<AssingnedDelivery> getPendingDeliveries(){
        return Collections.unmodifiableList(this.pendingDeliveries);
    }

    public static Courier brandNew(String name, String phone){
       Courier courier = new Courier();
       courier.setId(UUID.randomUUID());
       courier.setName(name);
       courier.setPhone(phone);
       courier.setPendingDeliveriesQuantity(0);
       courier.setFulfilledDeliveriesQuantity(0);
       return courier;
    }

    public void assign(UUID deliveryId){
        this.pendingDeliveries.add(AssingnedDelivery.pending(deliveryId));
        this.setPendingDeliveriesQuantity(getPendingDeliveriesQuantity() + 1);
    }

    public void fulfill(UUID deliveryId){
        AssingnedDelivery delivery = this.pendingDeliveries.stream()
            .filter(d -> d.getId().equals(deliveryId)).findFirst().orElseThrow();
            this.pendingDeliveries.remove(delivery);

            this.pendingDeliveriesQuantity++;
            this.fulfilledDeliveriesQuantity++;
            this.lastFulfilledDeliveryAt = OffsetDateTime.now();
    }
}
