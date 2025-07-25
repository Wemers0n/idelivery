package com.example.delivery.tracking.domain.model;

import java.util.Arrays;
import java.util.List;

public enum EDeliveryStatus {
    DRAFT,
    WAITING_FOR_COURIER(DRAFT),
    IN_TRANSIT(WAITING_FOR_COURIER),
    DELIVERY(IN_TRANSIT);

    private final List<EDeliveryStatus> previousStatuses;

    EDeliveryStatus(EDeliveryStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    public boolean canNotChangeTo(EDeliveryStatus newStatus){
        EDeliveryStatus current = this;
        return !newStatus.previousStatuses.contains(current);
    }

    public boolean canChangeTo(EDeliveryStatus newStatus){
        return !canNotChangeTo(newStatus);
    }

}
