package com.example.microservice.customer.service.controller;

import com.example.microservice.customer.service.model.Customer;
import com.example.microservice.customer.service.service.CustomerService;
import com.example.microservice.customer.service.service.MessageQueueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerController.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private MessageQueueService messageQueueService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerDto customerDto;

    private Customer customer;

    private CreateCustomerDto createCustomerDto;


    @Before
    public void setUp(){
        customerDto = new CustomerDto(UUID.randomUUID().toString(),
                                      UUID.randomUUID().toString());
        customer = customerDto.toDomain();
        createCustomerDto = new CreateCustomerDto(customer.getName());
        when(customerService.findById(customerDto.getId())).thenReturn(customer);
        when(customerService.save(any(Customer.class))).thenReturn(customer);
        when(customerService.findAll()).thenReturn(Collections.singletonList(customer));
        when(customerService.findByName(customer.getName())).thenReturn(Collections.singletonList(customer));
        when(customerService.verifyById(customer.getId())).thenReturn(true);
    }

    @Test
    public void verifyCustomer_verified_returnsTrue() throws Exception {
        this.mockMvc.perform(get("/customers/verify/{id}",
                                 customer.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void findById_found_returnsCorrectObject() throws Exception  {
        this.mockMvc.perform(get("/customers/{id}",
                                 customer.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(customer.getId()))
                    .andExpect(jsonPath("$.name").value(customer.getName()));
    }

    @Test
    public void findById_notFound_throwsException() throws Exception{
        when(customerService.findById(customerDto.getId())).thenThrow(EntityNotFoundException.class);

        this.mockMvc.perform(get("/customers/{id}",
                                 customer.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isNotFound());
    }

    @Test
    public void findByName_found_returnsCorrectObject() throws Exception {
        this.mockMvc.perform(get("/customers/search-name/{name}",
                                 customer.getName()).contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].id").value(customer.getId()))
                    .andExpect(jsonPath("$[0].name").value(customer.getName()));
    }

    @Test
    public void findAll() throws Exception{
        this.mockMvc.perform(get("/customers"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$[0].id").value(customer.getId()))
                    .andExpect(jsonPath("$[0].name").value(customer.getName()));
    }

    @Test
    public void create() throws Exception{
        this.mockMvc.perform(post("/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                                               .content(objectMapper.writeValueAsString(createCustomerDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(customer.getId()))
                    .andExpect(jsonPath("$.name").value(customer.getName()));
    }

    @Test
    public void update() throws Exception{
        this.mockMvc.perform(put("/customers/update").contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .content(objectMapper.writeValueAsString(customerDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id").value(customer.getId()))
                    .andExpect(jsonPath("$.name").value(customer.getName()));
    }

    @Test
    public void receiveMqMessage() {
    }
}