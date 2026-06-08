package com.company.shipment.controller;

import com.company.shipment.model.Shipment;
import com.company.shipment.service.ShipmentService;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(
            ShipmentService service) {

        this.service = service;
    }

    @GetMapping
    public Collection<Shipment> getAll() {

        return service.findAll();
    }

    @GetMapping("/{trackingId}")
    public Shipment getShipment(
            @PathVariable String trackingId) {

        return service.findByTrackingId(
                trackingId
        );
    }

    @PostMapping
    public Shipment createShipment(
            @RequestBody Shipment shipment) {

        return service.createShipment(
                shipment
        );
    }
}
