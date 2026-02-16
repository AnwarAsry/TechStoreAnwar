package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.SupplierNotFoundException;
import org.iths.techstore.Exceptions.SupplierNotValidFieldException;
import org.iths.techstore.Model.Supplier;
import org.iths.techstore.Repository.SupplierRepository;
import org.iths.techstore.Validator.SupplierValidator;
import org.junit.jupiter.api.BeforeEach;
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
public class SupplierServiceMockTest {
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private SupplierValidator supplierValidator;
    @InjectMocks
    private SupplierService supplierService;

    Supplier supplier;

    @BeforeEach
    void setUp() {
        supplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "some@ek.se");
    }

    // Testing fetch all suppliers
    @Test
    @DisplayName("Test get all suppliers")
    void getAllSuppliers() {
        when(supplierRepository.findAll()).thenReturn(List.of(
                new Supplier(1L, "Company A", "Country A", "Contact Person A", "somw@ek.se"),
                new Supplier(2L, "Company B", "Country B", "Contact Person B", "meok@oke.com")));

        List<Supplier> suppliers = supplierService.getAllSuppliers();

        verify(supplierRepository).findAll();
        assertFalse(suppliers.isEmpty());
    }

    // Testing fetch supplier by id
    @Test
    @DisplayName("Test get supplier by id success")
    void getSupplierById() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.getSupplierById(1L);
        verify(supplierRepository).findById(1L);
        assertEquals(1L, result.getId());
    }

    // Testing fetch supplier by non-existing id
    @Test
    @DisplayName("Test get supplier by id failure")
    void getSupplierByIdNotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(SupplierNotFoundException.class, () -> supplierService.getSupplierById(1L));
        verify(supplierRepository).findById(1L);
    }

    // Testing create supplier with valid data
    @Test
    @DisplayName("Test create supplier success")
    void createSupplierSuccess() {
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(supplier);

        verify(supplierValidator).isValidCompanyName(supplier.getCompanyName());
        verify(supplierValidator).isValidCountry(supplier.getCountry());
        verify(supplierValidator).isValidContactPerson(supplier.getContactPerson());
        verify(supplierValidator).isValidEmail(supplier.getEmail());
        verify(supplierRepository).save(supplier);
        assertEquals("Company A", createdSupplier.getCompanyName());
    }

    // Testing create supplier with invalid company name
    @Test
    @DisplayName("Test create supplier invalid companyName field")
    void createSupplierInvalidCompanyName() {
        supplier.setCompanyName("");

        doThrow(new SupplierNotValidFieldException("Invalid Value for company name"))
                .when(supplierValidator).isValidCompanyName("");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidCompanyName(supplier.getCompanyName());
    }

    // Testing create supplier with invalid country
    @Test
    @DisplayName("Test create supplier invalid country field")
    void createSupplierInvalidCountry() {
        supplier.setCountry("");

        doThrow(new SupplierNotValidFieldException("Invalid Value for country"))
                .when(supplierValidator).isValidCountry("");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidCountry(supplier.getCountry());
    }

    // Testing create supplier with invalid contact person
    @Test
    @DisplayName("Test create supplier invalid contactPerson field")
    void createSupplierInvalidContactPerson() {
        supplier.setContactPerson("");

        doThrow(new SupplierNotValidFieldException("Invalid Value for contact person"))
                .when(supplierValidator).isValidContactPerson("");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidContactPerson(supplier.getContactPerson());
    }

    // Testing create supplier with invalid email
    @Test
    @DisplayName("Test create supplier invalid email field")
    void createSupplierInvalidEmail() {
        supplier.setEmail("sdjksj");

        doThrow(new SupplierNotValidFieldException("Invalid Value for email"))
                .when(supplierValidator).isValidEmail("sdjksj");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidEmail(supplier.getEmail());
    }

    // Testing update supplier with valid data
    @Test
    @DisplayName("Test update supplier success")
    void updateSupplierSuccess() {
        supplier.setCompanyName("Updated Company A");

        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier updatedSupplier = supplierService.updateSupplier(1L, supplier);

        verify(supplierRepository).save(updatedSupplier);
        assertEquals("Updated Company A", updatedSupplier.getCompanyName());
    }

    // Testing update supplier by non-existing id
    @Test
    @DisplayName("Test update supplier non-existing id")
    void updateSupplierNonExistingId() {
        when(supplierRepository.existsById(1L)).thenReturn(false);

        assertThrows(SupplierNotFoundException.class, () -> supplierService.updateSupplier(1L, supplier));
    }

    // Testing update supplier with invalid company name
    @Test
    @DisplayName("Test update supplier invalid companyName field")
    void updateSupplierInvalidCompanyName() {
        supplier.setCompanyName("");
        when(supplierRepository.existsById(1L)).thenReturn(true);

        doThrow(new SupplierNotValidFieldException("Invalid Value for company name"))
                .when(supplierValidator).isValidCompanyName("");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidCompanyName(supplier.getCompanyName());
    }

    // Testing update supplier with invalid country
    @Test
    @DisplayName("Test update supplier invalid country field")
    void updateSupplierInvalidCountry() {
        supplier.setCountry("");

        when(supplierRepository.existsById(1L)).thenReturn(true);

        doThrow(new SupplierNotValidFieldException("Invalid Value for country"))
                .when(supplierValidator).isValidCountry("");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidCountry(supplier.getCountry());
    }

    // Testing update supplier with invalid contact person
    @Test
    @DisplayName("Test update supplier invalid contactPerson field")
    void updateSupplierInvalidContactPerson() {
        supplier.setContactPerson("");

        when(supplierRepository.existsById(1L)).thenReturn(true);

        doThrow(new SupplierNotValidFieldException("Invalid Value for contact person"))
                .when(supplierValidator).isValidContactPerson("");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidContactPerson(supplier.getContactPerson());
    }

    // Testing update supplier with invalid email
    @Test
    @DisplayName("Test update supplier invalid email field")
    void updateSupplierInvalidEmail() {
        supplier.setEmail("sdjksj");

        when(supplierRepository.existsById(1L)).thenReturn(true);

        doThrow(new SupplierNotValidFieldException("Invalid Value for email"))
                .when(supplierValidator).isValidEmail("sdjksj");

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidEmail(supplier.getEmail());
    }

    // Testing delete supplier with existing id
    @Test
    @DisplayName("Test delete supplier success")
    void deleteSupplierSuccess() {
        when(supplierRepository.existsById(supplier.getId())).thenReturn(true);
        supplierService.deleteSupplier(supplier.getId());
        verify(supplierRepository).deleteById(supplier.getId());
    }

    // Testing delete supplier with non-existing id
    @Test
    @DisplayName("Test delete supplier non-existing id")
    void deleteSupplierNonExistingId() {
        when(supplierRepository.existsById(supplier.getId())).thenReturn(false);
        assertThrows(SupplierNotFoundException.class, () -> supplierService.deleteSupplier(supplier.getId()));
        verify(supplierRepository).existsById(supplier.getId());
    }
}