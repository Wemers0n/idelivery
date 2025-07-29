package com.example.delivery.tracking.application.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.delivery.tracking.application.model.DeliveryInput;
import com.example.delivery.tracking.domain.model.Delivery;
import com.example.delivery.tracking.domain.repository.DeliveryRepository;
import com.example.delivery.tracking.domain.service.DeliveryPreparationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryRepository deliveryRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input){
        return deliveryPreparationService.draft(input);
    }

    @PutMapping("/{deliveryId}")
    public Delivery edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput input){
        return deliveryPreparationService.edit(deliveryId, input);
    }

    @GetMapping
    public PagedModel<Delivery> findAll(@PageableDefault Pageable pageable){
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId){
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
