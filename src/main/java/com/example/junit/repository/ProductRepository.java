package com.example.junit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.junit.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

}
