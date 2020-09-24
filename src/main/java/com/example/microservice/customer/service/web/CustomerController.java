package com.example.microservice.customer.service.web;

import com.example.microservice.customer.service.domain.Customer;
import com.example.microservice.customer.service.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.ResponseWrapper;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {
   CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers/{id}/verify")
    public Boolean verifyCustomer(@PathVariable String id){
        return customerService.verifyById(id);
    }

    @GetMapping(path = "/customers/{id}/search")
    @ResponseBody
    public CustomerDto findById(@PathVariable String id){
        Customer customer = customerService.findById(id);
        return new CustomerDto(customer.getId(), customer.getName());
    }

    @GetMapping(path = "/customers/{name}/search-name")
    @ResponseWrapper
    public List<CustomerDto> findByName(@PathVariable String name){
        return customerService.findByName(name).stream()
                              .map(customer -> new CustomerDto(customer.getId(), customer.getName()))
                              .collect(Collectors.toList());
    }
}
