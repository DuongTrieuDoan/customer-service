package com.example.microservice.customer.service.domain.errorHandlers;

public class MqException extends RuntimeException {
    String message;

    public MqException(String message) {
        this.message = message;
    }
}