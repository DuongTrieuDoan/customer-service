package com.example.microservice.customer.service.repo;

import com.example.microservice.customer.service.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CustomerRepository extends CrudRepository<Customer, String> {
    Customer findByName(String name);
}
