package com.example.delivery.tracking.domain.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EDeliveryStatusTest {

    @Test
    public void draft_canChangeToWaitingForCourier(){
        assertTrue(EDeliveryStatus.DRAFT.canChangeTo(EDeliveryStatus.WAITING_FOR_COURIER));
    }

    @Test
    public void draft_canChangeToInTransit(){
        assertTrue(EDeliveryStatus.DRAFT.canNotChangeTo(EDeliveryStatus.IN_TRANSIT));
    }

}
