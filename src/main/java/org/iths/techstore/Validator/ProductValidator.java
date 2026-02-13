package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.ProductNotFoundException;
import org.iths.techstore.Exceptions.ProductNotValidException;
import org.iths.techstore.Model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public void validator(Product product) {
        validateProductName(product.getName());
        validateProductCategory(product.getCategory());
        validateProductPrice(product.getPrice());
        validateProductStockQuantity(product.getStockQuantity());
    }

    public void validateProductName(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductNotFoundException("Product name is not valid");
        }
    }

    public void validateProductCategory(String category) {
        if (category == null || category.isBlank()) {
            throw new ProductNotFoundException("Category is not valid.");
        }
    }

    public void validateProductPrice(int price) {
        if (price <= 0 || price > 100000) {
            throw new ProductNotValidException("Price must be between 1 and 100000.");
        }
    }

    public void validateProductStockQuantity(int stockQuantity) {
        if (stockQuantity < 0 || stockQuantity > 100) {
            throw new ProductNotValidException("Stock must be between 0 and 100.");
        }
    }
}

