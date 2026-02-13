package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.ProductNotFoundException;
import org.iths.techstore.Exceptions.ProductNotValidException;
import org.iths.techstore.Model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductValidatorTest {

    private ProductValidator productValidator;
    private Product product;


    @BeforeEach
    void setUp() {
        productValidator = new ProductValidator();
        product = new Product("Sony", "TV", 7999, 45);
    }

    @Test
    void validatorShouldNotThrowWhenProductIsValid() {
        assertDoesNotThrow(() -> productValidator.validator(product));
    }

    @Test
    void validatorShouldThrowWhenNameIsNotValid() {
        product.setName(null);
        assertThrows(ProductNotFoundException.class, () -> {
            productValidator.validator(product);
        });
    }

    @Test
    void validatorShouldThrowWhenCategoryIsNotValid() {
        product.setCategory(null);
        assertThrows(ProductNotFoundException.class, () -> {
            productValidator.validator(product);
        });
    }

    @Test
    void validatorShouldThrowWhenPriceIsNotValid() {
        product.setPrice(200000);
        assertThrows(ProductNotValidException.class, () -> {
            productValidator.validator(product);
        });
    }

    @Test
    void validatorShouldThrowWhenQuantityIsNotValid() {
        product.setStockQuantity(-1);
        assertThrows(ProductNotValidException.class, () -> {
            productValidator.validator(product);
        });
    }

    @Test
    void validateProductName() {
        assertThrows(ProductNotFoundException.class, () -> {
            productValidator.validateProductName(null);
        });
    }

    @Test
    void productNameValid() {
        assertDoesNotThrow(() -> productValidator.validateProductName("Nokia")
        );
    }

    @Test
    void validateProductCategory() {
        assertThrows(ProductNotFoundException.class, () -> {
            productValidator.validateProductCategory(null);
        });
    }

    @Test
    void productCategoryValid() {
        assertDoesNotThrow(() -> productValidator.validateProductCategory("Cellphone")
        );
    }

    @Test
    void validateProductPrice() {
        assertThrows(ProductNotValidException.class, () -> {
            productValidator.validateProductPrice(-1);
        });
    }

    @Test
    void productPriceValid() {
        assertDoesNotThrow(() -> productValidator.validateProductPrice(3499));
    }

    @Test
    void validateProductStockQuantity() {
        assertThrows(ProductNotValidException.class, () -> {
            productValidator.validateProductStockQuantity(101);
        });
    }

    @Test
    void productStockQuantityValid() {
        assertDoesNotThrow(() -> productValidator.validateProductStockQuantity(51));
    }
}