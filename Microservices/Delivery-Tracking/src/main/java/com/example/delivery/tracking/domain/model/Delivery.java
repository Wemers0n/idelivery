package com.example.delivery.tracking.domain.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.example.delivery.tracking.domain.event.DeliveryFulFilledEvent;
import com.example.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.example.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.example.delivery.tracking.domain.exception.DomainException;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Delivery extends AbstractAggregateRoot<Delivery>{

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID courierId;

    private EDeliveryStatus status;

    private OffsetDateTime placedAt;
    private OffsetDateTime assingnedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;

    private Integer totalItems;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode", column = @Column(name = "sender_zip_code")),
        @AttributeOverride(name = "street", column = @Column(name = "sender_street")),
        @AttributeOverride(name = "number", column = @Column(name = "sender_number")),
        @AttributeOverride(name = "complement", column = @Column(name = "sender_complemnt")),
        @AttributeOverride(name = "name", column = @Column(name = "sender_name")),
        @AttributeOverride(name = "phone", column = @Column(name = "sender_phone"))
    })
    private ContactPoint sender;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode", column = @Column(name = "recipient_zip_code")),
        @AttributeOverride(name = "street", column = @Column(name = "recipient_street")),
        @AttributeOverride(name = "number", column = @Column(name = "recipient_number")),
        @AttributeOverride(name = "complement", column = @Column(name = "recipient_complemnt")),
        @AttributeOverride(name = "name", column = @Column(name = "recipient_name")),
        @AttributeOverride(name = "phone", column = @Column(name = "recipient_phone"))
    })
    private ContactPoint recipient;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "delivery")
    private List<Item> items = new ArrayList<>();

    public static Delivery draft(){
        Delivery delivery = new Delivery();
        delivery.setId(UUID.randomUUID());
        delivery.setStatus(EDeliveryStatus.DRAFT);
        delivery.setTotalItems(0);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
        delivery.setDistanceFee(BigDecimal.ZERO);
        
        return delivery;
    }

    public UUID addItem(String name, int quantity){
        Item item = Item.brandNew(name, quantity, this);
        items.add(item);
        calculateTotalItens();
        return item.getId();
    }

    public void removeItem(UUID itemId){
        items.removeIf(item -> item.getId().equals(itemId));
        calculateTotalItens();
    }

    public void removeItems(){
        items.clear();
        calculateTotalItens();
    }

    public void editPreparationDetails(PreparationDetails details){
        verifyIfCanBeEdited();
        setSender(details.getSender());
        setRecipient(details.getRecipient());
        setDistanceFee(details.getDistanceFee());
        setCourierPayout(details.getCourierPayout());

        setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDeliveryTime()));
        setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
    }

    public void place(){
        verifyIfCanBePlaced();
        this.changeStatusTo(EDeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryPlacedEvent(this.getPlacedAt(), this.getId())); // Registra um evento
    }

    public void pickUp(UUID courierId){
        this.setCourierId(courierId);
        this.changeStatusTo(EDeliveryStatus.IN_TRANSIT);
        this.setAssingnedAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryPickUpEvent(this.getAssingnedAt(), this.getId()));
    }

    public void markAsDelived(){ // Estado final do pedido
        this.changeStatusTo(EDeliveryStatus.DELIVERED);
        this.setFulfilledAt(OffsetDateTime.now());
        super.registerEvent(new DeliveryFulFilledEvent(this.getFulfilledAt(), this.getId()));
    }

    public void changeItemQuantity(UUID itemId, int quantity){
        Item item = getItems().stream().filter(i -> i.getId().equals(itemId))
        .findFirst().orElseThrow();

        item.setQuantity(quantity);
        calculateTotalItens();
    }

    public List<Item> getItems(){
        return Collections.unmodifiableList(this.items);
    }

    private void calculateTotalItens(){
        int totalItems = getItems().stream().mapToInt(Item::getQuantity).sum();
        setTotalItems(totalItems);
    }

    private void verifyIfCanBePlaced(){
        if(!isFilled()){
            throw new DomainException();
        }
        if(!getStatus().equals(EDeliveryStatus.DRAFT)){
            throw new DomainException();
        }
    }

    private void verifyIfCanBeEdited(){
        if(!getStatus().equals(EDeliveryStatus.DRAFT)){
            throw new DomainException();
        }
    }

    private boolean isFilled(){
        return this.getSender() != null && 
            this.getRecipient() != null && 
            this.getTotalCost() != null;
    }

    private void changeStatusTo(EDeliveryStatus newStatus){
        if(newStatus != null && this.getStatus().canNotChangeTo(newStatus)){
            throw new DomainException("Invalid status transition from" + this.getStatus() + " to " + newStatus);
        }
        this.setStatus(newStatus);
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PreparationDetails {
        private ContactPoint sender;
        private ContactPoint recipient;
        private BigDecimal distanceFee;
        private BigDecimal courierPayout;
        private Duration expectedDeliveryTime;
    }
}
