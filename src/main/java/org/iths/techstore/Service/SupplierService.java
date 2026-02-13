package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.SupplierNotFoundException;
import org.iths.techstore.Exceptions.SupplierNotValidFieldException;
import org.iths.techstore.Model.Supplier;
import org.iths.techstore.Repository.SupplierRepository;
import org.iths.techstore.Validator.SupplierValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierValidator supplierValidator;

    // Constructor Injection
    public SupplierService(SupplierRepository supplierRepository, SupplierValidator supplierValidator) {
        this.supplierRepository = supplierRepository;
        this.supplierValidator = supplierValidator;
    }

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // Get supplier by ID
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + id));
    }

    // Create a new supplier
    public Supplier createSupplier(Supplier supplier) {
        if (!supplierValidator.isValidName(supplier.getCompanyName())) {
            throw new SupplierNotValidFieldException("Invalid Value for company name: " + supplier.getCompanyName());
        }

        if (!supplierValidator.isValidName(supplier.getCountry())) {
            throw new SupplierNotValidFieldException("Invalid Value for country: " + supplier.getCountry());
        }

        if (!supplierValidator.isValidName(supplier.getContactPerson())) {
            throw new SupplierNotValidFieldException("Invalid Value for contact person: " + supplier.getContactPerson());
        }

        if (!supplierValidator.isValidEmail(supplier.getEmail())) {
            throw new SupplierNotValidFieldException("Invalid Value for email: " + supplier.getEmail());
        }
        return supplierRepository.save(supplier);
    }

    // Update an existing supplier
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        if (!supplierRepository.existsById(id))
            throw new SupplierNotFoundException("Supplier not found with id: " + id);

        if (!supplierValidator.isValidName(updatedSupplier.getCompanyName())) {
            throw new SupplierNotValidFieldException("Invalid Value for company name: " + updatedSupplier.getCompanyName());
        }

        if (!supplierValidator.isValidName(updatedSupplier.getCountry())) {
            throw new SupplierNotValidFieldException("Invalid Value for country: " + updatedSupplier.getCountry());
        }

        if (!supplierValidator.isValidName(updatedSupplier.getContactPerson())) {
            throw new SupplierNotValidFieldException("Invalid Value for contact person: " + updatedSupplier.getContactPerson());
        }

        if (!supplierValidator.isValidEmail(updatedSupplier.getEmail())) {
            throw new SupplierNotValidFieldException("Invalid Value for email: " + updatedSupplier.getEmail());
        }

        updatedSupplier.setId(id);
        return supplierRepository.save(updatedSupplier);
    }

    // Delete a supplier
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
