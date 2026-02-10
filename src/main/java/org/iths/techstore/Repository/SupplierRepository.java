package org.iths.techstore.Repository;

import org.iths.techstore.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
