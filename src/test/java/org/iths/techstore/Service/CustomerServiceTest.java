package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.CustomerNotFoundException;
import org.iths.techstore.Model.Customer;
import org.iths.techstore.Repository.CustomerRepository;
import org.iths.techstore.Validator.CustomerValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerValidator customerValidator;

    @Test
    @DisplayName("Method should return customers list")
    public void getAllCustomersTest() {
        List<Customer> customers = List.of(
                new Customer("Victoria","0701234567","victoria@test.com"),
                new Customer("Elisabeth", "0709876543","elisabeth@test.com")
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());

        verify(customerRepository).findAll();
    }

    @Test
    @DisplayName("Should return customer when id exists")
    public void getCustomerByIdTest() {

        Customer customer = new Customer(
                "Katherine","0708356271","katherine@test.com");
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertEquals(customer, result);
        assertEquals(1L, result.getId());
        assertEquals("Katherine", result.getName());
        assertEquals("0708356271", result.getTelefonNumber());
        assertEquals("katherine@test.com", result.getEmail());

        verify(customerRepository).findById(1L);
    }

    @Test
    @DisplayName("Throw exception when customer with specified id does not exist")
    public void getCustomerByIdShouldThrowExceptionWhenNotFound() {
        Long id = 3L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getCustomerById(id));

        assertEquals("Customer with id: 3 does not exist.",
                exception.getMessage()
        );

        verify(customerRepository).findById(id);
    }

    @Test
    @DisplayName("Create customer should validate and save")
    public void createCustomerTest() {

        Customer customer = new Customer(
                "Lisa","0703457643","lisa@test.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertEquals("Lisa", result.getName());
        assertEquals("0703457643", result.getTelefonNumber());
        assertEquals("lisa@test.com", result.getEmail());

        verify(customerValidator).validate(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    @DisplayName("Should update customer information when id exists")
    public void updateExistingCustomerTest() {
        Long id = 1L;
        Customer customer = new Customer(
                "Melina", "0709999999", "melina@test.com");

        customer.setTelefonNumber("0709876543");

        when(customerRepository.existsById(id)).thenReturn(true);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(id, customer);

        assertEquals(id, updatedCustomer.getId());
        assertEquals("0709876543", updatedCustomer.getTelefonNumber());

        verify(customerRepository).existsById(id);
        verify(customerValidator).validate(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    @DisplayName("Throw exception when updating non existing customer")
    public void updateNoneExistCustomerShouldThrowException() {
        Long id = 5L;
        Customer customer = new Customer(
                "Melissa","0709463821","melissa@test.com");


        when(customerRepository.existsById(id)).thenReturn(false);

        CustomerNotFoundException exception  = assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.updateCustomer(id, customer));

        assertEquals("Customer with id: 5 does not exist.", exception.getMessage());

        verify(customerRepository).existsById(id);
        verify(customerValidator, never()).validate(any());
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete customer when id exists")
    public void deleteCustomerTest() {
        Long id = 1L;
        when(customerRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(
                () -> customerService.deleteCustomer(id));

        verify(customerRepository).existsById(id);
        verify(customerRepository).deleteById(id);
    }

    @Test
    @DisplayName("Throw exception when deleting non existing customer")
    public void deleteNonExistCustomerShouldThrowException() {
        Long id = 9L;

        when(customerRepository.existsById(id)).thenReturn(false);

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(id));

        assertEquals("Customer with id: 9 does not exist.", exception.getMessage());

        verify(customerRepository).existsById(id);
        verify(customerRepository, never()).deleteById(any());
    }
}
