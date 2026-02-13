package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.ProductNotFoundException;
import org.iths.techstore.Exceptions.ProductNotValidException;
import org.iths.techstore.Model.Product;
import org.iths.techstore.Repository.ProductRepository;
import org.iths.techstore.Validator.ProductValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    ProductService productService;

    @Mock
    ProductValidator productValidator;

    @Mock
    ProductRepository productRepository;

    //Get all products-test

    @Test
    @DisplayName("Test to get all products")
    public void getAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(
                new Product("Ericsson", "Mobile", 2999, 10),
                new Product("Sony", "Tablet", 4999, 20)
        ));

        List<Product> productList = productService.getAllProducts();

        verify(productRepository).findAll();
        assertEquals("Ericsson", productList.getFirst().getName());
        assertEquals("Sony", productList.getLast().getName());
        assertEquals(2, productList.size());
    }

    //Create Product-tests
    @Test
    @DisplayName("Test to create a product")
    public void createProduct() {
        Product product = new Product("iPad", "Tablet", 5999, 50);

        doNothing().when(productValidator).validator(product);
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        assertEquals("iPad", savedProduct.getName());
        assertNotNull(savedProduct);
        assertEquals(5999, savedProduct.getPrice());

        verify(productValidator).validator(product);
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Test to create a product with invalid name")
    public void createProductShouldThrowExceptionWhenNameIsInvalid() {
        Product product = new Product("", "Tablet", 4999, 8);

        doThrow(new ProductNotFoundException("Product name is not valid"))
                .when(productValidator).validator(product);

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.createProduct(product)
        );

        assertEquals("Product name is not valid", exception.getMessage());
        verify(productValidator).validator(product);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test to create a product with invalid price")
    public void createProductShouldThrowExceptionWhenPriceIsInvalid() {
        Product product = new Product("Apple", "Tablet", 0, 10);

        doThrow(new ProductNotValidException("Price can't be nothing"))
                .when(productValidator).validator(product);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> productService.createProduct(product)
        );

        assertEquals("Price can't be nothing", exception.getMessage());
        verify(productValidator).validator(product);
        verify(productRepository, never()).save(any());
    }

    //Update Product-tests
    @Test
    @DisplayName("Test to update existning product")
    public void updateProduct() {
        Long id = 2L;
        Product product = new Product("Lenovo", "Computer", 9999, 10);

        when(productRepository.existsById(id)).thenReturn(true);
        doNothing().when(productValidator).validator(product);
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(id, product);

        assertNotNull(updatedProduct);
        assertEquals(id, updatedProduct.getId());
        assertEquals("Lenovo", updatedProduct.getName());

        verify(productRepository).existsById(id);
        verify(productValidator).validator(product);
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Test to update non-existing product")
    public void updateProductShouldThrowExceptionWhenProductDoesNotExist() {
        Long id = 999L;
        Product product = new Product("Samsung", "TV", 8999, 43);

        when(productRepository.existsById(id)).thenReturn(false);

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.updateProduct(id, product)
        );

        assertEquals("Product with id: 999 doesn't exist.", exception.getMessage());
        verify(productRepository).existsById(id);
        verify(productValidator, never()).validator(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test to update product with invalid data")
    public void updateProductShouldThrowExceptionWhenDataIsInvalid() {
        Long id = 1L;
        Product product = new Product("Samsung", "Tablet", -100, 30);

        when(productRepository.existsById(id)).thenReturn(true);
        doThrow(new ProductNotValidException("Price can't be nothing"))
                .when(productValidator).validator(product);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> productService.updateProduct(id, product)
        );

        assertEquals("Price can't be nothing", exception.getMessage());
        verify(productRepository).existsById(id);
        verify(productValidator).validator(product);
        verify(productRepository, never()).save(any());

    }

    //Get ProductByID-tests
    @Test
    @DisplayName("Test to get product by id")
    public void getProductById() {
        Long id = 1L;
        Product product = new Product("Samsung", "Tablet", 8999, 30);
        product.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(id);
        assertNotNull(foundProduct);
        assertEquals(id, foundProduct.getId());
        assertEquals("Samsung", foundProduct.getName());
        assertEquals(8999, foundProduct.getPrice());
        verify(productRepository).findById(id);
    }

    @Test
    @DisplayName("Test to get product by id when not found")
    public void getProductByIdWhenNotFound() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(id)
        );
        assertEquals("Product with id: 1 doesn't exist.", exception.getMessage());
        verify(productRepository).findById(id);
    }

    //Delete Product-tests
    @Test
    @DisplayName("Test to delete existning product")
    public void deleteProduct() {
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(true);
        doNothing().when(productRepository).deleteById(id);

        assertDoesNotThrow(() -> productService.deleteProduct(id));
        verify(productRepository).existsById(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    @DisplayName("Test to delete non-existing product")
    public void deleteProductShouldThrowExceptionWhenProductDoesNotExist() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(false);

        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productService.deleteProduct(id)
        );
        assertEquals("Product with id: 1 doesn't exist.", exception.getMessage());
        verify(productRepository).existsById(id);
        verify(productRepository, never()).deleteById(id);
    }
}