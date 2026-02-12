package org.iths.techstore.Validator;

import org.springframework.stereotype.Component;

@Component
public class SupplierValidator {
    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
