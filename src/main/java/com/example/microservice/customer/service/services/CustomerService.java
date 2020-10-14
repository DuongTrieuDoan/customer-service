package com.example.microservice.customer.service.services;

import com.example.microservice.customer.service.domain.Customer;
import com.example.microservice.customer.service.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findById(String id) {
        return customerRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with id %s is not found", id)));
    }

    public Boolean verifyById(String id){
        return customerRepository.findById(id).isPresent();
    }

    public List<Customer> findByName(String name){
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                            .filter(customer -> customer.getName().equals(name))
                            .collect(Collectors.toList());
    }

    public List<Customer> findAll(){
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    public  List<Customer> save(List<Customer> customers){
        Iterable<Customer> customerIterable = customers;
        return StreamSupport.stream(customerRepository.saveAll(customerIterable).spliterator(), false)
                            .collect(Collectors.toList());
    }

    public Customer save(Customer customer){
        return customerRepository.save(customer);
    }
}
