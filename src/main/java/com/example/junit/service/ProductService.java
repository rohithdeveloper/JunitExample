package com.example.junit.service;

import java.util.List;

import com.example.junit.model.Product;

public interface ProductService {
	 Product saveProduct(Product product);
	 List<Product> fetchAllProducts();
	 Product fetchProduct(Long id) throws Exception;
	 Product delete(Long id) throws Exception;
	 Product UpdateProductById(Long id,Product product) throws Exception;
}
