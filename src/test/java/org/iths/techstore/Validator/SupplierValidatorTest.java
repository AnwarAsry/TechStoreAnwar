package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.SupplierNotValidFieldException;
import org.iths.techstore.Model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SupplierValidatorTest {
    private SupplierValidator supplierValidator;
    private Supplier supplier;


    @BeforeEach
    void setUp() {
        supplierValidator = new SupplierValidator();
        supplier = new Supplier(1L, "TechSupplier", "Germany", "Contact A", "some@techsupplier.se");
    }

    // Test for valid supplier company name
    @Test
    @DisplayName("Test valid company name")
    void testValidCompanyName() {
        assertDoesNotThrow(() -> supplierValidator.isValidCompanyName(supplier.getCompanyName()));
    }

    // Test for invalid supplier company name
    @Test
    @DisplayName("Test invalid company name")
    void testInvalidCompanyName() {
        supplier.setCompanyName("");
        assertThrows(SupplierNotValidFieldException.class, () -> supplierValidator.isValidCompanyName(supplier.getCompanyName()));
    }

    // Test for valid supplier country
    @Test
    @DisplayName("Test valid country")
    void testValidCountry() {
        assertDoesNotThrow(() -> supplierValidator.isValidCountry(supplier.getCountry()));
    }

    // Test for invalid supplier country
    @Test
    @DisplayName("Test invalid country")
    void testInvalidCountry() {
        supplier.setCountry("");
        assertThrows(SupplierNotValidFieldException.class, () -> supplierValidator.isValidCountry(supplier.getCountry()));
    }

    // Test for valid supplier contact person
    @Test
    @DisplayName("Test valid contact person")
    void testValidContactPerson() {
        assertDoesNotThrow(() -> supplierValidator.isValidContactPerson(supplier.getContactPerson()));
    }

    // Test for invalid supplier contact person
    @Test
    @DisplayName("Test invalid contact person")
    void testInvalidContactPerson() {
        supplier.setContactPerson("");
        assertThrows(SupplierNotValidFieldException.class, () -> supplierValidator.isValidContactPerson(supplier.getContactPerson()));
    }

    // Test for valid supplier email
    @Test
    @DisplayName("Test valid email")
    void testValidEmail() {
        assertDoesNotThrow(() -> supplierValidator.isValidEmail(supplier.getEmail()));
    }

    // Test for invalid supplier email
    @Test
    @DisplayName("Test invalid email")
    void testInvalidEmail() {
        supplier.setEmail("invalid-email");
        assertThrows(SupplierNotValidFieldException.class, () -> supplierValidator.isValidEmail(supplier.getEmail()));
    }
}