package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.SupplierNotValidFieldException;
import org.springframework.stereotype.Component;

@Component
public class SupplierValidator {
    public void isValidCompanyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new SupplierNotValidFieldException("Invalid Value for company name: " + name);
        }
    }

    public void isValidCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new SupplierNotValidFieldException("Invalid Value for country: " + country);
        }
    }

    public void isValidContactPerson(String contactPerson) {
        if (contactPerson == null || contactPerson.trim().isEmpty()) {
            throw new SupplierNotValidFieldException("Invalid Value for contact person: " + contactPerson);
        }
    }

    public void isValidEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new SupplierNotValidFieldException("Invalid Value for email: " + email);
        }
    }
}
