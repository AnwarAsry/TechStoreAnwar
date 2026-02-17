package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.CustomerNotValidateException;
import org.iths.techstore.Model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerValidatorTest {

    private CustomerValidator customerValidator;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customerValidator = new CustomerValidator();
        customer = new Customer();
    }

    @Test
    @DisplayName("Validator should not throw when all fields are filled")
    public void validatorShouldNotThrowWhenAllFieldsAreFilled() {

        customer.setName("Vin Diesel");
        customer.setTelefonNumber("0701234567");
        customer.setEmail("vin@diesel.com");

        assertDoesNotThrow(() -> customerValidator.validate(customer));
    }

    @Test
    @DisplayName("Validator should throw exception when name is null")
    public void validateCustomerNameNull() {

        customer.setName(null);
        customer.setTelefonNumber("0701234567");
        customer.setEmail("vin@diesel.com");

        assertThrows(CustomerNotValidateException.class,
                () -> customerValidator.validate(customer));
    }

    @Test
    @DisplayName("Validator should throw exception when name is empty")
    public void validateCustomerNameEmpty() {

        assertThrows(CustomerNotValidateException.class,
                () -> customerValidator.validateCustomerName(""));
    }

    @Test
    @DisplayName("Validator should not throw when telephone number is valid")
    public void validateTelephoneNumberValid() {

        assertDoesNotThrow(() ->
                customerValidator.validateCustomerTelephoneNumber("0701234567"));
    }

    @Test
    @DisplayName("Validator should throw exception when telephone number is null")
    public void validateTelephoneNumberNull() {

        assertThrows(CustomerNotValidateException.class,
                () -> customerValidator.validateCustomerTelephoneNumber(null));
    }

    @Test
    @DisplayName("Validator should throw exception when telephone number is empty")
    public void validateTelephoneNumberEmpty() {

        assertThrows(CustomerNotValidateException.class,
                () -> customerValidator.validateCustomerTelephoneNumber(""));
    }

    @Test
    @DisplayName("Validator should not throw when email is valid")
    public void validateEmailValid() {

        assertDoesNotThrow(() ->
                customerValidator.validateCustomerEmail("rihanna@smith.com"));
    }

    @Test
    @DisplayName("Validator should throw exception when email is null")
    public void validateEmailNull() {

        assertThrows(CustomerNotValidateException.class,
                () -> customerValidator.validateCustomerEmail(null));
    }

    @Test
    @DisplayName("Validator should throw exception when email is empty")
    public void validateEmailEmpty() {

        assertThrows(CustomerNotValidateException.class,
                () -> customerValidator.validateCustomerEmail(""));
    }
}
