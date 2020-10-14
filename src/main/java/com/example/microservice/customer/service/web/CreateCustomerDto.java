package com.example.microservice.customer.service.web;

import com.example.microservice.customer.service.domain.Customer;

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
