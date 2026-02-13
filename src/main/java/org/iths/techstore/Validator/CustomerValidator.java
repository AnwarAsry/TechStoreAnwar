package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.CustomerNotValidateException;
import org.iths.techstore.Model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {
    public void validate(Customer customer) {
        validateCustomerName(customer.getName());
        validateCustomerTelephoneNumber(customer.getTelefonNumber());
        validateCustomerEmail(customer.getEmail());
    }

    public void validateCustomerName(String name) {
        if (name == null || name.isEmpty()) {
            throw new CustomerNotValidateException("Customer name is invalid");
        }
    }

    public void validateCustomerTelephoneNumber(String telephoneNumber) {
        if (telephoneNumber == null || telephoneNumber.isEmpty()) {
            throw new CustomerNotValidateException("Customer telephone number is invalid.");
        }
    }

    public void validateCustomerEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new CustomerNotValidateException("Email is invalid.");
        }
    }
}
