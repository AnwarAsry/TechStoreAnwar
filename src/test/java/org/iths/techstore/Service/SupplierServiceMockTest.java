package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.SupplierNotFoundException;
import org.iths.techstore.Exceptions.SupplierNotValidFieldException;
import org.iths.techstore.Model.Supplier;
import org.iths.techstore.Repository.SupplierRepository;
import org.iths.techstore.Validator.SupplierValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupplierServiceMockTest {
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private SupplierValidator supplierValidator;
    @InjectMocks
    private SupplierService supplierService;

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
        Supplier supplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "somw@ek.se");
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
        Supplier supplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "somw@ek.se");
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getContactPerson())).thenReturn(true);
        when(supplierValidator.isValidEmail(supplier.getEmail())).thenReturn(true);
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(supplier);

        verify(supplierRepository).save(supplier);
        verify(supplierValidator).isValidName(supplier.getCompanyName());
        verify(supplierValidator).isValidName(supplier.getCountry());
        verify(supplierValidator).isValidName(supplier.getContactPerson());
        verify(supplierValidator).isValidEmail(supplier.getEmail());
        assertEquals("Company A", createdSupplier.getCompanyName());
    }

    // Testing create supplier with invalid company name
    @Test
    @DisplayName("Test create supplier invalid companyName field")
    void createSupplierInvalidCompanyName() {
        Supplier supplier = new Supplier(1L, "", "Country A", "Contact Person A", "somw@ek.se");
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(false);

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidName(supplier.getCompanyName());
    }

    // Testing create supplier with invalid country
    @Test
    @DisplayName("Test create supplier invalid country field")
    void createSupplierInvalidCountry() {
        Supplier supplier = new Supplier(1L, "Company A", "", "Contact Person A", "somw@ek.se");
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(false);


        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidName(supplier.getCountry());
    }

    // Testing create supplier with invalid contact person
    @Test
    @DisplayName("Test create supplier invalid contactPerson field")
    void createSupplierInvalidContactPerson() {
        Supplier supplier = new Supplier(1L, "Company A", "Country A", "", "somw@ek.se");
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getContactPerson())).thenReturn(false);

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidName(supplier.getContactPerson());
    }

    // Testing create supplier with invalid email
    @Test
    @DisplayName("Test create supplier invalid email field")
    void createSupplierInvalidEmail() {
        Supplier supplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "sdjksj");
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getContactPerson())).thenReturn(true);
        when(supplierValidator.isValidEmail(supplier.getEmail())).thenReturn(false);

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.createSupplier(supplier));
        verify(supplierValidator).isValidEmail(supplier.getEmail());
    }

    // Testing update supplier with valid data
    @Test
    @DisplayName("Test update supplier success")
    void updateSupplierSuccess() {
        Supplier existingSupplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "some@ek.se");

        existingSupplier.setCompanyName("Updated Company A");
        existingSupplier.setCountry("Updated Country A");
        existingSupplier.setContactPerson("Updated Contact Person A");
        existingSupplier.setEmail("updated@gmail.com");

        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierValidator.isValidName(existingSupplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(existingSupplier.getCountry())).thenReturn(true);
        when(supplierValidator.isValidName(existingSupplier.getContactPerson())).thenReturn(true);
        when(supplierValidator.isValidEmail(existingSupplier.getEmail())).thenReturn(true);
        when(supplierRepository.save(existingSupplier)).thenReturn(existingSupplier);

        Supplier updatedSupplier = supplierService.updateSupplier(1L, existingSupplier);
        verify(supplierRepository).save(updatedSupplier);
    }

    // Testing update supplier by non-existing id
    @Test
    @DisplayName("Test update supplier non-existing id")
    void updateSupplierNonExistingId() {
        Supplier updatedSupplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "some@ek.se");
        when(supplierRepository.existsById(1L)).thenReturn(false);

        assertThrows(SupplierNotFoundException.class, () -> supplierService.updateSupplier(1L, updatedSupplier));
    }

    // Testing update supplier with invalid company name
    @Test
    @DisplayName("Test update supplier invalid companyName field")
    void updateSupplierInvalidCompanyName() {
        Supplier supplier = new Supplier(1L, "", "Country A", "Contact Person A", "somw@ek.se");
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(false);

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidName(supplier.getCompanyName());
    }

    // Testing update supplier with invalid country
    @Test
    @DisplayName("Test update supplier invalid country field")
    void updateSupplierInvalidCountry() {
        Supplier supplier = new Supplier(1L, "Company A", "", "Contact Person A", "some@ek.se");
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(false);


        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidName(supplier.getCountry());
    }

    // Testing update supplier with invalid contact person
    @Test
    @DisplayName("Test update supplier invalid contactPerson field")
    void updateSupplierInvalidContactPerson() {
        Supplier supplier = new Supplier(1L, "Company A", "Country A", "", "some@ek.se");
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getContactPerson())).thenReturn(true);

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidName(supplier.getContactPerson());
    }

    // Testing update supplier with invalid email
    @Test
    @DisplayName("Test update supplier invalid email field")
    void updateSupplierInvalidEmail() {
        Supplier supplier = new Supplier(1L, "Company A", "Country A", "Contact Person A", "sdjksj");
        when(supplierRepository.existsById(1L)).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCompanyName())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getCountry())).thenReturn(true);
        when(supplierValidator.isValidName(supplier.getContactPerson())).thenReturn(true);
        when(supplierValidator.isValidEmail(supplier.getEmail())).thenReturn(false);

        assertThrows(SupplierNotValidFieldException.class, () -> supplierService.updateSupplier(1L, supplier));
        verify(supplierValidator).isValidEmail(supplier.getEmail());
    }

    // Testing delete supplier with existing id
    @Test
    @DisplayName("Test delete supplier success")
    void deleteSupplierSuccess() {
        when(supplierRepository.existsById(1L)).thenReturn(true);
        supplierService.deleteSupplier(1L);
        verify(supplierRepository).deleteById(1L);
    }

    // Testing delete supplier with non-existing id
    @Test
    @DisplayName("Test delete supplier non-existing id")
    void deleteSupplierNonExistingId() {
        when(supplierRepository.existsById(1L)).thenReturn(false);
        assertThrows(SupplierNotFoundException.class, () -> supplierService.deleteSupplier(1L));
        verify(supplierRepository).existsById(1L);
    }
}
