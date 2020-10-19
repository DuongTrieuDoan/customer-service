package com.example.microservice.customer.service.controller;

import com.example.microservice.customer.service.domain.Customer;
import com.example.microservice.customer.service.service.CustomerService;
import com.example.microservice.customer.service.service.MessageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.ResponseWrapper;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CustomerController {
   private CustomerService customerService;
   private MessageQueueService messageQueueService;

    @Autowired
    public CustomerController(CustomerService customerService, MessageQueueService messageQueueService) {
        this.customerService = customerService;
        this.messageQueueService = messageQueueService;
    }

    @GetMapping(path = "/customers/verify/{id}")
    public Boolean verifyCustomer(@PathVariable String id){
        return customerService.verifyById(id);
    }

    @GetMapping(path = "/customers/{id}")
    @ResponseBody
    public CustomerDto findById(@PathVariable String id){
        Customer customer = customerService.findById(id);
        return new CustomerDto(customer.getId(), customer.getName());
    }

    @GetMapping(path = "/customers/search-name/{name}")
    @ResponseWrapper
    public List<CustomerDto> findByName(@PathVariable String name){
        return customerService.findByName(name).stream()
                              .map(customer -> new CustomerDto(customer.getId(), customer.getName()))
                              .collect(Collectors.toList());
    }

    @GetMapping(path = "/customers")
    @ResponseWrapper
    public List<CustomerDto> findAll(){
        return customerService.findAll().stream()
                              .map(customer -> new CustomerDto(customer.getId(), customer.getName()))
                              .collect(Collectors.toList());
    }

    @PostMapping(path = "/customers")
    @ResponseBody
    public CustomerDto create(@RequestBody CreateCustomerDto createCustomerDto){
        Customer customer = customerService.save(createCustomerDto.toDomain());
        return new CustomerDto(customer.getId(), customer.getName());
    }

    @PutMapping(path = "/customers/update")
    @ResponseBody
    public CustomerDto update(@RequestBody CustomerDto customerDto){
        Customer customer = customerService.save(customerDto.toDomain());
        return new CustomerDto(customer.getId(), customer.getName());
    }

    @GetMapping("receive")
    public void receiveMqMessage() throws Exception{
        messageQueueService.receiveOrderMessage();
    }
}
