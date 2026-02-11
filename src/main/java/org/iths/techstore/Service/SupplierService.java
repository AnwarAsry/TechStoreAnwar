package org.iths.techstore.Service;

import org.iths.techstore.Model.Supplier;
import org.iths.techstore.Repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    // Constructor Injection
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // Get supplier by ID
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    // Create a new supplier
    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    // Update an existing supplier
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        if (!supplierRepository.existsById(id))
            throw new NoSuchElementException("Supplier not found with id: " + id);

        updatedSupplier.setId(id);
        return supplierRepository.save(updatedSupplier);
    }

    // Delete a supplier
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new NoSuchElementException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
