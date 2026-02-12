package org.iths.techstore.Service;

import org.iths.techstore.Model.Customer;
import org.iths.techstore.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer with id: " + id + " doesn't exist."));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("Customer with id: " + id + " doesn't exist.");
        }
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("Customer with id: " + id + " doesn't exist.");
        }
        customerRepository.deleteById(id);
    }
}
