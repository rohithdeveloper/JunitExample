package com.example.junit.testcases;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.junit.controller.ProductController;
import com.example.junit.model.Product;
import com.example.junit.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    @DisplayName("JUnit test for welcome endpoint")
    public void welcomeTest() throws Exception {
        mockMvc.perform(get("/api/welcome"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Welcome to springboot security"));
    }

    @Test
    @DisplayName("JUnit test for saving a product")
    public void saveProductsTest() throws Exception {
        Product product = new Product(null, "Iphone", 1000, "Mobile");
        when(productService.saveProduct(product)).thenReturn(product);

        mockMvc.perform(post("/api/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone"))
                .andExpect(jsonPath("$.price").value(1000))
                .andExpect(jsonPath("$.department").value("Mobile"));
    }

    @Test
    @DisplayName("JUnit test for fetching all products")
    public void fetchProductsTest() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Iphone", 1000, "Mobile"));
        products.add(new Product(2L, "Hp", 2000, "Laptop"));
        
        when(productService.fetchAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/product/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Iphone"))
                .andExpect(jsonPath("$[1].productName").value("Hp"));
    }

    @Test
    @DisplayName("JUnit test for fetching product by ID")
    public void fetchProductByIdTest() throws Exception {
        Product product = new Product(1L, "Iphone", 1000, "Mobile");
        when(productService.fetchProduct(1L, product)).thenReturn(product);

        mockMvc.perform(get("/api/product/fetch/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone"));
    }

    @Test
    @DisplayName("JUnit test for updating product by ID")
    public void updateProductByIdTest() throws Exception {
        Product existingProduct = new Product(1L, "Iphone", 1000, "Mobile");
        Product updatedProduct = new Product(1L, "Iphone 13", 1200, "Mobile");

        when(productService.UpdateProductById(1L, updatedProduct)).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone 13"))
                .andExpect(jsonPath("$.price").value(1200));
    }
    
    @Test
    @DisplayName("JUnit test for deleting product by ID")
    public void deleteProductById() throws Exception {
    	Product deleteproduct=new Product(1L, "Iphone", 1000, "Mobile");
    	
    	when(productService.delete(1L)).thenReturn(deleteproduct);
    	
    	 mockMvc.perform(delete("/api/product/1")
    	            .contentType(MediaType.APPLICATION_JSON))
    	            .andExpect(status().isOk())
    	            .andExpect(jsonPath("$.productName").value("Iphone"))
    	            .andExpect(jsonPath("$.price").value(1000))
    	            .andExpect(jsonPath("$.department").value("Mobile"));
    	
    }
}
