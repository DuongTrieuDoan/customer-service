package com.example.microservice.customer.service;

import com.example.microservice.customer.service.model.Customer;
import com.example.microservice.customer.service.service.CustomerService;
import com.example.microservice.customer.service.service.MessageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class CustomerServiceApplication implements CommandLineRunner {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MessageQueueService messageQueueService;

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Override
    public void run(String... strings) throws Exception{
	    customerService.save(new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
	    customerService.save(new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
	    customerService.save(new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
	    customerService.save(new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString()));

	    customerService.findAll().stream().forEach(customer -> System.out.println(customer));

        messageQueueService.receiveOrderMessage();
    }
}
