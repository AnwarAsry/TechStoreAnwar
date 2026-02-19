package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.SupplierNotValidFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SupplierValidator {
    private static final Logger log = LoggerFactory.getLogger(SupplierValidator.class);

    public void isValidCompanyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            log.warn("Invalid company name: {}", name);
            throw new SupplierNotValidFieldException("Invalid Value for company name: " + name);
        }
    }

    public void isValidCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            log.warn("Invalid country: {}", country);
            throw new SupplierNotValidFieldException("Invalid Value for country: " + country);
        }
    }

    public void isValidContactPerson(String contactPerson) {
        if (contactPerson == null || contactPerson.trim().isEmpty()) {
            log.warn("Invalid contact person: {}", contactPerson);
            throw new SupplierNotValidFieldException("Invalid Value for contact person: " + contactPerson);
        }
    }

    public void isValidEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            log.warn("Invalid email: {}", email);
            throw new SupplierNotValidFieldException("Invalid Value for email: " + email);
        }
    }
}
