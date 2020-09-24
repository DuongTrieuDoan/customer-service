package com.example.microservice.customer.service.web;

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
}
