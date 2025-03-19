package com.example.junit.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.junit.model.Product;
import com.example.junit.repository.ProductRepository;
import com.example.junit.serviceimpl.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {

	@Mock
	private ProductRepository productRepo;

	@InjectMocks
	private ProductServiceImpl productService;

	private AutoCloseable closeable; // For cleaning up mocks

	@Before
	public void setUp() {
		// Initialize mocks before each test
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		// Clean up resources and close mocks after each test
		closeable.close();
	}

	@Test
	@DisplayName("JUnit test for saving a product")
	public void saveProductTest() {

		Product product = new Product(null, "Iphone", 1000, "Mobile");
		when(productRepo.save(product)).thenReturn(product);
		assertEquals(product, productService.saveProduct(product));
	}

	@Test
	@DisplayName("JUnit test for Fetching all products")
	public void fetchAllProductsTest() {
		List<Product> products = new ArrayList<>();
		products.add(new Product(null, "Iphone", 1000, "Mobile"));
		products.add(new Product(null, "Hp", 2000, "Laptop"));
		products.add(new Product(null, "H&M", 3000, "Cloth"));

		when(productRepo.findAll()).thenReturn(products);
		assertEquals(products, productService.fetchAllProducts());
		assertEquals(3, productService.fetchAllProducts().size());

	}

	@Test
	@DisplayName("JUnit test for fetch product by id")
	public void fetchProductByIdSuccessTest() throws Exception {
		Product product = new Product(1L, "Iphone", 1000, "Mobile");
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));

		Product fetchedProduct = productService.fetchProduct(1L);

		assertEquals(1L, fetchedProduct.getId().longValue());
		assertEquals("Iphone", fetchedProduct.getProductName());
		assertEquals(1000, fetchedProduct.getPrice());
		assertEquals("Mobile", fetchedProduct.getDepartment());
	}

	@Test
	@DisplayName("JUnit test for product not found by ID, throws Exception")
	public void fetchProductByIdNotFoundTest() throws Exception {
		when(productRepo.findById(1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(Exception.class, () -> {
			productService.fetchProduct(1L);
		});
		assertEquals("Product id not found", exception.getMessage());
	}

	@Test
	@DisplayName("JUnit test for Update Product By Id")
	public void UpdateProductByIdSuccessTest() throws Exception {
		Product product = new Product(1L, "Iphone", 1000, "Mobile");
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));
		Product updatedProduct = new Product(1L, "Iphone 13", 1200, "Mobile");
		Product result=productService.UpdateProductById(1L, updatedProduct);
		
		assertEquals(result, updatedProduct);
		
	}
	
	@Test
	@DisplayName("JUnit test for product not found by ID, throws Exception")
	public void updateProductByIdNotFoundTest() throws Exception {
		when(productRepo.findById(1L)).thenReturn(Optional.empty());
		Exception exception=assertThrows(Exception.class,()->{
			productService.UpdateProductById(1L, new Product());
		});
		assertEquals("Product id not found", exception.getMessage());
	}
	
	@Test
	@DisplayName("JUnit test for delete Product By Id")
	public void deleteProductByIdSuccessTest() throws Exception {
		Product product = new Product(1L, "Iphone", 1000, "Mobile");
		when(productRepo.findById(1L)).thenReturn(Optional.of(product));
		
		Product deleteProduct=productService.delete(1L);
		
		assertEquals(product, deleteProduct);
	}
	
	@Test
	@DisplayName("JUnit test for product not found by ID, throws Exception")
	public void deleteProductByIdNotFoundTest() {
		when(productRepo.findById(1L)).thenReturn(Optional.empty());
		Exception exception=assertThrows(Exception.class,()->{
			productService.delete(1L);
		});
		assertEquals("Product id not found", exception.getMessage());
	}
}
