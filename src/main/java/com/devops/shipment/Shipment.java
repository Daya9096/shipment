package com.company.shipment.model;

public class Shipment {

    private Long id;
    private String trackingId;
    private String source;
    private String destination;
    private String status;

    public Shipment() {
    }

    public Shipment(Long id,
                    String trackingId,
                    String source,
                    String destination,
                    String status) {
        this.id = id;
        this.trackingId = trackingId;
        this.source = source;
        this.destination = destination;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
