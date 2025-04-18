package com.ebanx.takehome.model;

public class Event {

    private EventType type;
    private String origin;
    private String destination;
    private Integer amount;

    public Event() {
    }

    // Getters e Setters
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public enum EventType {
        deposit,
        withdraw,
        transfer
    }
}
