package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.SupplierNotFoundException;
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
        supplierValidator.isValidCompanyName(supplier.getCompanyName());
        supplierValidator.isValidCountry(supplier.getCountry());
        supplierValidator.isValidContactPerson(supplier.getContactPerson());
        supplierValidator.isValidEmail(supplier.getEmail());

        return supplierRepository.save(supplier);
    }

    // Update an existing supplier
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierNotFoundException("Supplier not found with id: " + id);
        }

        supplierValidator.isValidCompanyName(updatedSupplier.getCompanyName());
        supplierValidator.isValidCountry(updatedSupplier.getCountry());
        supplierValidator.isValidContactPerson(updatedSupplier.getContactPerson());
        supplierValidator.isValidEmail(updatedSupplier.getEmail());

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
