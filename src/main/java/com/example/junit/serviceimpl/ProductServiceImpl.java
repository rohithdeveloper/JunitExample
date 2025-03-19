package com.example.junit.serviceimpl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.junit.model.Product;
import com.example.junit.repository.ProductRepository;
import com.example.junit.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		Product p=productRepo.save(product);
		return p;
	}

	@Override
	public List<Product> fetchAllProducts() {
		// TODO Auto-generated method stub
		List<Product> list=productRepo.findAll();
		return list;
	}

	@Override
	public Product UpdateProductById(Long id,Product product) throws Exception {
		// TODO Auto-generated method stub
		Optional<Product> products=productRepo.findById(id);
		if(products.isPresent()) {
			Product p=products.get();
			p.setId(product.getId());
			p.setPrice(product.getPrice());
			p.setProductName(product.getProductName());
			p.setDepartment(product.getDepartment());
			productRepo.save(p);
			return p;
		}
		else {
			throw new Exception("Product id not found");
		}
	}

	@Override
	public Product delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Product> p=productRepo.findById(id);
		if(p.isPresent()) {
			Product deleteProduct=p.get();
			productRepo.delete(deleteProduct);
			return deleteProduct;
		}
		else {
			throw new Exception("Product id not found");
		}
	}



	@Override
	public Product fetchProduct(Long id) throws Exception {
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent()) {
			return p.get();
		} else {
			throw new Exception("Product id not found");
		}
	}

}
