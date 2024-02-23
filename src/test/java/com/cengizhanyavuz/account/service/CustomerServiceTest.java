package com.cengizhanyavuz.account.service;

import com.cengizhanyavuz.account.TestSupport;
import com.cengizhanyavuz.account.dto.CustomerDto;
import com.cengizhanyavuz.account.dto.converter.CustomerDtoConverter;
import com.cengizhanyavuz.account.exception.CustomerNotFoundException;
import com.cengizhanyavuz.account.model.Customer;
import com.cengizhanyavuz.account.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;


public class CustomerServiceTest extends TestSupport {

    private CustomerService service;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter converter;

    @BeforeEach
    public void setUp() {
        customerRepository = mock(CustomerRepository.class);
        converter = mock(CustomerDtoConverter.class);
        service = new CustomerService(customerRepository, converter);
    }

    @Test
    public void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer() {
        Customer customer = generateCustomer();

        Mockito.when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        Customer result = service.findCustomerById("customer-id");

        Assertions.assertEquals(customer, result);
    }

    @Test
    public void testFindByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {
        Mockito.when(customerRepository.findById("1")).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class, () -> service.findCustomerById("1"));
    }

    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer() {
        Customer customer = generateCustomer();
        CustomerDto customerDto = generateCustomerDto();

        Mockito.when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));
        Mockito.when(converter.convertToCustomerDto(customer)).thenReturn(customerDto);


        CustomerDto result = service.getCustomerById("customer-id");
        Assertions.assertEquals(result, customerDto);
    }

    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {
        Mockito.when(customerRepository.findById("customer-id")).thenReturn(Optional.empty());

        Assertions.assertThrows(CustomerNotFoundException.class,
                () -> service.getCustomerById("customer-id"));

        Mockito.verifyNoInteractions(converter);
    }

    @Test
    public void testFindAllCustomer_whenCustomerExists_shouldReturnCustomer() {
        List<CustomerDto> customerDtos = service.getAllCustomer();

        Assertions.assertNotNull(customerDtos);
        Assertions.assertEquals(0, customerDtos.size());
    }
}