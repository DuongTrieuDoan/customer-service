package com.example.microservice.customer.service.controller;

import com.example.microservice.customer.service.model.Customer;

import java.util.UUID;

public class CreateCustomerDto {
    private String name;

    public CreateCustomerDto(String name) {
        this.name = name;
    }

    protected CreateCustomerDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer toDomain(){
        return new Customer(UUID.randomUUID().toString(), this.name);
    }
}
