package com.example.junit.testcases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.junit.model.Product;
import com.example.junit.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryTests {
    
    @Autowired
    private ProductRepository productRepo;
    
    private Product product;

    @BeforeEach
    public void setupTestData() {
        // Create a default product for testing
        product = Product.builder()
                .department("Mobile")
                .price(100)
                .productName("Oneplus")
                .id(1L)
                .build();
    }
    
    @Test
    @DisplayName("JUnit test for save product operation")
    public void saveProductTest() {
        // Arrange & Act: Save a new product
        Product product = new Product(null, "Iphone", 1000, "Mobile");
        Product savedProduct = productRepo.save(product);
        
        // Assert: Check if saved product fields are correct
        assertEquals("Iphone", savedProduct.getProductName());
        assertEquals(1000, savedProduct.getPrice());
        assertEquals("Mobile", savedProduct.getDepartment());
        assertThat(savedProduct.getId()).isNotNull(); // Check ID is auto-generated
    }
    
    @Test
    @DisplayName("JUnit test for fetch all products")
    public void fetchAllProductsTest() {
        // Arrange: Create and save a list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product(null, "Iphone", 1000, "Mobile"));
        products.add(new Product(null, "Hp", 2000, "Laptop"));
        products.add(new Product(null, "H&M", 3000, "Cloth"));
        productRepo.saveAll(products);
        
        // Act: Fetch all products and assert size
        List<Product> savedProducts = productRepo.findAll();
        assertEquals(3, savedProducts.size());
    }
    
    @Test
    @DisplayName("Fetch product by id")
    public void fetchProductByIdTest() {
        // Arrange: Save the product
        Product savedProduct = productRepo.save(product);
        
        // Act: Fetch the product by ID
        Optional<Product> fetchedProduct = productRepo.findById(savedProduct.getId());
        
        // Assert: Check if fetched product matches saved product
        assertEquals(savedProduct.getId(), fetchedProduct.get().getId());
    }
    
    @Test
    @DisplayName("Update product by id")
    public void updateProductByIdTest() {
        // Arrange: Save the initial product
        Product savedProduct = productRepo.save(product);
        
        // Act: Update and save the product
        Optional<Product> optionalProduct = productRepo.findById(savedProduct.getId());
        if (optionalProduct.isPresent()) {
            Product getProduct = optionalProduct.get();
            getProduct.setProductName("Iphone");
            getProduct.setPrice(1000);
            getProduct.setDepartment("Mobile");
            Product updatedProduct = productRepo.save(getProduct);
            
            // Assert: Check updated fields
            assertEquals("Iphone", updatedProduct.getProductName());
            assertEquals(1000, updatedProduct.getPrice());
            assertEquals("Mobile", updatedProduct.getDepartment());
        }
    }
    
    @Test
    @DisplayName("Delete product by id")
    public void deleteProductByIdTest() {
        // Arrange: Save the product
        Product savedProduct = productRepo.save(product);
        
        // Act: Delete the product by ID
        productRepo.deleteById(savedProduct.getId());
        
        // Assert: Check that the product is no longer present
        Optional<Product> optionalProduct = productRepo.findById(savedProduct.getId());
        assertThat(optionalProduct).isEmpty();  // Check if the product was deleted
    }
}
