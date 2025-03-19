package com.example.junit.testcases;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.junit.controller.ProductController;
import com.example.junit.model.Product;
import com.example.junit.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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
        when(productService.saveProduct(Mockito.any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone"))
                .andExpect(jsonPath("$.price").value(1000))
                .andExpect(jsonPath("$.department").value("Mobile"));
    }

    @Test
    @DisplayName("JUnit test for fetching all products")
    public void fetchProductsTest() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "Iphone", 1000, "Mobile"),
                new Product(2L, "Hp", 2000, "Laptop"));

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
        when(productService.fetchProduct(Mockito.eq(1L))).thenReturn(product); // Updated to match the new method signature

        mockMvc.perform(get("/api/product/fetch/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone"))
                .andExpect(jsonPath("$.price").value(1000))
                .andExpect(jsonPath("$.department").value("Mobile"));
    }

    @Test
    @DisplayName("JUnit test for updating product by ID")
    public void updateProductByIdTest() throws Exception {
        Product updatedProduct = new Product(1L, "Iphone 13", 1200, "Mobile");
        when(productService.UpdateProductById(Mockito.eq(1L), Mockito.any())).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone 13"))
                .andExpect(jsonPath("$.price").value(1200));
    }

    @Test
    @DisplayName("JUnit test for deleting product by ID")
    public void deleteProductById() throws Exception {
        Product deleteProduct = new Product(1L, "Iphone", 1000, "Mobile");
        when(productService.delete(Mockito.eq(1L))).thenReturn(deleteProduct);

        mockMvc.perform(delete("/api/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Iphone"))
                .andExpect(jsonPath("$.price").value(1000))
                .andExpect(jsonPath("$.department").value("Mobile"));
    }
}
