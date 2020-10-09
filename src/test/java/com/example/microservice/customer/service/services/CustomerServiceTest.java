package com.example.microservice.customer.service.services;

import com.example.microservice.customer.service.domain.Customer;
import com.example.microservice.customer.service.repo.CustomerRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Captor
    ArgumentCaptor<Iterable<Customer>> customerArgumentCaptorIterable;

    private Customer customer;
    private final String id = UUID.randomUUID().toString();
    private final String name = UUID.randomUUID().toString();

    @Before
    public void setUp() {
        customer = new Customer(id, name);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        Iterable<Customer> customers = Lists.newArrayList(customer);
        when(customerRepository.findAll()).thenReturn(customers);
    }

    @Test
    public void findById_whenFound_returnCustomer() {
        Customer result = customerService.findById(id);

        assertThat(result.equals(customer), is(true));
    }

    @Test
    public void findById_whenNotFound_returnException() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(String.format("Customer with id %s is not found", id));

        customerService.findById(id);
    }

    @Test
    public void verifyById_whenCustomerExists_returnTrue() {
        Boolean result = customerService.verifyById(id);

        assertThat(result, is(true));
    }

    @Test
    public void verifyById_whenCustomerNotExists_returnFalse() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        Boolean result = customerService.verifyById(id);

        assertThat(result, is(false));
    }

    @Test
    public void findByName_whenFound_returnCustomerList() {
        List<Customer> result = customerService.findByName(name);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0).equals(customer), is(true));
    }

    @Test
    public void findAll_whenNotFound_returnCustomerList() {
        List<Customer> result = customerService.findAll();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0).equals(customer), is(true));
    }

    @Test
    public void findByName_whenNotFound_returnEmptyList() {
        when(customerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        List<Customer> result = customerService.findByName(name);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void save_withCustomerList_saveList(){
        customerService.save(Lists.newArrayList(customer));

        verify(customerRepository).saveAll(customerArgumentCaptorIterable.capture());
        List<Customer> customerLists = StreamSupport.stream(customerArgumentCaptorIterable.getValue().spliterator(), false)
                                                    .collect(Collectors.toList());
        assertThat(customerLists.size(), is(1));
        assertThat(customerLists.get(0).equals(customer), is(true));
    }

    @Test
    public void save_withCustomer_saveCustomer(){
        customerService.create(customer);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        assertThat(customerArgumentCaptor.getValue().equals(customer), is(true));
    }

}