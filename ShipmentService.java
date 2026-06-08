package com.company.shipment.service;

import com.company.shipment.model.Shipment;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShipmentService {

    private final Map<String, Shipment> db = new HashMap<>();

    public ShipmentService() {

        Shipment shipment =
                new Shipment(
                        1L,
                        "TRK1001",
                        "Hyderabad",
                        "Bangalore",
                        "CREATED"
                );

        db.put(
                shipment.getTrackingId(),
                shipment
        );
    }

    public Collection<Shipment> findAll() {
        return db.values();
    }

    public Shipment findByTrackingId(
            String trackingId) {

        return db.get(trackingId);
    }

    public Shipment createShipment(
            Shipment shipment) {

        db.put(
                shipment.getTrackingId(),
                shipment
        );

        return shipment;
    }
}
