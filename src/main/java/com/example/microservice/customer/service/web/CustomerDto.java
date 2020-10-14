package com.example.microservice.customer.service.web;

import com.example.microservice.customer.service.domain.Customer;

public class CustomerDto {
    private String id;
    private String name;

    public CustomerDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected CustomerDto(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer toDomain() {
        return new Customer(this.id, this.name);
    }
}
